package org.hnau.anna.money.view.main_activity.view

import android.content.Context
import android.widget.LinearLayout
import com.arellomobile.mvp.MvpDelegate
import org.hnau.anna.money.utils.ui.view.CurrenciesList
import org.hnau.anna.money.presenter.AppPresenter
import ru.hnau.androidutils.ui.view.addLinearSeparator
import ru.hnau.androidutils.ui.view.utils.apply.addChild
import ru.hnau.androidutils.ui.view.utils.apply.applyVerticalOrientation
import ru.hnau.androidutils.ui.view.utils.apply.layout_params.applyLinearParams


class MainActivityView(
        context: Context,
        parentDelegate: MvpDelegate<*>
) : LinearLayout(
        context
) {

    private val state = MainActivityViewState(parentDelegate)

    private val presenter: AppPresenter
        get() = state.presenter

    private val fromCurrenciesList = CurrenciesList(
            context = context,
            currencies = state.availableFromCurrencies,
            onCurrencyClick = { presenter.onFromCurrencyClicked(it) },
            selectedCurrency = state.selectedFromCurrency
    )
            .applyLinearParams { setMatchParentWidth() }

    private val toCurrenciesList = CurrenciesList(
            context = context,
            currencies = state.availableToCurrencies,
            onCurrencyClick = { presenter.onToCurrencyClicked(it) },
            selectedCurrency = state.selectedToCurrency
    )
            .applyLinearParams { setMatchParentWidth() }

    init {
        applyVerticalOrientation()
        addChild(fromCurrenciesList)
        addLinearSeparator()
        addChild(toCurrenciesList)
    }

}