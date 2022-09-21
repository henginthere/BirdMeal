package com.dttmm.web3test.wrapper

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
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
class Storage : Contract {
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

    fun setNum(num: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = Function(
            FUNC_SETNUM,
            Arrays.asList<Type<*>>(Uint256(num)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    val num: RemoteFunctionCall<BigInteger>
        get() {
            val function = Function(
                FUNC_GETNUM,
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
            )
            return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
        }

    companion object {
        const val BINARY = "Bin file was not provided"
        const val FUNC_SETNUM = "setNum"
        const val FUNC_GETNUM = "getNum"

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Storage {
            return Storage(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Storage {
            return Storage(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): Storage {
            return Storage(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): Storage {
            return Storage(contractAddress, web3j, transactionManager, contractGasProvider)
        }
    }
}
