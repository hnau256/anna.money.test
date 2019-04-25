package org.hnau.anna.money.converter.provider.di

import dagger.Component
import org.hnau.anna.money.model.AppModel
import javax.inject.Singleton

@Component(modules = [CurrencyConverterProviderModule::class])
@Singleton
interface CurrencyConverterProviderComponent {

    fun inject(appModel: AppModel)

}