package org.hnau.anna.money.data

import org.hnau.anna.money.R
import ru.hnau.androidutils.context_getters.StringGetter


enum class Currency(
        val key: String,
        val title: StringGetter,
        val sign: String? = null
) {


    USD(
            key = "USD",
            title = StringGetter(R.string.currency_name_USD),
            sign = "\$"
    ),


    EUR(
            key = "EUR",
            title = StringGetter(R.string.currency_name_EUR),
            sign = "€"
    ),

    JPY(
            key = "JPY",
            title = StringGetter(R.string.currency_name_JPY),
            sign = "¥"
    ),

    BGN(
            key = "BGN",
            title = StringGetter(R.string.currency_name_BGN)
    ),

    CZK(
            key = "CZK",
            title = StringGetter(R.string.currency_name_CZK)
    ),

    DKK(
            key = "DKK",
            title = StringGetter(R.string.currency_name_DKK)
    ),

    GBP(
            key = "GBP",
            title = StringGetter(R.string.currency_name_GBP),
            sign = "₤"
    ),

    HUF(
            key = "HUF",
            title = StringGetter(R.string.currency_name_HUF),
            sign = "ƒ"
    ),

    PLN(
            key = "PLN",
            title = StringGetter(R.string.currency_name_PLN)
    ),

    RON(
            key = "RON",
            title = StringGetter(R.string.currency_name_RON)
    ),

    SEK(
            key = "SEK",
            title = StringGetter(R.string.currency_name_SEK)
    ),

    CHF(
            key = "CHF",
            title = StringGetter(R.string.currency_name_CHF)
    ),

    ISK(
            key = "ISK",
            title = StringGetter(R.string.currency_name_ISK)
    ),

    NOK(
            key = "NOK",
            title = StringGetter(R.string.currency_name_NOK)
    ),

    HRK(
            key = "HRK",
            title = StringGetter(R.string.currency_name_HRK)
    ),

    RUB(
            key = "RUB",
            title = StringGetter(R.string.currency_name_RUB),
            sign = "\u20BD"
    ),

    TRY(
            key = "TRY",
            title = StringGetter(R.string.currency_name_TRY),
            sign = "₺"
    ),

    AUD(
            key = "AUD",
            title = StringGetter(R.string.currency_name_AUD),
            sign = "\$"
    ),

    BRL(
            key = "BRL",
            title = StringGetter(R.string.currency_name_BRL),
            sign = "\$"
    ),

    CAD(
            key = "CAD",
            title = StringGetter(R.string.currency_name_CAD),
            sign = "\$"
    ),

    CNY(
            key = "CNY",
            title = StringGetter(R.string.currency_name_CNY),
            sign = "¥"
    ),

    HKD(
            key = "HKD",
            title = StringGetter(R.string.currency_name_HKD),
            sign = "\$"
    ),

    IDR(
            key = "IDR",
            title = StringGetter(R.string.currency_name_IDR),
            sign = "₨"
    ),

    ILS(
            key = "ILS",
            title = StringGetter(R.string.currency_name_ILS),
            sign = "₪"
    ),

    INR(
            key = "INR",
            title = StringGetter(R.string.currency_name_INR),
            sign = "₹"
    ),

    KRW(
            key = "KRW",
            title = StringGetter(R.string.currency_name_KRW),
            sign = "₩"
    ),

    MXN(
            key = "MXN",
            title = StringGetter(R.string.currency_name_MXN),
            sign = "\$"
    ),

    MYR(
            key = "MYR",
            title = StringGetter(R.string.currency_name_MYR)
    ),

    NZD(
            key = "NZD",
            title = StringGetter(R.string.currency_name_NZD),
            sign = "\$"
    ),

    PHP(
            key = "PHP",
            title = StringGetter(R.string.currency_name_PHP),
            sign = "₱"
    ),

    SGD(
            key = "SGD",
            title = StringGetter(R.string.currency_name_SGD),
            sign = "\$"
    ),

    THB(
            key = "THB",
            title = StringGetter(R.string.currency_name_THB),
            sign = "฿"
    ),

    ZAR(
            key = "ZAR",
            title = StringGetter(R.string.currency_name_ZAR)
    );

    companion object {

        fun findByName(name: String) =
                values().find { it.name.equals(name, true) }

    }

    val signOrKey: String
        get() = sign ?: key

}