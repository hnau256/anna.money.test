package org.hnau.anna.money.converter

import ru.hnau.jutils.TimeValue


interface CurrencyConverterProvider {

    val actualConverterLifetime: TimeValue

    suspend fun getCurrencyConverter(): CurrencyConverter

}