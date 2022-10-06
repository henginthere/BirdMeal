package com.ssafy.birdmeal.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.birdmeal.api.*
import com.ssafy.birdmeal.utils.AccessTokenInterceptor
import com.ssafy.birdmeal.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.plugins.RxJavaPlugins
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
    fun provideOauthApi(retrofit: Retrofit) : Oauth2Api {
        return retrofit.create(Oauth2Api::class.java)
    }

    // ProductApi DI
    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit) : ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    // ProductApi DI
    @Provides
    @Singleton
    fun provideSellerApi(retrofit: Retrofit) : SellerApi {
        return retrofit.create(SellerApi::class.java)
    }
    
    // UserApi DI
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

    // DonationApi DI
    @Provides
    @Singleton
    fun provideDonationApi(retrofit: Retrofit) : DonationApi {
        return retrofit.create(DonationApi::class.java)
    }

    // OrderApi DI
    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit) : OrderApi {
        return retrofit.create(OrderApi::class.java)
    }

    // NftApi DI
    @Provides
    @Singleton
    fun provideNftApi(retrofit: Retrofit) : NftApi {
        return retrofit.create(NftApi::class.java)
    }
}