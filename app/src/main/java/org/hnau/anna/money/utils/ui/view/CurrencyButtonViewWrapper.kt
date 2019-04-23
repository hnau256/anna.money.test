package org.hnau.anna.money.utils.ui.view

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import io.reactivex.Observable
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.utils.ui.ColorManager
import ru.hnau.androidutils.context_getters.ColorGetter
import ru.hnau.androidutils.context_getters.dp_px.dp64
import ru.hnau.androidutils.ui.bounds_producer.createBoundsProducer
import ru.hnau.androidutils.ui.canvas_shape.CircleCanvasShape
import ru.hnau.androidutils.ui.drawer.ripple.RippleDrawer
import ru.hnau.androidutils.ui.drawer.ripple.info.RippleDrawInfo
import ru.hnau.androidutils.ui.drawer.shadow.drawer.ButtonShadowDrawer
import ru.hnau.androidutils.ui.view.list.base.BaseListViewWrapper
import ru.hnau.androidutils.ui.view.utils.createIsVisibleToUserProducer
import ru.hnau.androidutils.ui.view.utils.getDefaultMeasurement
import ru.hnau.androidutils.ui.view.utils.touch.TouchHandler
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.producer.StateProducerSimple


class CurrencyButtonViewWrapper(
        context: Context,
        private val selectedCurrency: Producer<Box<Currency?>>,
        onClick: (Currency) -> Unit
) : View(
        context
), BaseListViewWrapper<Currency> {

    companion object {

        private val PREFERRED_SIZE = dp64

    }

    private val shadowInsets = ColorManager.DEFAULT_SHADOW_INFO.insets

    override val view = this

    private val currency = StateProducerSimple<Currency>()

    private val isVisibleToUserProducer =
            createIsVisibleToUserProducer()

    private val boundsProducer = createBoundsProducer()
            .applyInsents(context, shadowInsets)

    private val canvasShape = CircleCanvasShape(
            boundsProducer = boundsProducer
    )

    private val touchHandler = TouchHandler(
            canvasShape = canvasShape
    ) { currency.currentState?.let(onClick) }

    private val shadowDrawer = ButtonShadowDrawer(
            animatingView = this,
            animatingViewIsVisibleToUser = isVisibleToUserProducer,
            touchHandler = touchHandler,
            canvasShape = canvasShape,
            shadowInfo = ColorManager.DEFAULT_BUTTON_SHADOW_INFO
    )

    private val rippleDrawer = RippleDrawer(
            animatingView = this,
            animatingViewIsVisibleToUser = isVisibleToUserProducer,
            canvasShape = canvasShape,
            touchHandler = touchHandler,
            rippleDrawInfo = RippleDrawInfo(
                    rippleInfo = ColorManager.RIPPLE_INFO,
                    backgroundColor = ColorGetter.TRANSPARENT,
                    color = ColorGetter.BLACK,
                    rippleAlpha = ColorManager.RIPPLE_ALPHA
            )
    )

    private val contentDrawer = CurrencyButtonContentDrawer(
            context = context,
            canvasShape = canvasShape,
            boundsProducer = boundsProducer,
            currencyProducer = currency,
            isVisibleToUserProducer = isVisibleToUserProducer,
            onNeedInvalidate = this::invalidate,
            selectedCurrencyProducer = selectedCurrency
    )

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        shadowDrawer.draw(canvas)
        contentDrawer.drawBackground(canvas)
        rippleDrawer.draw(canvas)
        contentDrawer.drawForeground(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        touchHandler.handle(event)
        return true
    }

    override fun setContent(content: Currency, position: Int) {
        currency.updateState(content)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
                getDefaultMeasurement(
                        widthMeasureSpec,
                        (PREFERRED_SIZE + shadowInsets.horizontalSum).getPxInt(context)
                ),
                getDefaultMeasurement(
                        heightMeasureSpec,
                        (PREFERRED_SIZE + shadowInsets.verticalSum).getPxInt(context)
                )
        )
    }

}