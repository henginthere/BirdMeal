<template>
  <router-view></router-view>
</template>

<script setup>
import { MetaMaskLogin, balanceOf } from '@/web3util/events';
import { authState } from '@/stores/auth.js';

/** Variable */

/** Store */
const auth = authState();

/** function */
MetaMaskLogin()
  .then(balanceOf)
  .then((r) => {
    auth.setBalance(r);
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
