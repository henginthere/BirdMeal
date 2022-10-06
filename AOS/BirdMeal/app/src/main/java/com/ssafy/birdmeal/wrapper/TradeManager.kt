package com.ssafy.birdmeal.wrapper

import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.web3j.abi.EventEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.BaseEventResponse
import org.web3j.protocol.core.methods.response.Log
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tuples.generated.Tuple2
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
class TradeManager : Contract {
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

    fun getTradeCreatedEvents(transactionReceipt: TransactionReceipt?): List<TradeCreatedEventResponse> {
        val valueList = extractEventParametersWithLog(TRADECREATED_EVENT, transactionReceipt)
        val responses = ArrayList<TradeCreatedEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = TradeCreatedEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.tradeAddress = eventValues.indexedValues[0].value as String
            responses.add(typedResponse)
        }
        return responses
    }

    fun tradeCreatedEventFlowable(filter: EthFilter): Flowable<TradeCreatedEventResponse> {
        return web3j.ethLogFlowable(filter).map(object : Function<Log, TradeCreatedEventResponse> {
            override fun apply(log: Log): TradeCreatedEventResponse {
                val eventValues = extractEventParametersWithLog(TRADECREATED_EVENT, log)
                val typedResponse = TradeCreatedEventResponse()
                typedResponse.log = log
                typedResponse.tradeAddress = eventValues.indexedValues[0].value as String
                return typedResponse
            }
        })
    }

    fun tradeCreatedEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<TradeCreatedEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(TRADECREATED_EVENT))
        return tradeCreatedEventFlowable(filter)
    }

    fun createTrade(_name: String?, _price: BigInteger?): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_CREATETRADE,
            Arrays.asList<Type<*>>(
                Utf8String(_name),
                Uint256(_price)
            ), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun products(param0: String?, param1: BigInteger?): RemoteFunctionCall<Tuple2<String, String>> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_PRODUCTS,
            Arrays.asList<Type<*>>(
                Address(160, param0),
                Uint256(param1)
            ),
            Arrays.asList<TypeReference<*>>(
                object : TypeReference<Utf8String?>() {},
                object : TypeReference<Address?>() {})
        )
        return RemoteFunctionCall(
            function
        ) {
            val results = executeCallMultipleValueReturn(function)
            Tuple2(
                results[0].value as String,
                results[1].value as String
            )
        }
    }

    class TradeCreatedEventResponse : BaseEventResponse() {
        var tradeAddress: String? = null
    }

    companion object {
        const val BINARY = "Bin file was not provided"
        const val FUNC_CREATETRADE = "createTrade"
        const val FUNC_PRODUCTS = "products"
        val TRADECREATED_EVENT = Event("TradeCreated",
            Arrays.asList<TypeReference<*>>(object : TypeReference<Address?>(true) {})
        )

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): TradeManager {
            return TradeManager(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): TradeManager {
            return TradeManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): TradeManager {
            return TradeManager(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): TradeManager {
            return TradeManager(contractAddress, web3j, transactionManager, contractGasProvider)
        }
    }
}