package org.hnau.anna.money

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.MvpFacade
import org.hnau.anna.money.converter.provider.di.DaggerCurrencyConverterProviderComponent
import org.hnau.anna.money.model.AppModel
import ru.hnau.androidutils.utils.ContextConnector


class App : MultiDexApplication() {

    companion object {

        private val currencyConverterProviderComponent =
                DaggerCurrencyConverterProviderComponent
                        .builder().build()

        val appModel = AppModel(currencyConverterProviderComponent)

    }

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
        ContextConnector.init(this)
    }

}