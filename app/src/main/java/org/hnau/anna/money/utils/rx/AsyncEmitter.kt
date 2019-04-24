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


/**
 * Emitter, который кеширует результат выполнения асинхронной операции на [getterLifetime]
 * и обновляет значение раз в [getterLifetime] если наблюдаем
 */
abstract class AsyncEmitter<T : Any>(
        private val getterLifetime: TimeValue? = null
) : Emitter<GetterAsync<Unit, T>>() {

    private var cachedValue: Outdatable<CachedAsync<T>>? = null

    //cachedValue существует, не устарел и не завершился с ошибкой
    private val actualGetter: GetterAsync<Unit, T>?
        get() = cachedValue?.value?.takeIf { !it.finishedWithError }

    private val invalidateWaiter = HandlerWaiter(
            handler = Handler(),
            block = this::invalidate
    )

    fun invalidate() = synchronized(this) {
        if (isObserving) {
            //Если кто нибудь наблюдает, то обновить значнеие
            forceInvalidate()
        } else {
            //Если никто не наблюдает, то очистить значение
            cachedValue = null
        }
    }

    fun forceInvalidate() =
            update(getNewGetter())

    fun update(getter: GetterAsync<Unit, T>) {
        onNext(getter)
        //Значение перестанет быть актуальным чреез getterLifetime
        cachedValue = CachedAsync(getter).toOutdatable(getterLifetime)
        //Запланировать обновление значения через getterLifetime
        getterLifetime?.let(invalidateWaiter::start)
    }

    override fun onSubscribed(observer: Observer<in GetterAsync<Unit, T>>) {
        super.onSubscribed(observer)
        //Есть значение, то сообщить о нем, иначе обновить значение
        actualGetter.handle(
                ifNotNull = observer::onNext,
                ifNull = this::forceInvalidate
        )
    }

    protected abstract fun getNewGetter(): GetterAsync<Unit, T>

}