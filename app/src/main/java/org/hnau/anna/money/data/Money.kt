package org.hnau.anna.money.data

import java.math.BigDecimal


inline class Money(
        val value: BigDecimal
) {

    constructor(
            value: String
    ) : this(
            BigDecimal(value)
    )

    fun toString(currency: Currency) =
            "$value ${currency.signOrKey}"

    operator fun times(factor: BigDecimal) =
            Money(value * factor)

    operator fun div(factor: BigDecimal) =
            Money(value / factor)

}