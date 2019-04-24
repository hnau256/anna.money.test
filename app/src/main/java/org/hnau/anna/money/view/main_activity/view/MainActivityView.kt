package org.hnau.anna.money.view.main_activity.view

import android.content.Context
import android.widget.LinearLayout
import com.arellomobile.mvp.MvpDelegate
import org.hnau.anna.money.R
import org.hnau.anna.money.utils.ui.view.currencies_list.CurrenciesList
import org.hnau.anna.money.presenter.AppPresenter
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.androidutils.context_getters.dp_px.dp16
import ru.hnau.androidutils.context_getters.dp_px.dp24
import ru.hnau.androidutils.ui.utils.h_gravity.HGravity
import ru.hnau.androidutils.ui.view.addLinearSeparator
import ru.hnau.androidutils.ui.view.header.addHeader
import ru.hnau.androidutils.ui.view.header.addHeaderTitle
import ru.hnau.androidutils.ui.view.utils.apply.addChild
import ru.hnau.androidutils.ui.view.utils.apply.applyVerticalOrientation
import ru.hnau.androidutils.ui.view.utils.apply.layout_params.applyLinearParams
import ru.hnau.androidutils.ui.view.waiter.material.addMaterialWaiter
import ru.hnau.androidutils.ui.view.waiter.material.drawer.params.MaterialWaiterColor
import ru.hnau.androidutils.ui.view.waiter.material.drawer.params.MaterialWaiterSize


class MainActivityView(
        context: Context,
        parentDelegate: MvpDelegate<*>
) : LinearLayout(
        context
) {

    private val state = MainActivityViewState(parentDelegate)

    private val presenter: AppPresenter
        get() = state.presenter

    //Список валют для выборпа валюты из которой конвертировать
    private val fromCurrenciesList = CurrenciesList(
            context = context,
            currencies = state.availableFromCurrencies,
            onCurrencyClick = { presenter.onFromCurrencyClicked(it) },
            selectedCurrency = state.fromCurrency
    )
            .applyLinearParams { setMatchParentWidth() }

    //Список валют для выборпа валюты в которую конвертировать
    private val toCurrenciesList = CurrenciesList(
            context = context,
            currencies = state.availableToCurrencies,
            onCurrencyClick = { presenter.onToCurrencyClicked(it) },
            selectedCurrency = state.toCurrency
    )
            .applyLinearParams { setMatchParentWidth() }

    init {
        applyVerticalOrientation()

        addHeader(
                headerBackgroundColor = ColorManager.PRIMARY_DARK,
                underStatusBar = false
        ) {

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
                    lockedProducer = state.lockedProducer,
                    size = MaterialWaiterSize.SMALL,
                    color = MaterialWaiterColor.WHITE_ON_TRANSPARENT
            ) {
                applyLinearParams { setEndMargin(dp16) }
            }

        }

        addChild(fromCurrenciesList)
        addLinearSeparator()
        addChild(toCurrenciesList)
    }

}