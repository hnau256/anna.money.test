package org.hnau.anna.money.view.main_activity.view

import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import org.hnau.anna.money.presenter.AppPresenter
import org.hnau.anna.money.view.AppView
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.helpers.toBox
import ru.hnau.jutils.producer.ActualProducerSimple
import ru.hnau.jutils.producer.locked_producer.SimpleLockedProducer


class MainActivityViewState(
        parentDelegate: MvpDelegate<*>
) : AppView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "MainActivityView")
    lateinit var presenter: AppPresenter

    init {
        MvpDelegate(this)
                .setParentDelegate(parentDelegate, "MainActivityView")
    }

    val availableFromCurrencies = ActualProducerSimple<Set<Currency>>(emptySet())
    override fun setAvailableFromCurrencies(availableFromCurrencies: Set<Currency>) =
            this.availableFromCurrencies.updateState(availableFromCurrencies)

    val selectedFromCurrency = ActualProducerSimple<Box<Currency?>>(Box(null))
    override fun setSelectedFromCurrency(selectedFromCurrency: Currency?) =
            this.selectedFromCurrency.updateStateIfChanged(selectedFromCurrency.toBox())

    val availableToCurrencies = ActualProducerSimple<Set<Currency>>(emptySet())
    override fun setAvailableToCurrencies(availableToCurrencies: Set<Currency>) =
            this.availableToCurrencies.updateState(availableToCurrencies)

    val selectedToCurrency = ActualProducerSimple<Box<Currency?>>(Box(null))
    override fun setSelectedToCurrency(selectedToCurrency: Currency?) =
            this.selectedToCurrency.updateStateIfChanged(selectedToCurrency.toBox())

    val fromMoneyString = ActualProducerSimple<String>("")
    override fun setFromMoney(fromMoneyString: String) =
            this.fromMoneyString.updateStateIfChanged(fromMoneyString)

    val toMoney = ActualProducerSimple<Money>(Money.ZERO)
    override fun setToMoney(toMoney: Money) =
            this.toMoney.updateStateIfChanged(toMoney)

    val errorMessage = ActualProducerSimple<StringGetter>(StringGetter.EMPTY)
    override fun setError(errorMessage: StringGetter) =
            this.errorMessage.updateState(errorMessage)

    val lockedProducer = SimpleLockedProducer()
    override fun setIsUpdating(isUpdating: Boolean) =
            lockedProducer.setIsLocked(isUpdating)

}