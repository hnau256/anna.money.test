package org.hnau.anna.money.converter

import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import java.lang.IllegalArgumentException
import java.math.BigDecimal


open class FactorsMapCurrencyConverter(
        private val factorsMap: Map<Currency, BigDecimal>
) : CurrencyConverter {

    override val availableCurrencies = factorsMap.keys

    override fun convert(from: Currency, to: Currency, value: Money): Money {
        val fromFactor = factorsMap[from] ?: throwUnavailableCurrencyException(from)
        val toFactor = factorsMap[to] ?: throwUnavailableCurrencyException(to)
        return value * toFactor / fromFactor
    }

    private fun throwUnavailableCurrencyException(
            currency: Currency
    ): Nothing {
        throw IllegalArgumentException("Currency $currency not available")
    }

}