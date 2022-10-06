package com.ssafy.birdmeal.datasource.local

import com.ssafy.birdmeal.db.CartDao
import com.ssafy.birdmeal.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartLocalDataSource @Inject constructor(
    private val cartDao: CartDao
){
    fun insertCart(cart: CartEntity) = cartDao.insert(cart)
    fun updateCart(cart: CartEntity) = cartDao.update(cart)
    fun deleteCart(cart: CartEntity) = cartDao.delete(cart)
    fun clearCart() = cartDao.clear()
    fun getCartList(): Flow<List<CartEntity>> = cartDao.getCartList()
}