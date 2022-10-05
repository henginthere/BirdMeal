package com.ssafy.birdmeal.utils

// 서버 통신
const val BASE_URL = "http://j7d101.p.ssafy.io:8081/api/"

// JWT
const val JWT = "Authorization"

// 유저 정보
const val USER_SEQ = "user_seq"

// 지갑 private_key 비밀번호
const val WALLET_PASSWORD = "wallet_password"

// 부트페이 API ID
const val PAY_APPLICATION_ID = "633b9616cf9f6d001e9268c9"

// TAG
const val TAG = "TAG"

// 지갑 생성 상태
const val WALLET_SELECT = "select"
const val WALLET_CREATE = "create"
const val WALLET_PRIVATE = "private"
const val WALLET_COMPLETE = "complete"

// 싸피넷 정보
const val BOLCKCHAIN_NETWORK_URL = "http://20.196.209.2:8545"
const val BOLCKCHAIN_NETWORK_CHAINID = 31221L

// 컨트랙트 주소
const val CA_ELENA = "0x24Dae25c6005577E20D6c6927B56564d97C8aBe3" // 토큰 컨트랙트
const val CA_FUNDING = "0x1272AD925859Dd4e485b6d36AD941A5163e380De" // 기부 컨트랙트
const val CA_TRADE_MANAGER = "0x5F0A916710f2Cd1fd96D2896C0026B9585C0224f" // 거래 매니저 컨트랙트
const val CA_EXCHANGE = "0x792bdc2cA619B26B55fe22cC56fe1336A2408dDc"   // 환전소 컨트랙트
const val CA_NFT = "0xCb0eC79c8Cc01a2f257Ba41125e1D65B1e5473b3"   // NFT 컨트랙트

// 주소 검색 띄울 주소
const val ADDRESS_API_URL = "https://searchaddress-2c847.web.app/"

// 상태바 색 변경 이름
const val WHITE = "white"
const val BEIGE = "beige"

// 결식아동 토큰 충전 상태
const val FILL_ALREADY = "already"  // 이미 금주에 토큰 충전 완료
const val FILL_COMPLETED = "completed" // 토큰 충전 완료
const val FILL_POSSIBLE = "possible" // 토큰 충전 가능
const val FILL_OVER = "over" // 토큰 보유액 10만 이상
const val FILL_ERR = "err" // 컨트랙트 통신 오류

// 기부 가능한지 상태
const val DONATE_EMPTY = "empty" // 금액을 입력하지 않음
const val DONATE_BALANCE = "balance" // 금액이 부족함
const val DONATE_POSSIBLE = "possible" // 기부 가능
const val DONATE_COMPLETED = "completed" // 기부 완료
const val DONATE_ERR = "err" // 컨트랙트 통신 오류

// Info 화면 다시보기 여부
const val INFO_WALLET = "info_wallet" // 지갑 인포

// 에러 종류
const val ERR_GET_DONATION_AMOUNT = "getDonationAmount"
const val ERR_DO_DONATE = "doDonate"
const val ERR_GET_USER_TOKEN = "getUserTokenValue"
const val ERR_FILLUP_TOKEN = "fillUpToken"
const val ERR_FILLUP_TOKEN_CHILD = "fillUpTokenChild"
const val ERR_UPDATE_ORDER_STATE = "updateOrderState"
const val ERR_UPDATE_CANCEL = "updateCancel"
const val ERR_UPDATE_REFUND = "updateRefund"
const val ERR_DO_MINTING = "doMinting"
const val ERR_GET_MY_NFT = "getMyNft"

// 주문 상세 타입
const val ORDER_DETAIL_TO_STATE = "oderDetailToState"
const val ORDER_DETAIL_CANCEL = "oderDetailCancel"
const val ORDER_DETAIL_REFUND = "oderDetailRefund"