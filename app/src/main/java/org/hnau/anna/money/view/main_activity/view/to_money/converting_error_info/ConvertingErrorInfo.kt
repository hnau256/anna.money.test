package org.hnau.anna.money.view.main_activity.view.to_money.converting_error_info

import org.hnau.anna.money.model.converting.exception.ConvertingException
import org.hnau.anna.money.model.converting.exception.ConvertingExceptionWithAction
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
                    exception as? ConvertingException
                            ?: throw exception

            val actionException =
                    convertingException as? ConvertingExceptionWithAction

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