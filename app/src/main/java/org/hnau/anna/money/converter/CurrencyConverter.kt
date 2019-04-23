package org.hnau.anna.money.converter

import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import ru.hnau.jutils.TimeValue


interface CurrencyConverter {

    val availableCurrencies: Set<Currency>

    fun convert(from: Currency, to: Currency, value: Money): Money

}