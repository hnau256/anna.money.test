package org.hnau.anna.money.utils.ui.view.error_info

import org.hnau.anna.money.model.converting.exception.CurrencyConvertingException
import org.hnau.anna.money.model.converting.exception.CurrencyConvertingExceptionWithAdditionalAction
import ru.hnau.androidutils.context_getters.StringGetter
import java.lang.Exception


data class ConvertingErrorInfo(
        val title: StringGetter,
        val action: Action?
) {

    data class Action(
            val title: StringGetter,
            val onClick: () -> Unit
    )

    companion object {

        fun create(
                exception: Exception
        ): ConvertingErrorInfo {

            val convertingException =
                    exception as? CurrencyConvertingException
                            ?: throw exception

            val actionException =
                    convertingException as? CurrencyConvertingExceptionWithAdditionalAction

            return ConvertingErrorInfo(
                    title = convertingException.text,
                    action = actionException?.let {
                        Action(
                                title = it.actionTitle,
                                onClick = it.action
                        )
                    }
            )

        }

    }

}