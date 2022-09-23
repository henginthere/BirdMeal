import {ElenaTokenContract, TradeManagerContract, TradeContract} from "./web3Config";
import {FundingContractAddress} from "./CA"

import router from "@/router/index.js"

var Account;


//메타마스크 로그인
//const web3 = new Web3(window.ethereum);

export async function MetaMaskLogin() {
    let res = null; 
    //메타마스크 설치확인
    if (typeof window.ethereum !== 'undefined') {
        console.log('Ethereum successfully detected!')
        //계정연결
        const accounts = await window.ethereum
        .request({ method: "eth_requestAccounts" })
        Account = accounts[0]

        return Account
    //설치가 안되었다면 가이드 페이지로 보냄
      } else {
        // if the provider is not detected, detectEthereumProvider resolves to null
        router.push("/guide")
      }

}

export var totalSupply = async () => {
    const res = await ElenaTokenContract.methods
    .totalSupply().call();
    return res;
}

// 로그인한 사람의 계정 잔액
export var balanceOf = async () => {
    const res = await ElenaTokenContract.methods
    .balanceOf(Account).call();
    return res;
}

// export var balanceOf = function(){
//   const res = ElenaTokenContract.methods
//   .balanceOf(Account).call().then(r=>console.log(r))
//   return res
// }


export var approve = async () => {
  const res = await ElenaTokenContract.methods
  .approve(FundingContractAddress, 1000*10*18).send({from:Account}).then(console.log);
  console.log(res)
  return res;
}

export var createTrade = async (name, price) => {
  const res = await TradeManagerContract.methods
  .createTrade(name, price).send({from:Account});
  
  return res
}


export const updateName = async (name, ca) => {
  const res = await TradeContract(ca).methods
  .setName(name).send({from:Account});
  return res
  };

export const updatePrice = async (price, ca) => {
  const res = await TradeContract(ca).methods
  .setPrice(price).send({from:Account});
  return res
  };
