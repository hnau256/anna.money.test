package org.hnau.anna.money.view

import android.content.Context
import io.reactivex.Observable
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.utils.rx.toProducer
import ru.hnau.androidutils.ui.view.list.base.BaseList
import ru.hnau.androidutils.ui.view.list.base.BaseListCalculateDiffInfo
import ru.hnau.androidutils.ui.view.list.base.BaseListOrientation


class CurrenciesList(
        context: Context,
        selectedCurrency: Observable<Currency?>,
        currencies: Observable<List<Currency>>,
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
        itemsProducer = currencies.toProducer(),
        fixedSize = true,
        calculateDiffInfo = BaseListCalculateDiffInfo.create<Currency, String, Currency>(
                itemIdExtractor = { it.name },
                itemContentExtractor = { it }
        )
)