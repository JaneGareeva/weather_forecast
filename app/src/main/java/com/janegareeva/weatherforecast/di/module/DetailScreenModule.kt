package com.janegareeva.weatherforecast.di.module

import com.janegareeva.weatherforecast.ui.detail.DetailScreenContract
import dagger.Module
import dagger.Provides

@Module
class DetailScreenModule(val view: DetailScreenContract.View) {

    @Provides
    fun provideView(): DetailScreenContract.View = view
}