package com.ssafy.birdmeal.di

import com.ssafy.birdmeal.utils.BOLCKCHAIN_NETWORK_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import java.math.BigInteger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BolckChainModule {

    // GasProvider DI
    @Singleton
    @Provides
    fun provideGasProvider() =
        StaticGasProvider(BigInteger.valueOf(0), BigInteger.valueOf(0x09184e72a000))

    // Web3j DI
    @Singleton
    @Provides
    fun provideWeb3() = Web3j.build(HttpService(BOLCKCHAIN_NETWORK_URL))

    // PollingProcessor DI
    @Singleton
    @Provides
    fun providePollingProcessor(web3: Web3j) = PollingTransactionReceiptProcessor(web3, 100000, 3)
}