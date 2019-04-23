package org.hnau.anna.money.converter.ecb

import org.hnau.anna.money.converter.CurrencyConverter
import org.hnau.anna.money.converter.CurrencyConverterProvider
import org.hnau.anna.money.converter.ecb.api.ECBCurrencyRateGetter
import ru.hnau.jutils.TimeValue


class ECBCurrencyConverterProvider : CurrencyConverterProvider {

    companion object {

        private val ACTUAL_CONVERTER_LIFETIME = TimeValue.SECOND * 30

    }

    override val actualConverterLifetime = ACTUAL_CONVERTER_LIFETIME

    override suspend fun getCurrencyConverter(): CurrencyConverter {
        val ecbData = ECBCurrencyRateGetter.INSTANCE.getCurrencyRate().await()
        return ECBCurrencyConverter(ecbData)
    }

}