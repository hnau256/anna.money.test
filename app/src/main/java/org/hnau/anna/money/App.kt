package org.hnau.anna.money

import android.app.Application
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.MvpFacade
import org.hnau.anna.money.view.DaggerAppModelComponent
import ru.hnau.androidutils.utils.ContextConnector


class App : Application() {

    companion object {

        val appModelComponent =
                DaggerAppModelComponent.builder().build()

    }

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
        ContextConnector.init(this)
    }

}