import {ElenaTokenContract, TradeManagerContract} from "./web3Config";
import {FundingContractAddress} from "./CA"

import router from "@/router/index.js"

var Account;


//메타마스크 로그인
//const web3 = new Web3(window.ethereum);

export function MetaMaskLogin() {
    //메타마스크 설치확인
    if (typeof window.ethereum !== 'undefined') {
        console.log('Ethereum successfully detected!')
        
        //계정연결
        window.ethereum
        .request({ method: "eth_requestAccounts" })
        .then(accounts => {
          console.log("연결된 계정", accounts);
          Account = accounts
        });
      
    //설치가 안되었다면 에러 발생
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
    if(Account !== null){
      console.log(Account[0])
      const res = await ElenaTokenContract.methods
      .balanceOf(Account[0]).call();
      console.log(res)
      return res;
    }else{
      return "없다"}

}


export var approve = async () => {
  const res = await ElenaTokenContract.methods
  .approve(FundingContractAddress, 1000*10*18).send({from:Account[0]}).then(console.log);
  console.log(res)
  return res;
}

export var createTrade = async (name, price) => {
  const res = await TradeManagerContract.methods
  .createTrade(name, price).send({from:Account[0]});
  
  return res
}


