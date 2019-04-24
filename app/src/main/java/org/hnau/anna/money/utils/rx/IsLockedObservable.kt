package org.hnau.anna.money.utils.rx


/**
 * Observable, который оборачивает выполнение асинхронных операций и отслеживает, выполняется ли хоть одна
 */
class IsLockedObservable : ActualObservable<Boolean>(false) {

    private var locked = false
        set(value) {
            if (field != value) {
                field = value
                onNext(value)
            }
        }

    private var locksCount = 0
        set(value) {
            field = value
            locked = value > 0
        }

    private fun changeLockedCount(delta: Int) =
            synchronized(this) { locksCount += delta }

    @Suppress("DEPRECATION")
    operator inline fun <T> invoke(block: () -> T): T {
        return try {
            incLockCount()
            block()
        } finally {
            decLockCount()
        }
    }

    @Deprecated("Should have been private, but inline fun invoke(block: () -> Unit) expected using only public methods")
    fun incLockCount() = changeLockedCount(1)

    @Deprecated("Should have been private, but inline fun invoke(block: () -> Unit) expected using only public methods")
    fun decLockCount() = changeLockedCount(-1)

}