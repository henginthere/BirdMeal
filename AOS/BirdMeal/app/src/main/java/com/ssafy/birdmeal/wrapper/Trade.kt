package com.dttmm.web3test.wrapper

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
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
class Trade : Contract {
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

    fun addOrderSheet(
        orderTransaction: String?,
        num: BigInteger?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_ADDORDERSHEET,
            Arrays.asList<Type<*>>(
                Utf8String(orderTransaction),
                Uint256(num)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun buying(num: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_BUYING,
            Arrays.asList<Type<*>>(Uint256(num)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun name(): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_NAME,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun orderSheet(param0: String?): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_ORDERSHEET,
            Arrays.asList<Type<*>>(Utf8String(param0)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun paying(orderTransaction: String?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_PAYING,
            Arrays.asList<Type<*>>(Utf8String(orderTransaction)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun price(): RemoteFunctionCall<BigInteger> {
        val function = Function(
            FUNC_PRICE,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun seller(): RemoteFunctionCall<String> {
        val function = Function(
            FUNC_SELLER,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Address?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun setName(_name: String?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_SETNAME,
            Arrays.asList<Type<*>>(Utf8String(_name)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun setPrice(_price: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_SETPRICE,
            Arrays.asList<Type<*>>(Uint256(_price)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    companion object {
        const val BINARY = "Bin file was not provided"
        const val FUNC_ADDORDERSHEET = "addOrderSheet"
        const val FUNC_BUYING = "buying"
        const val FUNC_NAME = "name"
        const val FUNC_ORDERSHEET = "orderSheet"
        const val FUNC_PAYING = "paying"
        const val FUNC_PRICE = "price"
        const val FUNC_SELLER = "seller"
        const val FUNC_SETNAME = "setName"
        const val FUNC_SETPRICE = "setPrice"

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Trade {
            return Trade(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Trade {
            return Trade(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): Trade {
            return Trade(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): Trade {
            return Trade(contractAddress, web3j, transactionManager, contractGasProvider)
        }
    }
}
