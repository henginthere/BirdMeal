package com.ssafy.birdmeal.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ssafy.birdmeal.db.CartDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // SharedPreferences DI
    @Singleton
    @Provides
    fun provideSharedPreferences(app : Application) =
        app.getSharedPreferences("app_preference", Context.MODE_PRIVATE)!!

    // RoomDB DI
    @Singleton
    @Provides
    fun provideCartDataBse(app : Application) =
        Room.databaseBuilder(app, CartDataBase::class.java, "cart_db")
            .fallbackToDestructiveMigration() // 버전 변경 시 기존 데이터 삭제
            .build()

    // Dao DI
    @Singleton
    @Provides
    fun provideCartDao(db : CartDataBase) = db.cartDao()

}