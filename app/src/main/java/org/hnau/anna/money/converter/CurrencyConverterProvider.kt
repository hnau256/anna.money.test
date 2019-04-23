package org.hnau.anna.money.converter

import ru.hnau.jutils.TimeValue


abstract class CurrencyConverterProvider(
        val actualConverterLifetime: TimeValue
) {

    suspend abstract fun getCurrencyConverter(): CurrencyConverter

}