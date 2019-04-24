package org.hnau.anna.money.utils.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.hnau.jutils.producer.ActualProducerSimple
import ru.hnau.jutils.producer.Producer


abstract class AttachableMvpPresenter<View : MvpView> : MvpPresenter<View>() {

    private val isVisibleToUserObservableInner =
            BehaviorSubject.createDefault(false)

    protected val isVisibleToUserObservable: Observable<Boolean>
        get() = isVisibleToUserObservableInner

    private var isVisibleToUser = false
        set(value) {
            if (field != value) {
                field = value
                isVisibleToUserObservableInner.onNext(value)
            }
        }

    private fun onViewsChanged() {
        isVisibleToUser = attachedViews.size > 0
    }

    override fun attachView(view: View?) {
        super.attachView(view)
        onViewsChanged()
    }

    override fun detachView(view: View?) {
        super.detachView(view)
        onViewsChanged()
    }

}