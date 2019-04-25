package org.hnau.anna.money.converter

import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import java.lang.IllegalArgumentException
import java.math.BigDecimal


/**
 * CurrencyConverter, который выполняет конвертацию на основе коэффициентов между валютами
 */
open class FactorsMapCurrencyConverter(
        private val factorsMap: Map<Currency, BigDecimal>
) : CurrencyConverter {

    override val availableCurrencies = factorsMap.keys

    override fun convert(from: Currency, to: Currency, value: Money): Money {
        val fromFactor = factorsMap[from] ?: throwUnavailableCurrencyException(from)
        val toFactor = factorsMap[to] ?: throwUnavailableCurrencyException(to)
        val factor = calcFactor(fromFactor, toFactor)
        val result = value * factor
        return result
    }

    private fun calcFactor(
            fromFactor: BigDecimal,
            toFactor: BigDecimal
    ): BigDecimal {
        val scale = fromFactor.scale() + toFactor.scale()
        return toFactor.setScale(scale) / fromFactor.setScale(scale)
    }

    private fun throwUnavailableCurrencyException(
            currency: Currency
    ): Nothing {
        throw IllegalArgumentException("Currency $currency not available")
    }

}