package org.hnau.anna.money.api

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.hnau.anna.money.api.entity.CurrencyRateBasedOnEuro
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET


interface CurrencyRateProvider {

    companion object {

        private const val BASE_URL = "https://www.ecb.europa.eu/"

        val INSTANCE = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(CurrencyRateProvider::class.java)

    }

    @GET("stats/eurofxref/eurofxref-daily.xml")
    fun getCurrencyRate(): Observable<CurrencyRateBasedOnEuro>

}