<template>
  <main-view v-if="auth.user"></main-view>
  <router-view v-else></router-view>
</template>

<script setup>
import { MetaMaskLogin, balanceOf } from '@/web3util/events';
import MainView from '@/views/MainView.vue';
import { authState } from '@/stores/auth.js';

/** Variable */

/** Store */
const auth = authState();

/** function */
MetaMaskLogin()
  .then(balanceOf)
  .then((r) => {
    console.log(r);
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
