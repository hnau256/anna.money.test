package org.hnau.anna.money.utils

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.channels.consumesAll
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.handle
import ru.hnau.jutils.helpers.Outdatable
import ru.hnau.jutils.helpers.toOutdatable


abstract class CachedSuspendResolverObservable<T : Any> : Observable<SuspendResolver<T>>() {

    private val publishSubject = PublishSubject.create<SuspendResolver<T>>()

    init {
        publishSubject.
    }

    private var cachedResolver: Outdatable<SuspendResolver<T>>? = null

    protected open val lifetime: TimeValue? = null

    protected abstract fun getNewResolver(): SuspendResolver<T>

    fun forceInvalidate() {
        Observable.fromPublisher<Int> {
            PublishSubject
        }.publish().connect()
        val newResolver = getNewResolver()
        cachedResolver = newResolver.toOutdatable(lifetime)

    }

    override fun subscribeActual(observer: Observer<in SuspendResolver<T>>?) {
        cachedResolver?.value.handle(
                ifNotNull = { existence ->
                    observer?.onNext(existence)
                },
                ifNull = {
                    forceInvalidate()
                }
        )
    }

}