package org.hnau.anna.money.view.main_activity.view

import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.presenter.AppPresenter
import org.hnau.anna.money.view.AppView
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.helpers.toBox
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.producer.ActualProducerSimple
import ru.hnau.jutils.producer.locked_producer.SimpleLockedProducer


class MainActivityViewState(
        parentDelegate: MvpDelegate<*>
) : AppView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MainActivityView")
    @JvmField
    var presenter: AppPresenter? = null

    init {
        MvpDelegate(this)
                .setParentDelegate(parentDelegate, "MainActivityView")
    }

    val availableFromCurrencies = BehaviorSubject.createDefault<Set<Currency>>(emptySet())
    override fun setAvailableFromCurrencies(availableFromCurrencies: Set<Currency>) =
            this.availableFromCurrencies.onNext(availableFromCurrencies)

    val fromCurrency = BehaviorSubject.createDefault<Box<Currency?>>(Box(null))
    override fun setSelectedFromCurrency(fromCurrency: Box<Currency?>) =
            this.fromCurrency.onNext(fromCurrency)

    val availableToCurrencies = BehaviorSubject.createDefault<Set<Currency>>(emptySet())
    override fun setAvailableToCurrencies(availableToCurrencies: Set<Currency>) =
            this.availableToCurrencies.onNext(availableToCurrencies)

    val toCurrency = BehaviorSubject.createDefault<Box<Currency?>>(Box(null))
    override fun setSelectedToCurrency(toCurrency: Box<Currency?>) =
            this.toCurrency.onNext(toCurrency)

    val fromMoneyString = BehaviorSubject.createDefault<String>("")
    override fun setFromMoney(fromMoneyString: String) =
            this.fromMoneyString.onNext(fromMoneyString)

    val toMoney = BehaviorSubject.createDefault<Possible<Money>>(Possible.undefined())
    override fun setToMoney(toMoney: Possible<Money>) =
            this.toMoney.onNext(toMoney)

    val errorMessage = BehaviorSubject.createDefault<StringGetter>(StringGetter.EMPTY)
    override fun setError(errorMessage: StringGetter) =
            this.errorMessage.onNext(errorMessage)

    val lockedProducer = SimpleLockedProducer()
    override fun setIsUpdating(isUpdating: Boolean) =
            lockedProducer.setIsLocked(isUpdating)

}