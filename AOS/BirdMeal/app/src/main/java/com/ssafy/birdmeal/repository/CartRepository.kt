package com.ssafy.birdmeal.repository

import com.ssafy.birdmeal.datasource.local.CartLocalDataSource
import com.ssafy.birdmeal.model.entity.CartEntity
import com.ssafy.birdmeal.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartLocalDataSource: CartLocalDataSource
) {

    fun insertCart(cart: CartEntity) = cartLocalDataSource.insertCart(cart)
    fun updateCart(cart: CartEntity) = cartLocalDataSource.updateCart(cart)
    fun deleteCart(cart: CartEntity) = cartLocalDataSource.deleteCart(cart)

    fun getCartList(): Flow<Result<List<CartEntity>>> = flow {
        emit(Result.Loading)

        cartLocalDataSource.getCartList().collect {
            emit(Result.Success(it))
        }
    }.catch { e ->
        emit(Result.Error(e))
    }

}