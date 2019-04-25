package org.hnau.anna.money.model.converting

import org.hnau.anna.money.R
import org.hnau.anna.money.converter.CurrencyConverter
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.model.converting.exception.ConvertingException
import org.hnau.anna.money.model.converting.exception.ConvertingExceptionWithAction
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

    val NO_CONVERTER_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_converter)
    val NO_CONVERTER_ACTION_TITLE = StringGetter(R.string.converting_error_no_converter_action)
    val NO_FROM_CURRENCY_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_from_currency)
    val NO_TO_CURRENCY_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_to_currency)
    val NO_FROM_MONEY_ERROR_MESSAGE = StringGetter(R.string.converting_error_no_from_money)
    val INCORRECT_FROM_MONEY_ERROR_MESSAGE = StringGetter(R.string.converting_error_incorrect_from_money)

    @Throws(ConvertingException::class)
    fun convert(
            possibleCurrencyConverter: Possible<CurrencyConverter>,
            fromCurrencyBox: Box<Currency?>,
            toCurrencyBox: Box<Currency?>,
            fromMoneyString: String,
            invalidateConverter: () -> Unit
    ): Money {

        val currencyConverter = possibleCurrencyConverter.data
                ?: throw ConvertingExceptionWithAction(
                        text = NO_CONVERTER_ERROR_MESSAGE,
                        actionTitle = NO_CONVERTER_ACTION_TITLE,
                        action = invalidateConverter
                )

        val fromCurrency = fromCurrencyBox.value
                ?: throw ConvertingException(NO_FROM_CURRENCY_ERROR_MESSAGE)

        val toCurrency = toCurrencyBox.value
                ?: throw ConvertingException(NO_TO_CURRENCY_ERROR_MESSAGE)

        val fromMoney = fromMoneyStringToMoney(fromMoneyString)

        return currencyConverter.convert(fromCurrency, toCurrency, fromMoney)
    }


    @Throws(ConvertingException::class)
    private fun fromMoneyStringToMoney(
            fromMoneyString: String
    ): Money {
        val formattedFromMoneyString = fromMoneyString
                .replace(",", ".")
                .filterNot(Char::isWhitespace)
                .takeIfNotEmpty()
                ?: throw ConvertingException(NO_FROM_MONEY_ERROR_MESSAGE)
        return tryOrElse(
                throwsAction = {
                    Money(formattedFromMoneyString)
                },
                onThrow = {
                    throw ConvertingException(INCORRECT_FROM_MONEY_ERROR_MESSAGE)
                }
        )
    }

}