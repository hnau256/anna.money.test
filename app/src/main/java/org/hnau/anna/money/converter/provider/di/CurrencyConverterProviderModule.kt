package org.hnau.anna.money.converter.provider.di

import dagger.Module
import dagger.Provides
import org.hnau.anna.money.converter.provider.CurrencyConverterProvider
import org.hnau.anna.money.converter.ecb.ECBCurrencyConverterProvider
import javax.inject.Singleton


@Module
class CurrencyConverterProviderModule {

    @Provides
    @Singleton
    fun provideCurrencyConverterProvider(): CurrencyConverterProvider =
            ECBCurrencyConverterProvider()


}