package org.hnau.anna.money.utils.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.hnau.jutils.producer.ActualProducerSimple
import ru.hnau.jutils.producer.Producer


abstract class AttachableMvpPresenter<View : MvpView> : MvpPresenter<View>() {

    private val isVisibleToUserProducerInner = ActualProducerSimple<Boolean>(false)

    protected val isVisibleToUserProducer: Producer<Boolean>
        get() = isVisibleToUserProducerInner

    private fun onViewsChanged() =
            isVisibleToUserProducerInner.updateStateIfChanged(attachedViews.size > 0)

    override fun attachView(view: View?) {
        super.attachView(view)
        onViewsChanged()
    }

    override fun detachView(view: View?) {
        super.detachView(view)
        onViewsChanged()
    }

}