package org.hnau.anna.money.utils.rx

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject


//Observable, который повторяет логику PublishSubject, но от которого можно наследоваться
abstract class Emitter<T> : Observable<T>() {

    private val publishSubject = PublishSubject.create<T>()

    protected val isObserving: Boolean
        get() = publishSubject.hasObservers()

    override final fun subscribeActual(observer: Observer<in T>) {
        publishSubject.subscribe(observer)
        onSubscribed(observer)
    }

    protected open fun onSubscribed(observer: Observer<in T>) {}

    protected fun onNext(data: T) =
            publishSubject.onNext(data)

    protected fun onComplete() =
            publishSubject.onComplete()

    protected fun onError(th: Throwable) =
            publishSubject.onError(th)

}