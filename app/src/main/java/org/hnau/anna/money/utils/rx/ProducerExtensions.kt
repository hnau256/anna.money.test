package org.hnau.anna.money.utils.rx

import org.hnau.anna.money.R
import ru.hnau.jutils.getter.base.GetterAsync
import ru.hnau.jutils.getter.base.get
import ru.hnau.jutils.possible.Possible
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.tryOrElse


fun <T : Any> Producer<GetterAsync<Unit, T>>.toSync(
        coroutinesExecutor: (suspend () -> Unit) -> Unit
): Producer<Possible<T>> {
    var currentAsyncGetter: GetterAsync<Unit, T>?
    return wrap<Possible<T>> { asyncGetter ->
        currentAsyncGetter = asyncGetter
        coroutinesExecutor {
            val result = Possible.trySuccessCatchError { asyncGetter.get() }
            synchronized(this@toSync) {
                if (currentAsyncGetter == asyncGetter) {
                    call(result)
                }
            }
        }
    }
}