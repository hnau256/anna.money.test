package org.hnau.anna.money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import org.hnau.anna.money.converter.ecb.ECBCurrencyConverterProvider
import ru.hnau.jutils.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dispatchers.IO.launch {
            val converter = ECBCurrencyConverterProvider().getCurrencyConverter()
            println(converter)
        }
    }
}
