package org.hnau.anna.money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import org.hnau.anna.money.converter.ecb.ECBCurrencyConverterProvider
import org.hnau.anna.money.data.Currency
import org.hnau.anna.money.view.CurrenciesList
import ru.hnau.jutils.coroutines.launch
import ru.hnau.jutils.producer.StateProducerSimple

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedCurrency = BehaviorSubject.create<Currency>()

        setContentView(
                CurrenciesList(
                        context = this,
                        selectedCurrency = selectedCurrency,
                        currencies = Observable.just(Currency.values().toList()),
                        onCurrencyClick = {
                            selectedCurrency.onNext(it)
                        }
                )
        )
    }
}
