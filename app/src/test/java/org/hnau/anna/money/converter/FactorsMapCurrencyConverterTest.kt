package org.hnau.anna.money.converter

import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.math.BigInteger

class FactorsMapCurrencyConverterTest {

    @Test
    fun getAvailableCurrencies() {

        val currencies = setOf(
                Currency.EUR,
                Currency.USD,
                Currency.RUB
        )

        val converter = FactorsMapCurrencyConverter(
                factorsMap = currencies.associate { currency ->
                    currency to BigDecimal.ONE
                }
        )

        val actual = converter.availableCurrencies
        assertEquals(currencies, actual)

    }

    @Test
    fun convert() {

        val converter = FactorsMapCurrencyConverter(
                factorsMap = mapOf(
                        Currency.AUD to BigDecimal("12.65"),
                        Currency.DKK to BigDecimal("0.56")
                )
        )

        val input = Money("14.24")
        val actual = converter.convert(Currency.AUD, Currency.DKK, input)
        val expected = Money("0.6304")

        assertEquals(expected, actual)
    }
}