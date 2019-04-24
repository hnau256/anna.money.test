package org.hnau.anna.money.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import org.hnau.anna.money.App
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.model.AppModel
import org.hnau.anna.money.utils.mvp.AttachableMvpPresenter
import org.hnau.anna.money.utils.rx.subscribeWhen
import org.hnau.anna.money.view.AppView
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.helpers.toBox
import javax.inject.Inject


@InjectViewState
class AppPresenter : AttachableMvpPresenter<AppView>() {

    @Inject
    lateinit var appModel: AppModel

    init {
        App.appModelComponent.inject(this)
    }

    init {
        appModel.fromCurrencyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setSelectedFromCurrency)
        appModel.toCurrencyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setSelectedToCurrency)
        appModel.availableCurrenciesObservable.subscribeWhen(isVisibleToUserObservable, viewState::setAvailableFromCurrencies)
        appModel.availableToCurrenciesObservable.subscribeWhen(isVisibleToUserObservable, viewState::setAvailableToCurrencies)
        appModel.isLockedObservable.subscribeWhen(isVisibleToUserObservable, viewState::setIsUpdating)
    }

    fun onFromMoneyEntered(fromMoney: String) {
        //appModel.setFromMoney(fromMoney)
        viewState.setFromMoney(fromMoney)
        TODO()
    }

    fun onFromCurrencyClicked(fromCurrency: Currency) =
            appModel.onFromCurrencySelected(fromCurrency)

    fun onToCurrencyClicked(toCurrency: Currency) =
            appModel.onToCurrencySelected(toCurrency)

}