package org.hnau.anna.money.converter

import org.hnau.anna.money.utils.rx.AsyncEmitter
import ru.hnau.jutils.getter.SuspendGetter
import ru.hnau.jutils.getter.base.GetterAsync


class CurrencyConverterAsyncEmitter(
        private val currencyConverterProvider: CurrencyConverterProvider
) : AsyncEmitter<CurrencyConverter>() {

    override fun getNewGetter() =
            SuspendGetter.simple(currencyConverterProvider::getCurrencyConverter)

}