package org.hnau.anna.money.view.main_activity.view

import android.content.Context
import org.hnau.anna.money.R
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.androidutils.context_getters.dp_px.dp16
import ru.hnau.androidutils.context_getters.dp_px.dp24
import ru.hnau.androidutils.ui.utils.h_gravity.HGravity
import ru.hnau.androidutils.ui.view.addLinearSeparator
import ru.hnau.androidutils.ui.view.header.Header
import ru.hnau.androidutils.ui.view.header.addHeaderTitle
import ru.hnau.androidutils.ui.view.utils.apply.layout_params.applyLinearParams
import ru.hnau.androidutils.ui.view.waiter.material.addMaterialWaiter
import ru.hnau.androidutils.ui.view.waiter.material.drawer.params.MaterialWaiterColor
import ru.hnau.androidutils.ui.view.waiter.material.drawer.params.MaterialWaiterSize
import ru.hnau.jutils.producer.locked_producer.LockedProducer


class MainActivityViewHeader(
        context: Context,
        operationIsInProgess: LockedProducer
) : Header(
        context = context,
        underStatusBar = false,
        headerBackgroundColor = ColorManager.PRIMARY_DARK
) {

    init {

        addHeaderTitle(
                text = StringGetter(R.string.app_name),
                textSize = dp24,
                gravity = HGravity.START_CENTER_VERTICAL,
                textColor = ColorManager.WHITE,
                maxLines = 1,
                minLines = 1,
                fontType = FontManager.REGULAR
        )

        addLinearSeparator()

        addMaterialWaiter(
                lockedProducer = operationIsInProgess,
                size = MaterialWaiterSize.SMALL,
                color = MaterialWaiterColor.WHITE_ON_TRANSPARENT
        ) {
            applyLinearParams { setEndMargin(dp16) }
        }

    }

}