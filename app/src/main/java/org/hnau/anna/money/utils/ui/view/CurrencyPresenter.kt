package org.hnau.anna.money.utils.ui.view

import android.content.Context
import android.view.animation.LinearInterpolator
import io.reactivex.Observable
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.utils.rx.toProducer
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.dp_px.dp16
import ru.hnau.androidutils.context_getters.dp_px.dp24
import ru.hnau.androidutils.context_getters.dp_px.dp32
import ru.hnau.androidutils.context_getters.dp_px.dp8
import ru.hnau.androidutils.context_getters.toGetter
import ru.hnau.androidutils.ui.utils.Side
import ru.hnau.androidutils.ui.utils.h_gravity.HGravity
import ru.hnau.androidutils.ui.view.label.Label
import ru.hnau.androidutils.ui.view.utils.apply.applyHorizontalPadding
import ru.hnau.androidutils.ui.view.utils.apply.applyPadding
import ru.hnau.androidutils.ui.view.view_presenter.*
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.producer.extensions.filterUnique


class CurrencyPresenter(
        context: Context,
        currencyObservable: Observable<Box<Currency?>>
) : PresenterView(
        context = context,
        presenterViewInfo = PresenterViewInfo(
                horizontalSizeInterpolator = SizeInterpolator.SMOOTH,
                verticalSizeInterpolator = SizeInterpolator.MAX
        ),
        presentingViewProducer = createPresentingViewProducer(
                context = context,
                currencyObservable = currencyObservable

        )

) {

    companion object {

        private val PRESENTING_VIEW_PROPERTIES = PresentingViewProperties(
                fromSide = Side.END,
                scrollFactor = 0.1f,
                animatingTime = TimeValue.MILLISECOND * 200,
                showInterpolator = LinearInterpolator()
        )

        private fun createPresentingViewProducer(
                context: Context,
                currencyObservable: Observable<Box<Currency?>>
        ): Producer<PresentingViewInfo> {

            return currencyObservable
                    .toProducer()
                    .filterUnique()
                    .map { currency ->
                        currency.value
                                ?.let { getCurrencyView(context, it) }
                                .toPresentingInfo(PRESENTING_VIEW_PROPERTIES)
                    }

        }

        private fun getCurrencyView(
                context: Context,
                currency: Currency
        ) = Label(
                context = context,
                initialText = currency.signOrKey.toGetter(),
                textColor = ColorManager.BLACK,
                gravity = HGravity.CENTER,
                fontType = FontManager.BOLD,
                textSize = dp32,
                maxLines = 1,
                minLines = 1
        )
                .applyHorizontalPadding(dp8)

    }

}