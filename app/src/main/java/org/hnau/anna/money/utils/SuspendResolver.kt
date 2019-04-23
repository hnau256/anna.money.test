package org.hnau.anna.money.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class SuspendResolver<T : Any>(
        private val source: suspend () -> T
) {

    private var cachedResult: T? = null
    private var cachedThrowable: Throwable? = null

    private val mutex = Mutex()

    operator suspend fun invoke() = mutex.withLock<T> {

        cachedThrowable?.let { throw it }

        cachedResult?.let { return@withLock it }

        return@withLock try {
            source().also { result -> cachedResult = result }
        } catch (th: Throwable) {
            cachedThrowable = th
            throw th
        }

    }


}