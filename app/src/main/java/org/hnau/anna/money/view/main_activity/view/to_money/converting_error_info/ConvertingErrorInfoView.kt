package org.hnau.anna.money.view.main_activity.view.to_money.converting_error_info

import android.content.Context
import android.widget.LinearLayout
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.dp_px.dp16
import ru.hnau.androidutils.context_getters.dp_px.dp32
import ru.hnau.androidutils.ui.utils.h_gravity.HGravity
import ru.hnau.androidutils.ui.view.label.Label
import ru.hnau.androidutils.ui.view.utils.apply.addChild
import ru.hnau.androidutils.ui.view.utils.apply.applyCenterGravity
import ru.hnau.androidutils.ui.view.utils.apply.applyPadding
import ru.hnau.androidutils.ui.view.utils.apply.applyVerticalOrientation
import ru.hnau.androidutils.ui.view.utils.apply.layout_params.applyLinearParams
import ru.hnau.remote_teaching_android.ui.button.AMButton
import ru.hnau.remote_teaching_android.ui.button.AMButtonInfo


class ConvertingErrorInfoView(
        context: Context,
        infoView: ConvertingErrorInfo
) : LinearLayout(context) {

    private val titleView = Label(
            context = context,
            textSize = dp16,
            fontType = FontManager.REGULAR,
            gravity = HGravity.CENTER,
            textColor = ColorManager.PRIMARY_DARK,
            initialText = infoView.title
    )

    private val buttonView = infoView.action?.let { action ->
        AMButton(
                context = context,
                onClick = action.onClick,
                text = action.title,
                info = AMButtonInfo.SECONDARY
        )
                .applyLinearParams { setTopMargin(dp16) }
    }


    init {
        applyVerticalOrientation()
        applyCenterGravity()
        applyPadding(dp32, dp16)

        addChild(titleView)
        addChild(buttonView)
    }

}