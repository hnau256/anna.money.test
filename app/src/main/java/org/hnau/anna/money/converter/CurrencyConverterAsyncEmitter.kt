package org.hnau.anna.money.converter

import org.hnau.anna.money.converter.provider.CurrencyConverterProvider
import org.hnau.anna.money.utils.rx.AsyncEmitter
import ru.hnau.jutils.getter.SuspendGetter


class CurrencyConverterAsyncEmitter(
        private val currencyConverterProvider: CurrencyConverterProvider
) : AsyncEmitter<CurrencyConverter>(
        getterLifetime = currencyConverterProvider.actualConverterLifetime
) {

    override fun getNewGetter() =
            SuspendGetter.simple(currencyConverterProvider::getCurrencyConverter)

}