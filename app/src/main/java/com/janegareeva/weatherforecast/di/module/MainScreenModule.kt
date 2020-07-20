package com.janegareeva.weatherforecast.di.module

import com.janegareeva.weatherforecast.ui.main.MainScreenContract
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule(val view: MainScreenContract.MainView) {

    @Provides
    fun provideView(): MainScreenContract.MainView = view
}