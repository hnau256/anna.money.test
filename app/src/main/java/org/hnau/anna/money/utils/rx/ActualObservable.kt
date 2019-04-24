package org.hnau.anna.money.utils.rx

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


//Observable, который повторяет логику BehaviorSubject, но от которого можно наследоваться
abstract class ActualObservable<T>(
        initialValue: T
) : Observable<T>() {

    private val behaviorSubject = BehaviorSubject.createDefault(initialValue)

    protected val isObserving: Boolean
        get() = behaviorSubject.hasObservers()

    override final fun subscribeActual(observer: Observer<in T>) {
        behaviorSubject.subscribe(observer)
        onSubscribed(observer)
    }

    protected open fun onSubscribed(observer: Observer<in T>) {}

    protected fun onNext(data: T) =
            behaviorSubject.onNext(data)

    protected fun onComplete() =
            behaviorSubject.onComplete()

    protected fun onError(th: Throwable) =
            behaviorSubject.onError(th)

}