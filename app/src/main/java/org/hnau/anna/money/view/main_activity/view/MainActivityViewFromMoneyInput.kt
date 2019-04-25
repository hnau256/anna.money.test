package org.hnau.anna.money.view.main_activity.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import io.reactivex.Observable
import org.hnau.anna.money.R
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.dp_px.dp32
import ru.hnau.androidutils.ui.view.utils.apply.applyHorizontalPadding
import ru.hnau.androidutils.ui.view.utils.apply.applyPadding
import ru.hnau.androidutils.ui.view.utils.apply.applyStartPadding
import ru.hnau.androidutils.ui.view.utils.setPadding
import ru.hnau.androidutils.utils.ContextConnector.context
import ru.hnau.jutils.ifTrue


class MainActivityViewFromMoneyInput(
        context: Context,
        valueObservable: Observable<String>,
        private val onValueChanged: (String) -> Unit
) : EditText(
        context
) {

    init {
        background = null
        applyHorizontalPadding(dp32)
        typeface = FontManager.DEFAULT.get(context).typeface
        setTextColor(ColorManager.BLACK.get(context))
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dp32.getPx(context))
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        setSingleLine()
        setHintTextColor(ColorManager.GREY.get(context))
        setHint(R.string.from_money_input_hint)
        gravity = Gravity.CENTER

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = onTextChanged(s?.toString() ?: "")
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        valueObservable.subscribe { newText ->
            if (text.toString() != newText) {
                setText(newText)
            }
        }
    }

    private fun onTextChanged(text: String) {
        isAttachedToWindow.ifTrue { onValueChanged(text) }
    }

}