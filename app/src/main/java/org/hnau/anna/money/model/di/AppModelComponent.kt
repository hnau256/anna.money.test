package org.hnau.anna.money.model.di

import dagger.Component
import org.hnau.anna.money.presenter.AppPresenter
import javax.inject.Singleton


@Component(modules = [AppModelModule::class])
@Singleton
interface AppModelComponent {

    fun inject(appPresenter: AppPresenter)

}