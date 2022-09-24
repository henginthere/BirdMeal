import {ElenaTokenContract, TradeManagerContract, TradeContract} from "./web3Config";
import {FundingContractAddress} from "./CA"

import router from "@/router/index.js"

var Account;


// 메타마스크 로그인

export async function MetaMaskLogin() {
    //메타마스크 설치확인
    // 주입된 프로바이더가 없다는건 메타마스크가 설치되지 않았다는 소리
    if (typeof window.ethereum !== 'undefined') {
        console.log('Ethereum successfully detected!')
        //계정연결
        const accounts = await window.ethereum
        .request({ method: "eth_requestAccounts" })
        // 계정 여러개 연결 가능한데 나는 계정 1개만 쓸거 임
        Account = accounts[0]

        return Account
    //설치가 안되었다면 가이드 페이지로 보냄
      } else {
        router.push("/guide")
      }

}


/* 
  로그인한 사람의 계정 잔액
  web3Config에서 작성해둔 컨트랙트를 불러온 후 
  컨트랙트의 함수를 호출해서 사용
  ether넷과 통신해야해서 client에서 export해서 사용하려면
  promise객체를 반환해야 함으로 async로 작성
  아래 모든 함수 모든 로직 동일 
*/
export var balanceOf = async () => {
    const res = await ElenaTokenContract.methods
    .balanceOf(Account).call();
    return res;
}



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
