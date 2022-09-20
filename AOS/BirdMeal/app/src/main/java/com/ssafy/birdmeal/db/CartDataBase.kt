package com.ssafy.birdmeal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.birdmeal.model.entity.CartEntity

/*
   entities = 사용할 엔티티 선언
   version = 엔티티 구조 변경 시 구분해주는 역할 -> 엔티티 수정 시 그대로두면 에러남
   exportSchema = 스키마 내보내기 설정
*/
@Database(entities = [CartEntity::class], version = 2)
abstract class CartDataBase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}