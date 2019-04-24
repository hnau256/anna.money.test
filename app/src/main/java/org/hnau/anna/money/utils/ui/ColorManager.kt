package org.hnau.anna.money.utils.ui

import org.hnau.anna.money.R
import ru.hnau.androidutils.context_getters.ColorGetter
import ru.hnau.androidutils.context_getters.dp_px.dp2
import ru.hnau.androidutils.context_getters.dp_px.dp4
import ru.hnau.androidutils.context_getters.dp_px.dp8
import ru.hnau.androidutils.ui.drawer.ripple.info.RippleDrawInfo
import ru.hnau.androidutils.ui.drawer.ripple.info.RippleInfo
import ru.hnau.androidutils.ui.drawer.shadow.info.ButtonShadowInfo
import ru.hnau.androidutils.ui.drawer.shadow.info.ShadowInfo
import ru.hnau.jutils.TimeValue


object ColorManager {

    val PRIMARY = ColorGetter.byResId(R.color.primary)
    val PRIMARY_DARK = ColorGetter.byResId(R.color.primary_dark)

    val WHITE = ColorGetter.byResId(R.color.white)
    val BLACK = ColorGetter.byResId(R.color.black)
    val GREY = ColorGetter.byResId(R.color.grey)

    val RIPPLE_INFO = RippleInfo()

    const val RIPPLE_ALPHA = 0.15f

    val DEFAULT_SHADOW_INFO = ShadowInfo(dp4, dp8, ColorGetter.BLACK, 0.4f)
    val DEFAULT_PRESSED_SHADOW_INFO = ShadowInfo(dp2, dp4, ColorGetter.BLACK, 0.4f)
    val DEFAULT_BUTTON_SHADOW_INFO = ButtonShadowInfo(
            normal = DEFAULT_SHADOW_INFO,
            pressed = DEFAULT_PRESSED_SHADOW_INFO,
            animationTime = TimeValue.MILLISECOND * 100
    )

}