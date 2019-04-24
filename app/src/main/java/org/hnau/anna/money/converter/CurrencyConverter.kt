package org.hnau.anna.money.converter

import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import ru.hnau.jutils.TimeValue

/**
 * Конвертер валют
 */
interface CurrencyConverter {

    //Доступные для конвертирования валюты
    val availableCurrencies: Set<Currency>

    //Выполнить конвертацию
    fun convert(from: Currency, to: Currency, value: Money): Money

}