package com.janegareeva.weatherforecast.di.module

import com.janegareeva.weatherforecast.api.ApiConfig
import com.janegareeva.weatherforecast.api.ApiService
import com.janegareeva.weatherforecast.api.HeaderInterceptor
import com.janegareeva.weatherforecast.ui.base.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class ApiServiceModule {
    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(): String {
        return ApiConfig.baseUrl
    }

    @Provides
    @AppScope
    fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    @AppScope
    fun provideHttpClient(
        headerInterceptor: HeaderInterceptor,
        httpInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(headerInterceptor)
            .addInterceptor(httpInterceptor)
            .build()
    }

    @Provides
    @AppScope
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @AppScope
    fun provideRxJavaAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @AppScope
    fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(client)
            .build()
    }

    @Provides
    @AppScope
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "base_url"
    }
}