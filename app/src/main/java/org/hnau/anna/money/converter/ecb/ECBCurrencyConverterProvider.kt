package org.hnau.anna.money.converter.ecb

import org.hnau.anna.money.converter.CurrencyConverter
import org.hnau.anna.money.converter.provider.CurrencyConverterProvider
import org.hnau.anna.money.converter.ecb.api.ECBCurrencyRateGetter
import ru.hnau.jutils.TimeValue


class ECBCurrencyConverterProvider : CurrencyConverterProvider(
        actualConverterLifetime = CONVERTER_LIFETIME
) {

    companion object {

        private val CONVERTER_LIFETIME = TimeValue.SECOND * 30

    }

    override suspend fun getCurrencyConverter(): CurrencyConverter {
        val ecbData = ECBCurrencyRateGetter.INSTANCE.getCurrencyRate().await()
        return ECBCurrencyConverter(ecbData)
    }

}