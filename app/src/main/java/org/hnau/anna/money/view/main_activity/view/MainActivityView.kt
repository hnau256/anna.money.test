package org.hnau.anna.money.view.main_activity.view

import android.content.Context
import android.widget.LinearLayout
import com.arellomobile.mvp.MvpDelegate
import org.hnau.anna.money.utils.ui.view.currencies_list.CurrenciesList
import org.hnau.anna.money.presenter.AppPresenter
import org.hnau.anna.money.utils.ui.ColorManager
import org.hnau.anna.money.utils.ui.FontManager
import org.hnau.anna.money.utils.ui.view.CurrencyPresenter
import org.hnau.anna.money.view.lottie_activity.LottieActivty
import org.hnau.anna.money.view.main_activity.view.to_money.ToMoneyView
import ru.hnau.androidutils.context_getters.DrawableGetter
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.androidutils.context_getters.dp_px.dp16
import ru.hnau.androidutils.context_getters.dp_px.dp24
import ru.hnau.androidutils.context_getters.dp_px.dp8
import ru.hnau.androidutils.ui.drawables.layout_drawable.view.addLayoutDrawableView
import ru.hnau.androidutils.ui.utils.Side
import ru.hnau.androidutils.ui.utils.h_gravity.HGravity
import ru.hnau.androidutils.ui.view.addLinearSeparator
import ru.hnau.androidutils.ui.view.header.addHeader
import ru.hnau.androidutils.ui.view.header.addHeaderTitle
import ru.hnau.androidutils.ui.view.utils.apply.*
import ru.hnau.androidutils.ui.view.utils.apply.layout_params.applyLinearParams
import ru.hnau.androidutils.ui.view.utils.createIsVisibleToUserProducer
import ru.hnau.androidutils.ui.view.waiter.material.addMaterialWaiter
import ru.hnau.androidutils.ui.view.waiter.material.drawer.params.MaterialWaiterColor
import ru.hnau.androidutils.ui.view.waiter.material.drawer.params.MaterialWaiterSize
import ru.hnau.androidutils.utils.startActivity
import ru.hnau.jutils.ifTrue
import ru.hnau.remote_teaching_android.ui.button.AMButtonInfo
import ru.hnau.remote_teaching_android.ui.button.addAMButton
import androidx.core.content.ContextCompat.startActivity
import android.app.ActivityOptions
import android.content.Intent
import org.hnau.anna.money.R


class MainActivityView(
        context: Context,
        parentDelegate: MvpDelegate<*>
) : LinearLayout(
        context
) {

    private val state = MainActivityViewState(parentDelegate)

    private val presenter: AppPresenter?
        get() = state.presenter

    init {

        applyVerticalOrientation()

        //Заголовок
        addChild(MainActivityViewHeader(context, state.lockedProducer))

        //Список валют для выбора валюты из которой конвертировать
        addChild(
                CurrenciesList(
                        context = context,
                        currencies = state.availableFromCurrencies,
                        onCurrencyClick = { presenter?.onFromCurrencyClicked(it) },
                        selectedCurrency = state.fromCurrency
                )
                        .applyLinearParams { setMatchParentWidth() }
        )

        addHorizontalLayout {

            applyLinearParams {
                setMatchParentWidth()
                setStretchedHeight()
            }

            applyCenterGravity()

            //Значение для конвертации
            addChild(
                    MainActivityViewFromMoneyInput(
                            context = context,
                            onValueChanged = { presenter?.onFromMoneyEntered(it) },
                            valueObservable = state.fromMoneyString
                    ).applyLinearParams {
                        setMatchParentHeight()
                        setStretchedWidth()
                    }
            )

            //Исходная валюта конвертации
            addChild(
                    CurrencyPresenter(
                            context = context,
                            currencyObservable = state.fromCurrency
                    ).applyLinearParams { setMatchParentHeight() }
            )

        }

        addHorizontalLayout {

            applyLinearParams {
                setMatchParentWidth()
                setStretchedHeight()
            }

            applyCenterGravity()

            //Результат конвертации
            addChild(
                    ToMoneyView(
                            context = context,
                            toMoneyObservable = state.toMoney
                    ).applyLinearParams {
                        setStretchedWidth()
                    }
            )

            //Валюта результата конвертации
            addChild(
                    CurrencyPresenter(
                            context = context,
                            currencyObservable = state.toCurrency
                    ).applyLinearParams { setMatchParentHeight() }
            )
        }


        //Список валют для выборпа валюты в которую конвертировать
        addChild(
                CurrenciesList(
                        context = context,
                        currencies = state.availableToCurrencies,
                        onCurrencyClick = { presenter?.onToCurrencyClicked(it) },
                        selectedCurrency = state.toCurrency
                )
                        .applyLinearParams { setMatchParentWidth() }
        )

        addAMButton(
                text = StringGetter(R.string.go_to_lottie_screen),
                info = AMButtonInfo.PRIMARY,
                onClick = this::startLottieActivity
        ) {
            applyLinearParams { }
        }

    }

    private fun startLottieActivity() {
        val myIntent = Intent(context, LottieActivty::class.java)
        val options = ActivityOptions.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        context.startActivity(myIntent, options.toBundle())
    }

}