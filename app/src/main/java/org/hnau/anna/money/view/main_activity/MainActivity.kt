package org.hnau.anna.money.view.main_activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import org.hnau.anna.money.view.main_activity.view.MainActivityView


class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val contentView = MainActivityView(this, mvpDelegate)
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }

}
