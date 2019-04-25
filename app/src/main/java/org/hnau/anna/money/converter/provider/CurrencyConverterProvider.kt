package org.hnau.anna.money.converter.provider

import org.hnau.anna.money.converter.CurrencyConverter
import ru.hnau.jutils.TimeValue


/**
 * Получает CurrencyConverter асинхронно [actualConverterLifetime] время актуальности полученного CurrencyConverter
 */
abstract class CurrencyConverterProvider(
        val actualConverterLifetime: TimeValue
) {

    suspend abstract fun getCurrencyConverter(): CurrencyConverter

}