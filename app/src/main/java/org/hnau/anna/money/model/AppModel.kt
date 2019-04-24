package org.hnau.anna.money.model

import io.reactivex.Observable
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


class AppModel {

    val availableCurrencies =
            Observable.just(Currency.values().toSet())


}