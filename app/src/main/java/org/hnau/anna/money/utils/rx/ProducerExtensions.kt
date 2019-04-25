package org.hnau.anna.money.utils.rx

import android.os.Handler
import ru.hnau.androidutils.utils.handler.HandlerWaiter
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.producer.Producer
import ru.hnau.jutils.producer.WrapProducer

/**
 * Использование вложенных Observable.combineLatest может привести к тому,
 * что генерируется синхронная последовательность промежуточных значений.
 * Эта функция позволяет их пропустить
 */
fun <T : Any> Producer<T>.prorogue(
        pause: TimeValue,
        handler: Handler
): Producer<T> {
    lateinit var proxy: WrapProducer.Proxy<T>
    lateinit var value: T
    val waiter = HandlerWaiter(
            handler = handler,
            block = { proxy.call(value) }
    )
    return wrap<T> { newValue ->
        value = newValue
        proxy = this
        waiter.start(pause)
    }
}