package org.hnau.anna.money.view

import android.content.Context
import android.graphics.*
import io.reactivex.Observable
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.utils.rx.toProducer
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.ColorGetter
import ru.hnau.androidutils.context_getters.dp_px.*
import ru.hnau.androidutils.context_getters.dp_px.DpPxGetter.Companion.dp
import ru.hnau.androidutils.ui.bounds_producer.BoundsProducer
import ru.hnau.androidutils.ui.canvas_shape.CanvasShape
import ru.hnau.androidutils.ui.utils.types_utils.ColorUtils
import ru.hnau.androidutils.ui.view.utils.ViewIsVisibleToUserProducer
import ru.hnau.androidutils.utils.ContextConnector.context
import ru.hnau.jutils.handle
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.producer.extensions.filterUnique
import ru.hnau.jutils.producer.extensions.observeWhen
import ru.hnau.jutils.producer.extensions.toProducer
import kotlin.math.min


class CurrencyButtonContentDrawer(
        private val context: Context,
        isVisibleToUserProducer: Producer<Boolean>,
        private val onNeedInvalidate: () -> Unit,
        currencyProducer: Producer<Currency>,
        selectedCurrencyProducer: Observable<Currency?>,
        private val canvasShape: CanvasShape,
        private val boundsProducer: BoundsProducer
) {

    companion object {

        private val BG_COLOR_ACTIVE = ColorManager.PRIMARY
        private val FG_COLOR_ACTIVE = ColorManager.PRIMARY_DARK
        private val BG_COLOR_INACTIVE = ColorManager.GREY
        private val FG_COLOR_INACTIVE = ColorManager.BLACK

        private val TITLE_KEY_OFFSET = dp(1)
        private val TITLE_SIGN_OFFSET = dp(4)

        private val TITLE_KEY_TEXT_SIZE = dp16
        private val TITLE_SIGN_TEXT_SIZE = dp(28)

    }


    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp2.getPx(context)
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        FontManager.BOLD.get(context).applyToPaint(this)
        textAlign = Paint.Align.CENTER
    }

    private val subtitlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = dp8.getPxInt(context).toFloat()
        FontManager.REGULAR.get(context).applyToPaint(this)
        textAlign = Paint.Align.CENTER
    }

    private val titleDrawPoint = PointF()
    private val subtitleDrawPath = Path()

    private var currency: Currency? = null
        set(value) {
            if (field != value) {
                field = value
                layout()
                onNeedInvalidate()
            }
        }

    private var isActive = false
        set(value) {
            if (field != value) {
                field = value
                updateColors(value)
                onNeedInvalidate()
            }
        }

    private var bounds = RectF()
        set(value) {
            field = value
            layout()
        }

    init {
        currencyProducer.attach { currency = it }

        currencyProducer.combineWith(
                selectedCurrencyProducer.toProducer()
        ) { thisCurrency, selectedCurrency ->
            thisCurrency == selectedCurrency
        }.observeWhen(isVisibleToUserProducer) {
            isActive = it
        }

        boundsProducer.attach { bounds = it }

        updateColors(false)
    }

    fun drawBackground(canvas: Canvas) {
        canvasShape.draw(canvas, backgroundPaint)
    }

    fun drawForeground(canvas: Canvas) {
        val currency = currency ?: return
        canvasShape.draw(canvas, borderPaint)
        canvas.drawText(currency.signOrKey, titleDrawPoint.x, titleDrawPoint.y, titlePaint)
        canvas.drawTextOnPath(currency.title.get(context), subtitleDrawPath, 0f, dp4.getPx(context), subtitlePaint)
    }

    private fun layout() {


        val titleTextSize = currency?.sign.handle(
                forNotNull = TITLE_SIGN_TEXT_SIZE,
                forNull = TITLE_KEY_TEXT_SIZE
        )
        titlePaint.textSize = titleTextSize.getPxInt(context).toFloat()

        val titleOffset = currency?.sign.handle(
                forNotNull = TITLE_SIGN_OFFSET,
                forNull = TITLE_KEY_OFFSET
        )
        titleDrawPoint.set(
                bounds.centerX(),
                bounds.centerY() + titleOffset.getPxInt(context)
        )

        val subtitleRadius = min(bounds.width(), bounds.height()) * 0.35f
        subtitleDrawPath.reset()
        subtitleDrawPath.addArc(
                bounds.centerX() - subtitleRadius,
                bounds.centerY() - subtitleRadius,
                bounds.centerX() + subtitleRadius,
                bounds.centerY() + subtitleRadius,
                270f,
                -360f
        )
    }

    private fun updateColors(active: Boolean) {
        val fg = (if (active) FG_COLOR_ACTIVE else FG_COLOR_INACTIVE).get(context)
        val bg = (if (active) BG_COLOR_ACTIVE else BG_COLOR_INACTIVE).get(context)
        borderPaint.color = fg
        backgroundPaint.color = bg
        titlePaint.color = fg
        subtitlePaint.color = fg
    }

}