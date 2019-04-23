package org.hnau.anna.money.model

import kotlinx.coroutines.Dispatchers
import org.hnau.anna.money.converter.CurrencyConverter
import org.hnau.anna.money.converter.CurrencyConverterAsyncEmitter
import org.hnau.anna.money.converter.ecb.ECBCurrencyConverterProvider
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.utils.rx.toProducer
import org.hnau.anna.money.utils.rx.toSync
import ru.hnau.jutils.coroutines.launch
import ru.hnau.jutils.getter.base.get
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.producer.ActualProducer
import ru.hnau.jutils.producer.ActualProducerSimple
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.producer.detacher.ProducerDetachers
import ru.hnau.jutils.producer.locked_producer.SuspendLockedProducer
import ru.hnau.jutils.tryOrElse


class AppModel : ActualProducer<Money>(Money.ZERO) {

    private val detachers = ProducerDetachers()

    val lockedProducer = SuspendLockedProducer()

    private val 

    private val moneyFromProducer = ActualProducerSimple<Money>(Money.ZERO)

    private val currencyConverterProducer = CurrencyConverterAsyncEmitter(
            currencyConverterProvider = ECBCurrencyConverterProvider()
    ).toProducer().toSync { coroutine ->
        Dispatchers.Main.launch { lockedProducer { coroutine() } }
    }

    private val availableCurrenciesProducerInner = ActualProducerSimple<Set<Currency>>(emptySet())
    val availableCurrenciesProducer: Producer<Set<Currency>>
        get() = availableCurrenciesProducerInner

    private var currencyConverter: CurrencyConverter? = null
        set(value) {
            field = value
            availableCurrenciesProducerInner
                    .updateStateIfChanged(value?.availableCurrencies?.toSet() ?: emptySet())
        }

    fun setFromMoney(fromMoney: String) {
        TODO()
    }

    fun setFromCurrency(fromCurrency: Currency) {
        TODO()
    }

    fun setToCurrency(toCurrency: Currency) {
        TODO()
    }

    override fun onFirstAttached() {
        super.onFirstAttached()
        currencyConverterAsyncProducer.attach(detachers) { currencyConverterGetter ->
            Dispatchers.Main.launch {
                lockedProducer {
                    tryOrElse(
                            throwsAction = {
                                currencyConverter = currencyConverterGetter.get()
                            },
                            onThrow = {

                            }
                    )
                }
            }
        }
    }

    override fun onLastDetached() {
        super.onLastDetached()
        detachers.detachAllAndClear()
    }

}