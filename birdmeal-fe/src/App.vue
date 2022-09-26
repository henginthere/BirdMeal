<template>
  <router-view></router-view>
</template>

<script setup>
import { MetaMaskLogin, balanceOf } from '@/web3util/events';
import { authState } from '@/stores/auth.js';

/** Variable */

/** Store */
const auth = authState();

/** function
 * 페이지 진입 시 메타마스크 로그인을 시키고 이후
 * 로그인에 성공해서 계정정보를 받아오게 되면 계좌잔액을
 * 전역 state에 등록하기위해 다음과 같이 작성
 * 
 */
MetaMaskLogin()
  .then(balanceOf)
  .then((r) => {
    auth.setBalance(r);
    console.log(window.ethereum);
  });
</script>

<style>
div {
  font-family: 'GongGothic', sans-serif !important;
}
@font-face {
  font-family: 'GongGothic';
  src: url('@/assets/fonts/GongGothicMedium.ttf') format('truetype');
  font-weight: 400;
}

@font-face {
  font-family: 'GongGothic';
  src: url('@/assets/fonts/GongGothicLight.ttf') format('truetype');
  font-weight: 300;
}
</style>
