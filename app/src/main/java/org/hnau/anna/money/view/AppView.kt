package org.hnau.anna.money.view

import com.arellomobile.mvp.MvpView
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.jutils.helpers.Box
import java.math.BigDecimal


interface AppView : MvpView {

    fun setAvailableFromCurrencies(availableFromCurrencies: Set<Currency>)

    fun setSelectedFromCurrency(selectedFromCurrency: Box<Currency?>)

    fun setAvailableToCurrencies(availableToCurrencies: Set<Currency>)

    fun setSelectedToCurrency(selectedToCurrency: Box<Currency?>)

    fun setFromMoney(fromMoneyString: String)

    fun setToMoney(toMoney: Money)

    fun setError(errorMessage: StringGetter)

    fun setIsUpdating(isUpdating: Boolean)

}