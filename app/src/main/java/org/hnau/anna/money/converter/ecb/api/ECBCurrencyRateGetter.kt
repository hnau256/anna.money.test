package org.hnau.anna.money.converter.ecb.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import org.hnau.anna.money.converter.ecb.api.entity.ECBData
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import java.time.Duration

/**
 * Retrofit2 интерфейс для получения курса валют с https://www.ecb.europa.eu/
 */
interface ECBCurrencyRateGetter {

    companion object {

        private const val BASE_URL = "https://www.ecb.europa.eu/"

        private val CONNECTION_TIMEOUT = Duration.ofSeconds(20)

        val INSTANCE = run {

            val okHttpClient = OkHttpClient.Builder()
                    .callTimeout(CONNECTION_TIMEOUT)
                    .build()

            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
                    .create(ECBCurrencyRateGetter::class.java)
        }

    }

    @GET("stats/eurofxref/eurofxref-daily.xml")
    fun getCurrencyRate(): Deferred<ECBData>

}