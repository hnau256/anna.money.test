package org.hnau.anna.money.model.converting.exception

import ru.hnau.androidutils.context_getters.StringGetter
import java.lang.RuntimeException


open class CurrencyConvertingException(
        val text: StringGetter
) : RuntimeException()