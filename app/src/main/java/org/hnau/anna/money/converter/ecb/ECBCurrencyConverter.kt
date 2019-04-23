package org.hnau.anna.money.converter.ecb

import org.hnau.anna.money.converter.FactorsMapCurrencyConverter
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.converter.ecb.api.entity.ECBData
import org.hnau.anna.money.converter.ecb.api.entity.ECBItem
import ru.hnau.androidutils.ui.utils.logW
import ru.hnau.jutils.TimeValue
import java.math.BigDecimal


class ECBCurrencyConverter(
        ecbData: ECBData
) : FactorsMapCurrencyConverter(
        factorsMap = (ecbData
                .items
                ?.items
                ?.mapNotNull(this::ecbEuropaEuItemToItem)
                ?.associate { it }
                ?: emptyMap()
                ) + EUR_MAP
) {

    companion object {

        private val EUR_MAP = mapOf(Currency.EUR to BigDecimal.ONE)

        private fun ecbEuropaEuItemToItem(
                ECBItem: ECBItem
        ): Pair<Currency, BigDecimal>? {
            val currencyName = ECBItem.currency
            val currency = Currency.findByName(currencyName)
            if (currency == null) {
                ECBCurrencyConverter.logW("Currency $currencyName not found")
                return null
            }
            return currency to ECBItem.rate
        }

    }

}