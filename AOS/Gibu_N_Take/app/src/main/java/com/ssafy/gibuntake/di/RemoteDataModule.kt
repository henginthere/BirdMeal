package com.ssafy.gibuntake.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.gibuntake.api.Oauth2Api
import com.ssafy.gibuntake.utils.AccessTokenInterceptor
import com.ssafy.gibuntake.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    // OkHttpClient DI
    @Provides
    @Singleton
    fun provideOkHttpClient(accessTokenInterceptor: AccessTokenInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(accessTokenInterceptor)
            .build()
    }

    // Gson DI
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    // Retrofit DI
    @Provides
    @Singleton
    fun provideRetrofitInstance(gson : Gson, client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    // Oauth2Api DI
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : Oauth2Api {
        return retrofit.create(Oauth2Api::class.java)
    }
}