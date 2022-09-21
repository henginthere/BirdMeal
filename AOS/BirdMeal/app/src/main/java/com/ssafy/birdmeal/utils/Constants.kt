package com.ssafy.birdmeal.utils

// 서버 통신
const val BASE_URL = "http://j7d101.p.ssafy.io:8081/api/"

// JWT
const val JWT = "JWT-AUTHENTICATION"

// 유저 정보
const val USER_SEQ = "user_seq"

// 지갑 private_key 비밀번호
const val WALLET_PASSWORD = "wallet_password"

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
const val CA_TRADE_MANAGER = "0xE2eC84CDfFCBde58b0B4ab71af6802214e43247e" // 거래 매니저 컨트랙트
const val CA_EXCHANGE = "0x792bdc2cA619B26B55fe22cC56fe1336A2408dDc"   // 환전소 컨트랙트
