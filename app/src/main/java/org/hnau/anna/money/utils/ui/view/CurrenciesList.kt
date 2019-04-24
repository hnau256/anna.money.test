package org.hnau.anna.money.utils.ui.view

import android.content.Context
import io.reactivex.Observable
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.utils.rx.toProducer
import ru.hnau.androidutils.ui.view.list.base.BaseList
import ru.hnau.androidutils.ui.view.list.base.BaseListCalculateDiffInfo
import ru.hnau.androidutils.ui.view.list.base.BaseListOrientation
import ru.hnau.androidutils.utils.ContextConnector.context
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.producer.extensions.toProducer


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
        fixedSize = true/*,
        calculateDiffInfo = BaseListCalculateDiffInfo.create<Currency, Currency, Currency>(
                itemIdExtractor = { it },
                itemContentExtractor = { it }
        )*/
)