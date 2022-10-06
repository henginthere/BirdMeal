package com.ssafy.birdmeal.wrapper

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger
import java.util.*

/**
 *
 * Auto generated code.
 *
 * **Do not modify!**
 *
 * Please use the [web3j command line tools](https://docs.web3j.io/command_line.html),
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * [codegen module](https://github.com/web3j/web3j/tree/master/codegen) to update.
 *
 *
 * Generated with web3j version 1.4.1.
 */
class Funding : Contract {
    @Deprecated("")
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit
    ) {
    }

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, credentials, contractGasProvider
    ) {
    }

    @Deprecated("")
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit
    ) {
    }

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, contractGasProvider
    ) {
    }

    fun currentBalance(): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_CURRENTBALANCE,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun donationHistory(param0: String?, param1: BigInteger?): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_DONATIONHISTORY,
            Arrays.asList<Type<*>>(
                Address(160, param0),
                Uint256(param1)
            ),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun funding(sendMoney: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_FUNDING,
            Arrays.asList<Type<*>>(Uint256(sendMoney)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    val donationHistory: RemoteFunctionCall<List<*>>
        get() {
            val function = Function(
                FUNC_GETDONATIONHISTORY,
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object :
                    TypeReference<DynamicArray<Uint256?>?>() {})
            )
            return RemoteFunctionCall(
                function
            ) {
                val result =
                    executeCallSingleValueReturn<Type<*>, List<*>>(
                        function,
                        List::class.java
                    ) as List<Type<*>>
                convertToNative<Type<*>, Any>(
                    result
                )
            }
        }
    val totalWithdrawal: RemoteFunctionCall<BigInteger>
        get() {
            val function = Function(
                FUNC_GETTOTALWITHDRAWAL,
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
            )
            return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
        }

    fun takeMoney(isChild: Boolean?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_TAKEMONEY,
            Arrays.asList<Type<*>>(Bool(isChild)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    companion object {
        const val BINARY = "Bin file was not provided"
        const val FUNC_CURRENTBALANCE = "currentBalance"
        const val FUNC_DONATIONHISTORY = "donationHistory"
        const val FUNC_FUNDING = "funding"
        const val FUNC_GETDONATIONHISTORY = "getDonationHistory"
        const val FUNC_GETTOTALWITHDRAWAL = "getTotalWithdrawal"
        const val FUNC_TAKEMONEY = "takeMoney"

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Funding {
            return Funding(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Funding {
            return Funding(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): Funding {
            return Funding(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): Funding {
            return Funding(contractAddress, web3j, transactionManager, contractGasProvider)
        }
    }
}