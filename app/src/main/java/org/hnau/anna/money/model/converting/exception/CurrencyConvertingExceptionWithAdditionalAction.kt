package org.hnau.anna.money.model.converting.exception

import ru.hnau.androidutils.context_getters.StringGetter


class CurrencyConvertingExceptionWithAdditionalAction(
        text: StringGetter,
        val actionTitle: StringGetter,
        val action: () -> Unit
) : CurrencyConvertingException(
        text
)