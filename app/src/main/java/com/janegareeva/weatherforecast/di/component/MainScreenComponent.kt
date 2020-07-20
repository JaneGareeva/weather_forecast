package com.janegareeva.weatherforecast.di.component

import com.janegareeva.weatherforecast.di.module.MainScreenModule
import com.janegareeva.weatherforecast.ui.base.ActivityScope
import com.janegareeva.weatherforecast.ui.main.MainScreenActivity
import dagger.Component

@ActivityScope
@Component(modules = [MainScreenModule::class])
interface MainScreenComponent {
    fun inject(activity: MainScreenActivity)
}