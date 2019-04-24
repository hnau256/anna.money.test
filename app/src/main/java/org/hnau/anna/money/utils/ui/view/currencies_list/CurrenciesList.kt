package org.hnau.anna.money.utils.ui.view.currencies_list

import android.content.Context
import io.reactivex.Observable
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.utils.rx.toProducer
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.view.currencies_list.item.CurrencyButtonViewWrapper
import ru.hnau.androidutils.context_getters.dp_px.dp8
import ru.hnau.androidutils.ui.view.list.base.BaseList
import ru.hnau.androidutils.ui.view.list.base.BaseListCalculateDiffInfo
import ru.hnau.androidutils.ui.view.list.base.BaseListOrientation
import ru.hnau.androidutils.ui.view.list.base.BaseListPaddingDecoration
import ru.hnau.androidutils.ui.view.utils.apply.applyVerticalPadding
import ru.hnau.androidutils.ui.view.utils.getDefaultMeasurement
import ru.hnau.androidutils.ui.view.utils.getMaxMeasurement
import ru.hnau.androidutils.ui.view.utils.makeExactlyMeasureSpec
import ru.hnau.androidutils.ui.view.utils.verticalPaddingSum
import ru.hnau.jutils.helpers.Box

/**
 * RecycleView - список валют
 */
class CurrenciesList(
        context: Context,
        selectedCurrency: Observable<Box<Currency?>>,
        currencies: Observable<Set<Currency>>,
        onCurrencyClick: (Currency) -> Unit
) : BaseList<Currency>(
        context = context,
        viewWrappersCreator = {
            CurrencyButtonViewWrapper(
                    context = context,
                    onClick = onCurrencyClick,
                    selectedCurrency = selectedCurrency
            )
        },
        orientation = BaseListOrientation.HORIZONTAL,
        itemsProducer = currencies.map { currenciesSet ->
            currenciesSet.sortedBy(Currency::name)
        }.toProducer(),
        fixedSize = true,
        calculateDiffInfo = BaseListCalculateDiffInfo.create<Currency, Currency, Currency>(
                itemIdExtractor = { it },
                itemContentExtractor = { it }
        ),
        itemsDecoration = BaseListPaddingDecoration(
                beforeFirst = HORIZONTAL_PADDING,
                afterLast = HORIZONTAL_PADDING
        )
) {

    companion object {

        private val HORIZONTAL_PADDING = dp8
        private val VERTRICAL_PADDING = dp8

        val PREFERRED_HEIGHT = CurrencyButtonViewWrapper.PREFERRED_SIZE +
                ColorManager.DEFAULT_SHADOW_INFO.insets.verticalSum

    }

    init {
        applyVerticalPadding(VERTRICAL_PADDING)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val width = getMaxMeasurement(widthSpec, 0)
        val height = getDefaultMeasurement(heightSpec, PREFERRED_HEIGHT.getPxInt(context) + verticalPaddingSum)
        super.onMeasure(
                makeExactlyMeasureSpec(width),
                makeExactlyMeasureSpec(height)
        )
    }

}