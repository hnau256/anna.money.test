package ru.hnau.remote_teaching_android.ui.button

import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.ColorGetter
import ru.hnau.androidutils.context_getters.dp_px.*
import ru.hnau.androidutils.context_getters.dp_px.DpPxGetter.Companion.dp
import ru.hnau.androidutils.ui.drawer.border.BorderInfo
import ru.hnau.androidutils.ui.drawer.ripple.info.RippleDrawInfo
import ru.hnau.androidutils.ui.drawer.shadow.info.ButtonShadowInfo
import ru.hnau.androidutils.ui.font_type.FontTypeGetter
import ru.hnau.androidutils.ui.utils.ScreenManager.height


data class AMButtonInfo(
        val textSize: DpPxGetter,
        val shadow: ButtonShadowInfo? = null,
        val height: DpPxGetter,
        val paddingHorizontal: DpPxGetter = height,
        val font: FontTypeGetter = FontManager.DEFAULT,
        val borderInfo: BorderInfo? = null,
        val textColor: ColorGetter,
        val underline: Boolean = false,
        val backgroundColor: ColorGetter
) {

    companion object {

        val PRIMARY = AMButtonInfo(
                shadow = ColorManager.DEFAULT_BUTTON_SHADOW_INFO,
                textSize = dp(20),
                height = dp48,
                textColor = ColorManager.PRIMARY_DARK,
                backgroundColor = ColorManager.PRIMARY,
                borderInfo = createBorderInfo(ColorManager.PRIMARY_DARK)
        )

        val SECONDARY = AMButtonInfo(
                textSize = dp16,
                height = dp32,
                textColor = ColorManager.PRIMARY_DARK,
                backgroundColor = ColorManager.TRANSPARENT,
                borderInfo = createBorderInfo(ColorManager.PRIMARY_DARK)
        )

        private fun createBorderInfo(
                color: ColorGetter
        ) = BorderInfo(
                color = color,
                alpha = 1f,
                width = dp2
        )

    }

    val rippleDrawInfo = RippleDrawInfo(
            rippleInfo = ColorManager.RIPPLE_INFO,
            backgroundColor = backgroundColor,
            color = textColor
    )

}