package org.hnau.anna.money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.hnau.anna.money.api.CurrencyRateProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CurrencyRateProvider.INSTANCE.getCurrencyRate().subscribe {
            println(it)
        }

    }
}
