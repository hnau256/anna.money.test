package org.hnau.anna.money.model.converting

import org.hnau.anna.money.R
import org.hnau.anna.money.converter.CurrencyConverter
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.model.converting.exception.CurrencyConvertingException
import org.hnau.anna.money.model.converting.exception.CurrencyConvertingExceptionWithAdditionalAction
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.takeIfNotEmpty
import ru.hnau.jutils.tryOrElse

/**
 * Вычисление результата конвертации на основе полученного конвертера,
 * выбранных пользователем исходной и конечной валют
 * и введеной пользователем суммы
 */
object Converter {

    private val NO_CONVERTER_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_converter)
    private val NO_CONVERTER_ACTION_TITLE = StringGetter(R.string.converting_error_no_converter_action)
    private val NO_FROM_CURRENCY_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_from_currency)
    private val NO_TO_CURRENCY_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_to_currency)
    private val NO_FROM_MONEY_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_from_money)
    private val INCORRECT_FROM_MONEY_ERROR_MESSAGE = StringGetter(R.string.converting_error_incorrect_from_money)

    @Throws(CurrencyConvertingException::class)
    fun convert(
            possibleCurrencyConverter: Possible<CurrencyConverter>,
            fromCurrencyBox: Box<Currency?>,
            toCurrencyBox: Box<Currency?>,
            fromMoneyString: String,
            invalidateConverter: () -> Unit
    ): Money {

        val currencyConverter = possibleCurrencyConverter.data
                ?: throw CurrencyConvertingExceptionWithAdditionalAction(
                        text = NO_CONVERTER_ERROR_MESSAGE,
                        actionTitle = NO_CONVERTER_ACTION_TITLE,
                        action = invalidateConverter
                )

        val fromCurrency = fromCurrencyBox.value
                ?: throw CurrencyConvertingException(NO_FROM_CURRENCY_ERROR_MESSAGE)

        val toCurrency = toCurrencyBox.value
                ?: throw CurrencyConvertingException(NO_TO_CURRENCY_ERROR_MESSAGE)

        val fromMoney = fromMoneyStringToMoney(fromMoneyString)

        return currencyConverter.convert(fromCurrency, toCurrency, fromMoney)
    }


    @Throws(CurrencyConvertingException::class)
    private fun fromMoneyStringToMoney(
            fromMoneyString: String
    ): Money {
        val formattedFromMoneyString = fromMoneyString
                .replace(",", ".")
                .filter { it.isDigit() || it == '.' }
                .takeIfNotEmpty()
                ?: throw CurrencyConvertingException(NO_FROM_MONEY_ERROR_MESSAGE)
        return tryOrElse(
                throwsAction = {
                    Money(formattedFromMoneyString)
                },
                onThrow = {
                    throw CurrencyConvertingException(INCORRECT_FROM_MONEY_ERROR_MESSAGE)
                }
        )
    }

}