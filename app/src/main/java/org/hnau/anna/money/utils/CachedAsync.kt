package org.hnau.anna.money.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.hnau.jutils.getter.base.GetterAsync
import ru.hnau.jutils.getter.base.get


class CachedAsync<T : Any>(
        private val source: GetterAsync<Unit, T>
) : GetterAsync<Unit, T> {

    private var cachedResult: T? = null
    private var cachedThrowable: Throwable? = null

    private val mutex = Mutex()

    val finishedWithError: Boolean
        get() = cachedThrowable != null

    override suspend fun get(param: Unit) = mutex.withLock<T> {

        cachedThrowable?.let { th -> throw th }
        cachedResult?.let { result -> return@withLock result }

        return@withLock try {
            source.get().also { result -> cachedResult = result }
        } catch (th: Throwable) {
            cachedThrowable = th
            throw th
        }

    }


}