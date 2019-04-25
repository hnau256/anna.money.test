package org.hnau.anna.money.view.main_activity.view.to_money

import android.content.Context
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import io.reactivex.Observable
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.utils.rx.prorogue
import org.hnau.anna.money.utils.rx.toProducer
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import org.hnau.anna.money.view.main_activity.view.to_money.converting_error_info.ConvertingErrorInfo
import org.hnau.anna.money.view.main_activity.view.to_money.converting_error_info.ConvertingErrorInfoView
import ru.hnau.androidutils.context_getters.dp_px.dp32
import ru.hnau.androidutils.context_getters.toGetter
import ru.hnau.androidutils.ui.utils.Side
import ru.hnau.androidutils.ui.utils.h_gravity.HGravity
import ru.hnau.androidutils.ui.view.label.Label
import ru.hnau.androidutils.ui.view.view_presenter.PresenterView
import ru.hnau.androidutils.ui.view.view_presenter.PresentingViewInfo
import ru.hnau.androidutils.ui.view.view_presenter.PresentingViewProperties
import ru.hnau.androidutils.ui.view.view_presenter.toPresentingInfo
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.handle
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.producer.extensions.filterUnique
import java.lang.Exception

/**
 * Отбражение результата конвертации или ошибки
 */
class ToMoneyView(
        context: Context,
        toMoneyObservable: Observable<Possible<Money>>
) : PresenterView(
        context = context,
        presentingViewProducer = toMoneyObservable
                .toProducer()
                .filterUnique()
                .prorogue(
                        pause = TimeValue.ZERO,
                        handler = Handler()
                )
                .map { possibleToMoney ->
                    toMoneyToPresentingViewInfo(context, possibleToMoney)
                }
) {

    companion object {

        private val PRESENTING_VIEW_PROPERTIES = PresentingViewProperties(
                fromSide = Side.END,
                scrollFactor = 0.1f,
                animatingTime = TimeValue.MILLISECOND * 200,
                showInterpolator = LinearInterpolator()
        )

        fun toMoneyToPresentingViewInfo(
                context: Context,
                possibleToMoney: Possible<Money>
        ): PresentingViewInfo {
            val view = possibleToMoney.handle(
                    onSuccess = { toMoney -> createToMoneyLabrl(context, toMoney) },
                    onError = { error -> createErrorView(context, error) }
            )
            return view.toPresentingInfo(PRESENTING_VIEW_PROPERTIES)
        }

        private fun createToMoneyLabrl(
                context: Context,
                toMoney: Money
        ) = Label(
                context = context,
                initialText = toMoney.value.toString().toGetter(),
                textColor = ColorManager.BLACK,
                gravity = HGravity.CENTER,
                textSize = dp32,
                minLines = 1,
                maxLines = 1,
                fontType = FontManager.REGULAR
        )

        private fun createErrorView(
                context: Context,
                error: Exception?
        ): View = error.handle(
                ifNull = { View(context) },
                ifNotNull = { exception ->
                    ConvertingErrorInfoView(
                            context = context,
                            infoView = ConvertingErrorInfo.create(exception)
                    )
                }
        )

    }

}