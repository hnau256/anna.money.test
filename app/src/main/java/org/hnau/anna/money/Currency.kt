package org.hnau.anna.money

import ru.hnau.androidutils.context_getters.StringGetter


enum class Currency(
        val key: String,
        val title: StringGetter,
        val flag: String,
        val sign: String? = null
) {


    USD(
            key = "USD",
            title = StringGetter(R.string.currency_name_USD),
            flag = "",
            sign = ""
    ),

    JPY(
            key = "JPY",
            title = StringGetter(R.string.currency_name_JPY),
            flag = "",
            sign = ""
    ),

    BGN(
            key = "BGN",
            title = StringGetter(R.string.currency_name_BGN),
            flag = "",
            sign = ""
    ),

    CZK(
            key = "CZK",
            title = StringGetter(R.string.currency_name_CZK),
            flag = "",
            sign = ""
    ),

    DKK(
            key = "DKK",
            title = StringGetter(R.string.currency_name_DKK),
            flag = "",
            sign = ""
    ),

    GBP(
            key = "GBP",
            title = StringGetter(R.string.currency_name_GBP),
            flag = "",
            sign = ""
    ),

    HUF(
            key = "HUF",
            title = StringGetter(R.string.currency_name_HUF),
            flag = "",
            sign = ""
    ),

    PLN(
            key = "PLN",
            title = StringGetter(R.string.currency_name_PLN),
            flag = "",
            sign = ""
    ),

    RON(
            key = "RON",
            title = StringGetter(R.string.currency_name_RON),
            flag = "",
            sign = ""
    ),

    SEK(
            key = "SEK",
            title = StringGetter(R.string.currency_name_SEK),
            flag = "",
            sign = ""
    ),

    CHF(
            key = "CHF",
            title = StringGetter(R.string.currency_name_CHF),
            flag = "",
            sign = ""
    ),

    ISK(
            key = "ISK",
            title = StringGetter(R.string.currency_name_ISK),
            flag = "",
            sign = ""
    ),

    NOK(
            key = "NOK",
            title = StringGetter(R.string.currency_name_NOK),
            flag = "",
            sign = ""
    ),

    HRK(
            key = "HRK",
            title = StringGetter(R.string.currency_name_HRK),
            flag = "",
            sign = ""
    ),

    RUB(
            key = "RUB",
            title = StringGetter(R.string.currency_name_RUB),
            flag = "",
            sign = ""
    ),

    TRY(
            key = "TRY",
            title = StringGetter(R.string.currency_name_TRY),
            flag = "",
            sign = ""
    ),

    AUD(
            key = "AUD",
            title = StringGetter(R.string.currency_name_AUD),
            flag = "",
            sign = ""
    ),

    BRL(
            key = "BRL",
            title = StringGetter(R.string.currency_name_BRL),
            flag = "",
            sign = ""
    ),

    CAD(
            key = "CAD",
            title = StringGetter(R.string.currency_name_CAD),
            flag = "",
            sign = ""
    ),

    CNY(
            key = "CNY",
            title = StringGetter(R.string.currency_name_CNY),
            flag = "",
            sign = ""
    ),

    HKD(
            key = "HKD",
            title = StringGetter(R.string.currency_name_HKD),
            flag = "",
            sign = ""
    ),

    IDR(
            key = "IDR",
            title = StringGetter(R.string.currency_name_IDR),
            flag = "",
            sign = ""
    ),

    ILS(
            key = "ILS",
            title = StringGetter(R.string.currency_name_ILS),
            flag = "",
            sign = ""
    ),

    INR(
            key = "INR",
            title = StringGetter(R.string.currency_name_INR),
            flag = "",
            sign = ""
    ),

    KRW(
            key = "KRW",
            title = StringGetter(R.string.currency_name_KRW),
            flag = "",
            sign = ""
    ),

    MXN(
            key = "MXN",
            title = StringGetter(R.string.currency_name_MXN),
            flag = "",
            sign = ""
    ),

    MYR(
            key = "MYR",
            title = StringGetter(R.string.currency_name_MYR),
            flag = "",
            sign = ""
    ),

    NZD(
            key = "NZD",
            title = StringGetter(R.string.currency_name_NZD),
            flag = "",
            sign = ""
    ),

    PHP(
            key = "PHP",
            title = StringGetter(R.string.currency_name_PHP),
            flag = "",
            sign = ""
    ),

    SGD(
            key = "SGD",
            title = StringGetter(R.string.currency_name_SGD),
            flag = "",
            sign = ""
    ),

    THB(
            key = "THB",
            title = StringGetter(R.string.currency_name_THB),
            flag = "",
            sign = ""
    ),

    ZAR(
            key = "ZAR",
            title = StringGetter(R.string.currency_name_ZAR),
            flag = "",
            sign = ""
    ),


}