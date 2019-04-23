package org.hnau.anna.money.utils.rx

import android.os.Handler
import io.reactivex.Observer
import org.hnau.anna.money.utils.CachedAsync
import ru.hnau.androidutils.utils.handler.HandlerWaiter
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.getter.base.GetterAsync
import ru.hnau.jutils.handle
import ru.hnau.jutils.helpers.Outdatable
import ru.hnau.jutils.helpers.toOutdatable


abstract class AsyncEmitter<T : Any>(
        private val getterLifetime: TimeValue? = null
) : Emitter<GetterAsync<Unit, T>>() {

    private var cachedValue: Outdatable<CachedAsync<T>>? = null

    private val actualGetter: GetterAsync<Unit, T>?
        get() = cachedValue?.value?.takeIf { !it.finishedWithError }

    private val invalidateWaiter = HandlerWaiter(
            handler = Handler(),
            block = this::invalidate
    )

    fun invalidate() = synchronized(this) {
        if (isObserving) {
            forceInvalidate()
        } else {
            cachedValue = null
        }
    }

    fun forceInvalidate() = update(getNewGetter())

    fun update(getter: GetterAsync<Unit, T>) {
        onNext(getter)
        cachedValue = CachedAsync(getter).toOutdatable(getterLifetime)
        getterLifetime?.let(invalidateWaiter::start)
    }

    override fun onSubscribed(observer: Observer<in GetterAsync<Unit, T>>) {
        super.onSubscribed(observer)
        actualGetter.handle(
                ifNotNull = observer::onNext,
                ifNull = this::forceInvalidate
        )
    }

    protected abstract fun getNewGetter(): GetterAsync<Unit, T>

}