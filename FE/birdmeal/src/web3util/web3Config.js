import Web3 from "web3";
import {ElenaTokenAbi, FundingAbi, ExchangeAbi, TradeManagerAbi, TradeAbi} from "./ABI.js"
import {ElenaTokenContractAddress, FundingContractAddress,ExchangeContractAddress, TradeManagerContractAddress} from "./CA"

/*
 window.ethereum은 웹사이트가 사용자의 이더리움 계정을 요청하고, 
사용자가 연결된 블록체인에서 데이터를 읽고, 
사용자가 메시지 및 트랜잭션에 서명하도록 제안할 수 있도록 한다.
*/

export const web3 = new Web3(window.ethereum);


// web3와 abi, Ca를 활용해 컨트랙트 불러오기
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


// trade의 경우 저장된 Ca가 없기에 events에서 바로 인자로 받아서 ca를 넣어주기위해 함수를 만듬
export const TradeContract = function(productCa) {
    const res = new web3.eth.Contract(
        TradeAbi,
        productCa
        );
    return res
}


