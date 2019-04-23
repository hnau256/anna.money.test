package org.hnau.anna.money.utils.rx

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import ru.hnau.jutils.producer.Producer


fun <T> Observable<T>.toProducer() =
        object : Producer<T>() {

            private var disposable: Disposable? = null
                set(value) {
                    field?.dispose()
                    field = value
                }

            override fun onFirstAttached() {
                super.onFirstAttached()
                disposable = this@toProducer.subscribe(this::call)
            }

            override fun onLastDetached() {
                disposable = null
                super.onLastDetached()
            }


        }