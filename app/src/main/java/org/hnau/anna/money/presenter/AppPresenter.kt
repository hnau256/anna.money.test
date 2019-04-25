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

    init {
        App.appModel.fromCurrencyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setSelectedFromCurrency)
        App.appModel.toCurrencyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setSelectedToCurrency)
        App.appModel.availableCurrenciesObservable.subscribeWhen(isVisibleToUserObservable, viewState::setAvailableFromCurrencies)
        App.appModel.availableToCurrenciesObservable.subscribeWhen(isVisibleToUserObservable, viewState::setAvailableToCurrencies)
        App.appModel.isLockedObservable.subscribeWhen(isVisibleToUserObservable, viewState::setIsUpdating)
        App.appModel.fromMoneyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setFromMoney)
        App.appModel.toMoneyObservable.subscribeWhen(isVisibleToUserObservable, viewState::setToMoney)
    }

    fun onFromMoneyEntered(fromMoney: String) =
            App.appModel.onFromMoneyEntered(fromMoney)

    fun onFromCurrencyClicked(fromCurrency: Currency) =
            App.appModel.onFromCurrencySelected(fromCurrency)

    fun onToCurrencyClicked(toCurrency: Currency) =
            App.appModel.onToCurrencySelected(toCurrency)

}