package com.dttmm.web3test.wrapper

import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.web3j.abi.EventEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.BaseEventResponse
import org.web3j.protocol.core.methods.response.Log
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
class Elena : Contract {
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

    fun getApprovalEvents(transactionReceipt: TransactionReceipt?): List<ApprovalEventResponse> {
        val valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt)
        val responses = ArrayList<ApprovalEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = ApprovalEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.owner = eventValues.indexedValues[0].value as String
            typedResponse.spender = eventValues.indexedValues[1].value as String
            typedResponse.value = eventValues.nonIndexedValues[0].value as BigInteger
            responses.add(typedResponse)
        }
        return responses
    }

    fun approvalEventFlowable(filter: EthFilter?): Flowable<ApprovalEventResponse> {
        return web3j.ethLogFlowable(filter).map(object : Function<Log?, ApprovalEventResponse> {
            override fun apply(log: Log): ApprovalEventResponse {
                val eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log)
                val typedResponse = ApprovalEventResponse()
                typedResponse.log = log
                typedResponse.owner = eventValues.indexedValues[0].value as String
                typedResponse.spender = eventValues.indexedValues[1].value as String
                typedResponse.value = eventValues.nonIndexedValues[0].value as BigInteger
                return typedResponse
            }
        })
    }

    fun approvalEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<ApprovalEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT))
        return approvalEventFlowable(filter)
    }

    fun getTransferEvents(transactionReceipt: TransactionReceipt?): List<TransferEventResponse> {
        val valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt)
        val responses = ArrayList<TransferEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = TransferEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.from = eventValues.indexedValues[0].value as String
            typedResponse.to = eventValues.indexedValues[1].value as String
            typedResponse.value = eventValues.nonIndexedValues[0].value as BigInteger
            responses.add(typedResponse)
        }
        return responses
    }

    fun transferEventFlowable(filter: EthFilter?): Flowable<TransferEventResponse> {
        return web3j.ethLogFlowable(filter).map(object : Function<Log?, TransferEventResponse> {
            override fun apply(log: Log): TransferEventResponse {
                val eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log)
                val typedResponse = TransferEventResponse()
                typedResponse.log = log
                typedResponse.from = eventValues.indexedValues[0].value as String
                typedResponse.to = eventValues.indexedValues[1].value as String
                typedResponse.value = eventValues.nonIndexedValues[0].value as BigInteger
                return typedResponse
            }
        })
    }

    fun transferEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<TransferEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT))
        return transferEventFlowable(filter)
    }

    fun allowance(owner: String?, spender: String?): RemoteFunctionCall<BigInteger> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_ALLOWANCE,
            Arrays.asList<Type<*>>(
                Address(160, owner),
                Address(160, spender)
            ),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun approve(spender: String?, amount: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_APPROVE,
            Arrays.asList<Type<*>>(
                Address(160, spender),
                Uint256(amount)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun balanceOf(account: String?): RemoteFunctionCall<BigInteger> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_BALANCEOF,
            Arrays.asList<Type<*>>(Address(160, account)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun decimals(): RemoteFunctionCall<BigInteger> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_DECIMALS,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint8?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun decreaseAllowance(
        spender: String?,
        subtractedValue: BigInteger?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_DECREASEALLOWANCE,
            Arrays.asList<Type<*>>(
                Address(160, spender),
                Uint256(subtractedValue)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun increaseAllowance(
        spender: String?,
        addedValue: BigInteger?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_INCREASEALLOWANCE,
            Arrays.asList<Type<*>>(
                Address(160, spender),
                Uint256(addedValue)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun name(): RemoteFunctionCall<String> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_NAME,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun symbol(): RemoteFunctionCall<String> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_SYMBOL,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    fun totalSupply(): RemoteFunctionCall<BigInteger> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_TOTALSUPPLY,
            Arrays.asList(),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, BigInteger::class.java)
    }

    fun transfer(to: String?, amount: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_TRANSFER,
            Arrays.asList<Type<*>>(
                Address(160, to),
                Uint256(amount)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun transferFrom(
        from: String?,
        to: String?,
        amount: BigInteger?
    ): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_TRANSFERFROM,
            Arrays.asList<Type<*>>(
                Address(160, from),
                Address(160, to),
                Uint256(amount)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    class ApprovalEventResponse : BaseEventResponse() {
        var owner: String? = null
        var spender: String? = null
        var value: BigInteger? = null
    }

    class TransferEventResponse : BaseEventResponse() {
        var from: String? = null
        var to: String? = null
        var value: BigInteger? = null
    }

    companion object {
        const val BINARY = "Bin file was not provided"
        const val FUNC_ALLOWANCE = "allowance"
        const val FUNC_APPROVE = "approve"
        const val FUNC_BALANCEOF = "balanceOf"
        const val FUNC_DECIMALS = "decimals"
        const val FUNC_DECREASEALLOWANCE = "decreaseAllowance"
        const val FUNC_INCREASEALLOWANCE = "increaseAllowance"
        const val FUNC_NAME = "name"
        const val FUNC_SYMBOL = "symbol"
        const val FUNC_TOTALSUPPLY = "totalSupply"
        const val FUNC_TRANSFER = "transfer"
        const val FUNC_TRANSFERFROM = "transferFrom"
        val APPROVAL_EVENT = Event(
            "Approval",
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Uint256?>() {})
        )
        val TRANSFER_EVENT = Event(
            "Transfer",
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Address?>(true) {},
                object : TypeReference<Uint256?>() {})
        )

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Elena {
            return Elena(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): Elena {
            return Elena(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): Elena {
            return Elena(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): Elena {
            return Elena(contractAddress, web3j, transactionManager, contractGasProvider)
        }
    }
}
