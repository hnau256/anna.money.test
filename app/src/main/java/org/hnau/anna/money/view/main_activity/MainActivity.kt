package org.hnau.anna.money.view.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpDelegate
import org.hnau.anna.money.view.main_activity.view.MainActivityView


class MainActivity : AppCompatActivity() {

    private val mvpDelegate: MvpDelegate<MainActivity>
            by lazy { MvpDelegate(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val contentView = MainActivityView(this, mvpDelegate)
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroyView()
        if (isFinishing()) {
            mvpDelegate.onDestroy()
        }
    }

}
