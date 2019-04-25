package org.hnau.anna.money.model.converting.exception

import ru.hnau.androidutils.context_getters.StringGetter


class ConvertingExceptionWithAction(
        text: StringGetter,
        val actionTitle: StringGetter,
        val action: () -> Unit
) : ConvertingException(
        text
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConvertingExceptionWithAction) return false
        if (!super.equals(other)) return false
        if (actionTitle != other.actionTitle) return false
        if (action != other.action) return false
        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + actionTitle.hashCode()
        result = 31 * result + action.hashCode()
        return result
    }
}