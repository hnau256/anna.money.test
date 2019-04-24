package org.hnau.anna.money.utils.rx

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.hnau.anna.money.converter.CurrencyConverterAsyncEmitter
import org.hnau.anna.money.converter.ecb.ECBCurrencyConverterProvider
import org.junit.Test
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.coroutines.launch
import ru.hnau.jutils.getter.SuspendGetter
import ru.hnau.jutils.getter.base.GetterAsync

class RxExtensionsKtTest {

    @Test
    fun test() {

        runBlocking {

            val emitter =
                    CurrencyConverterAsyncEmitter(
                            currencyConverterProvider = ECBCurrencyConverterProvider()
                    ).toSync { coroutine ->
                        Dispatchers.IO.launch { coroutine() }
                    }

            emitter.subscribe {
                println(it)
            }

            emitter.subscribe {
                println(it)
            }


            ru.hnau.jutils.coroutines.delay(TimeValue.SECOND * 20)

        }

    }

}