package org.hnau.anna.money.data

import java.math.BigDecimal


//Обертка над BigDecimal. Должен быть inline class но в таком случае MvpMoxy неверно выполняет кодогенерацию
class Money(
        val value: BigDecimal
) {

    companion object {

        val ZERO = Money("0")

    }

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