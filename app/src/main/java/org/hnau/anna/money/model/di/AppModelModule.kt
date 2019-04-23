package org.hnau.anna.money.model.di

import dagger.Module
import dagger.Provides
import org.hnau.anna.money.model.AppModel
import javax.inject.Singleton


@Module
class AppModelModule {

    @Provides
    @Singleton
    fun provideAppModel() = AppModel()

}