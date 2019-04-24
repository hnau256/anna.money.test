package org.hnau.anna.money.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import org.hnau.anna.money.App
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.model.AppModel
import org.hnau.anna.money.utils.mvp.AttachableMvpPresenter
import org.hnau.anna.money.utils.rx.subscribeWhen
import org.hnau.anna.money.view.AppView
import ru.hnau.androidutils.ui.utils.logD
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.helpers.toBox
import javax.inject.Inject


@InjectViewState
class AppPresenter : AttachableMvpPresenter<AppView>() {

    @Inject
    lateinit var appModel: AppModel

    init {
        App.appModelComponent.inject(this)
    }

    private val selectedFromCurrencyObservable =
            BehaviorSubject.create<Box<Currency?>>()

    private val availableToCurrenciesObservable =
            Observable.combineLatest<Set<Currency>, Box<Currency?>, Set<Currency>>(
                    appModel.availableCurrencies,
                    selectedFromCurrencyObservable,
                    BiFunction { availableCurrencies, selectedFromCurrency ->
                        selectedFromCurrency.value
                                ?.let { availableCurrencies - it }
                                ?: availableCurrencies
                    }
            )

    private val selectedToCurrencyObservableRaw =
            BehaviorSubject.create<Box<Currency?>>()

    private val selectedToCurrencyObservable =
            Observable.combineLatest<Box<Currency?>, Set<Currency>, Box<Currency?>>(
                    selectedToCurrencyObservableRaw,
                    availableToCurrenciesObservable,
                    BiFunction { selectedToCurrency, availableToCurrencies ->
                        selectedToCurrency.value
                                ?.takeIf { it in availableToCurrencies }
                                .toBox()
                    }
            )

    init {
        selectedFromCurrencyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setSelectedFromCurrency)
        selectedToCurrencyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setSelectedToCurrency)
        appModel.availableCurrencies.subscribeWhen(isVisibleToUserObservable, viewState::setAvailableFromCurrencies)
        availableToCurrenciesObservable.subscribeWhen(isVisibleToUserObservable, viewState::setAvailableToCurrencies)
    }

    fun onFromMoneyEntered(fromMoney: String) {
        //appModel.setFromMoney(fromMoney)
        viewState.setFromMoney(fromMoney)
        TODO()
    }

    fun onFromCurrencyClicked(fromCurrency: Currency) {
        selectedFromCurrencyObservable.onNext(fromCurrency.toBox())
    }

    fun onToCurrencyClicked(toCurrency: Currency) {
        selectedToCurrencyObservableRaw.onNext(toCurrency.toBox())
    }

}