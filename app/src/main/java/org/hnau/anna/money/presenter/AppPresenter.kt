package org.hnau.anna.money.presenter

import com.arellomobile.mvp.InjectViewState
import org.hnau.anna.money.App
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.model.AppModel
import org.hnau.anna.money.utils.mvp.AttachableMvpPresenter
import org.hnau.anna.money.view.AppView
import javax.inject.Inject


@InjectViewState
class AppPresenter : AttachableMvpPresenter<AppView>() {

    @Inject
    lateinit var appModel: AppModel

    init {
        App.appModelComponent.inject(this)
        viewState.setAvailableFromCurrencies(Currency.values().toSet())
        viewState.setAvailableToCurrencies(Currency.values().toSet())
    }

    fun onFromMoneyEntered(fromMoney: String) {
        appModel.setFromMoney(fromMoney)
        viewState.setFromMoney(fromMoney)
        TODO()
    }

    fun onFromCurrencyClicked(fromCurrency: Currency) {
        appModel.setFromCurrency(fromCurrency)
        viewState.setSelectedFromCurrency(fromCurrency)
        //TODO()
    }

    fun onToCurrencyClicked(toCurrency: Currency) {
        appModel.setToCurrency(toCurrency)
        viewState.setSelectedToCurrency(toCurrency)
        //TODO()
    }

}