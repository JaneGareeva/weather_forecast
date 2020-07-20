package com.janegareeva.weatherforecast.di.module

import com.janegareeva.weatherforecast.db.repository.CityInfoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityInfoRepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(repository: CityInfoRepository): CityInfoRepository {
        return repository
    }
}