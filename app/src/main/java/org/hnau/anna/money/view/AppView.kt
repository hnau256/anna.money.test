package org.hnau.anna.money.view

import com.arellomobile.mvp.MvpView
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.data.Money
import ru.hnau.androidutils.context_getters.StringGetter
import ru.hnau.jutils.helpers.Box
import ru.hnau.jutils.possible.Possible
import java.math.BigDecimal


interface AppView : MvpView {

    fun setAvailableFromCurrencies(availableFromCurrencies: Set<Currency>)

    fun setSelectedFromCurrency(fromCurrency: Box<Currency?>)

    fun setAvailableToCurrencies(availableToCurrencies: Set<Currency>)

    fun setSelectedToCurrency(toCurrency: Box<Currency?>)

    fun setFromMoney(fromMoneyString: String)

    fun setToMoney(toMoney: Possible<Money>)

    fun setError(errorMessage: StringGetter)

    fun setIsUpdating(isUpdating: Boolean)

}