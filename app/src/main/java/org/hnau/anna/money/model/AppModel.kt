package org.hnau.anna.money.model

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function4
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import org.hnau.anna.money.converter.CurrencyConverter
import org.hnau.anna.money.converter.CurrencyConverterAsyncEmitter
import org.hnau.anna.money.converter.ecb.ECBCurrencyConverterProvider
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.model.converting.Converter
import org.hnau.anna.money.utils.rx.IsLockedObservable
import org.hnau.anna.money.utils.rx.combineObservables
import org.hnau.anna.money.utils.rx.toSync
import ru.hnau.jutils.coroutines.launch
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.helpers.toBox
import ru.hnau.jutils.possible.Possible


class AppModel {

    val isLockedObservable = IsLockedObservable()

    //Асинхронный поставщик конвертера валют
    private val currencyConverterAsyncEmitter = run {
        CurrencyConverterAsyncEmitter(ECBCurrencyConverterProvider())
    }

    //Конвертер валют
    private val currencyConverterObservable =
            currencyConverterAsyncEmitter.toSync { coroutine ->
                Dispatchers.Main.launch { isLockedObservable { coroutine() } }
            }

    //Все доступные валюты
    val availableCurrenciesObservable =
            currencyConverterObservable.map { it.data?.availableCurrencies ?: emptySet() }

    //Выбранная исходная валюта
    private val fromCurrencyObservableRaw =
            BehaviorSubject.createDefault<Box<Currency?>>(Box(null))

    //Выбранная исходная валюта (только если она есть среди доступных)
    val fromCurrencyObservable = combineObservables(
            first = fromCurrencyObservableRaw,
            second = availableCurrenciesObservable
    ) { fromCurrency, availableCurrencies ->
        fromCurrency.value
                ?.takeIf { it in availableCurrencies }
                .toBox()
    }

    //Список доступных валют для конвертации (все доступные за исключением выбранной исходной)
    val availableToCurrenciesObservable = combineObservables(
            first = availableCurrenciesObservable,
            second = fromCurrencyObservable
    ) { availableCurrencies, fromCurrency ->
        fromCurrency.value
                ?.let { availableCurrencies - it }
                ?: availableCurrencies
    }

    //Введенная пользователем сумма
    private val fromMoneyObservable =
            BehaviorSubject.createDefault("")

    //Выбранная валюта для конвертации
    private val toCurrencyObservableRaw =
            BehaviorSubject.createDefault(Box<Currency?>(null))

    //Выбранная валюта для конвертации (только если она есть среди доступных для конвертации валют)
    val toCurrencyObservable = combineObservables(
            first = toCurrencyObservableRaw,
            second = availableToCurrenciesObservable
    ) { toCurrency, availableToCurrencies ->
        toCurrency.value
                ?.takeIf { it in availableToCurrencies }
                .toBox()
    }

    //Результат выполнения конвертации или ошибка
    private val toMoneyObservable = Observable.combineLatest<Possible<CurrencyConverter>, Box<Currency?>, Box<Currency?>, String, Possible<Money>>(
            currencyConverterObservable,
            fromCurrencyObservable,
            toCurrencyObservable,
            fromMoneyObservable,
            Function4 { possibleCurrencyConverter, fromCurrency, toCurrency, fromMoneyString ->
                Possible.trySuccessCatchError {
                    Converter.convert(
                            possibleCurrencyConverter = possibleCurrencyConverter,
                            fromCurrencyBox = fromCurrency,
                            toCurrencyBox = toCurrency,
                            fromMoneyString = fromMoneyString,
                            invalidateConverter = currencyConverterAsyncEmitter::invalidate
                    )
                }
            }
    )

    //Пользователь выбрал исходную валюту
    fun onFromCurrencySelected(fromCurrency: Currency) =
            fromCurrencyObservableRaw.onNext(fromCurrency.toBox())

    //Пользователь выбрал валюту, в которую конвертировать
    fun onToCurrencySelected(toCurrency: Currency) =
            toCurrencyObservableRaw.onNext(toCurrency.toBox())

    fun onFromMoneyEntered(fromMoneyString: String) =
            fromMoneyObservable.onNext(fromMoneyString)


}