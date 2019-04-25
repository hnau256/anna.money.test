package org.hnau.anna.money.model.converting

import org.hnau.anna.money.converter.FactorsMapCurrencyConverter
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.model.converting.exception.ConvertingException
import org.junit.Assert.*
import org.junit.Test
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.tryOrElse
import java.math.BigDecimal

class ConverterTest {

    @Test
    fun testConvertion() {

        val currencyConverter = FactorsMapCurrencyConverter(
                factorsMap = mapOf(
                        Currency.EUR to BigDecimal("1.2"),
                        Currency.BGN to BigDecimal("1.5")
                )
        )

        checkConvertion(Converter.INCORRECT_FROM_MONEY_ERROR_MESSAGE) {
            Converter.convert(
                    possibleCurrencyConverter = Possible.success(currencyConverter),
                    fromCurrencyBox = Box(Currency.EUR),
                    toCurrencyBox = Box(Currency.BGN),
                    fromMoneyString = "a",
                    invalidateConverter = {}
            )
        }

        checkConvertion(Converter.NO_FROM_MONEY_ERROR_MESSAGE) {
            Converter.convert(
                    possibleCurrencyConverter = Possible.success(currencyConverter),
                    fromCurrencyBox = Box(Currency.EUR),
                    toCurrencyBox = Box(Currency.BGN),
                    fromMoneyString = "",
                    invalidateConverter = {}
            )
        }

        checkConvertion(Converter.NO_FROM_CURRENCY_ERROR_MESSAGE) {
            Converter.convert(
                    possibleCurrencyConverter = Possible.success(currencyConverter),
                    fromCurrencyBox = Box(null),
                    toCurrencyBox = Box(Currency.BGN),
                    fromMoneyString = "1.0",
                    invalidateConverter = {}
            )
        }

        checkConvertion(Converter.NO_TO_CURRENCY_ERROR_MESSAGE) {
            Converter.convert(
                    possibleCurrencyConverter = Possible.success(currencyConverter),
                    fromCurrencyBox = Box(Currency.EUR),
                    toCurrencyBox = Box(null),
                    fromMoneyString = "1.0",
                    invalidateConverter = {}
            )
        }

        checkConvertion(Converter.NO_CONVERTER_ERROR_MESSAGE) {
            Converter.convert(
                    possibleCurrencyConverter = Possible.error(),
                    fromCurrencyBox = Box(Currency.EUR),
                    toCurrencyBox = Box(Currency.BGN),
                    fromMoneyString = "1.0",
                    invalidateConverter = {}
            )
        }

        checkConvertion(Money("1.25")) {
            Converter.convert(
                    possibleCurrencyConverter = Possible.success(currencyConverter),
                    fromCurrencyBox = Box(Currency.EUR),
                    toCurrencyBox = Box(Currency.BGN),
                    fromMoneyString = "1.0",
                    invalidateConverter = {}
            )
        }

    }

    private fun checkConvertion(
            expected: Any,
            action: () -> Any
    ) {
        val result = tryOrElse(
                throwsAction = action,
                onThrow = { exception ->
                    (exception as ConvertingException).text
                }
        )
        assertEquals(expected, result)
    }

}