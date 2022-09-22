import Web3 from "web3";
import {ElenaTokenAbi, FundingAbi, ExchangeAbi, TradeManagerAbi, TradeAbi} from "./ABI.js"
import {ElenaTokenContractAddress, FundingContractAddress,ExchangeContractAddress, TradeManagerContractAddress} from "./CA"

//web3 연결
export const web3 = new Web3(window.ethereum);


export const FundingContract = new web3.eth.Contract(
    FundingAbi,
    FundingContractAddress
);

export const ElenaTokenContract = new web3.eth.Contract(
    ElenaTokenAbi,
    ElenaTokenContractAddress
);

export const ExchangeContract = new web3.eth.Contract(
    ExchangeAbi,
    ExchangeContractAddress
);

export const TradeManagerContract = new web3.eth.Contract(
    TradeManagerAbi,
    TradeManagerContractAddress
);



export const TradeContract = function(productCa) {
    const res = new web3.eth.Contract(
        TradeAbi,
        productCa
        );
    return res
}


