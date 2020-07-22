package com.janegareeva.weatherforecast.di.component

import com.janegareeva.weatherforecast.di.module.DetailScreenModule
import com.janegareeva.weatherforecast.ui.base.ActivityScope
import com.janegareeva.weatherforecast.ui.detail.DetailActivity
import dagger.Component

@ActivityScope
@Component(modules = [DetailScreenModule::class], dependencies = [AppComponent::class])
interface DetailScreenComponent {

    fun inject(activity: DetailActivity)
}