package org.hnau.anna.money.model.converting.exception

import ru.hnau.androidutils.context_getters.StringGetter
import java.lang.RuntimeException


open class ConvertingException(
        val text: StringGetter
) : RuntimeException() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConvertingException) return false
        if (text != other.text) return false
        return true
    }

    override fun hashCode() =
            text.hashCode()

}