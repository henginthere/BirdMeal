package com.ssafy.birdmeal.db

import androidx.room.*
import com.ssafy.birdmeal.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: CartEntity)

    @Update
    fun update(cart: CartEntity)

    @Delete
    fun delete(cart: CartEntity)

    @Query("SELECT * FROM t_cart")
    fun getCartList() : Flow<List<CartEntity>>


}
