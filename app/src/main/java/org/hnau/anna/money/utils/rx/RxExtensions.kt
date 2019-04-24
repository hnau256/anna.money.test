package org.hnau.anna.money.utils.rx

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.hnau.androidutils.ui.utils.logD
import ru.hnau.jutils.getter.base.GetterAsync
import ru.hnau.jutils.getter.base.get
import ru.hnau.jutils.handle
import ru.hnau.jutils.ifTrue
import ru.hnau.jutils.possible.Possible
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

//Отслеживать Observable только когда значение [whenSign] - true
fun <T> Observable<T>.subscribeWhen(
        whenSign: Producer<Boolean>,
        onNext: (T) -> Unit
) {

    var disposable: Disposable? = null

    whenSign.attach {
        disposable?.dispose()
        disposable = it.ifTrue { subscribe(onNext) }
    }

}

/**
 * Отслеживать Observable только когда значение [whenSign] - true
 */
fun <T> Observable<T>.subscribeWhen(
        whenSign: Observable<Boolean>,
        onNext: (T) -> Unit
) {

    var disposable: Disposable? = null

    whenSign.subscribe {
        disposable?.dispose()
        disposable = it.ifTrue {
            subscribe(onNext)
        }
    }

}

//Обертка над Observable.combineLatest
fun <A, B, R> combineObservables(
        first: Observable<A>,
        second: Observable<B>,
        aggregator: (A, B) -> R
): Observable<R> = Observable.combineLatest<A, B, R>(
        first, second, BiFunction { a, b -> aggregator(a, b) }
)

/**
 * Получение синхронных значений из Observable<GetterAsync<Unit, T>> выполняя асинхронное их получение через [coroutinesExecutor]
 */
fun <T : Any> Observable<GetterAsync<Unit, T>>.toSync(
        coroutinesExecutor: (suspend () -> Unit) -> Unit
): Observable<Possible<T>> {
    var currentAsyncGetter: GetterAsync<Unit, T>?
    return flatMap { asyncGetter ->
        currentAsyncGetter = asyncGetter
        val publishSubject = PublishSubject.create<Possible<T>>()
        coroutinesExecutor {
            val result = Possible.trySuccessCatchError { asyncGetter.get() }
            synchronized(this@toSync) {
                if (currentAsyncGetter == asyncGetter) {
                    publishSubject.onNext(result)
                }
                publishSubject.onComplete()
            }
        }
        publishSubject
    }.share()
}
