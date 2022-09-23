<template>
  <main-view v-if="auth.user"></main-view>
  <signin-view v-else></signin-view>
</template>

<script setup>
import { ref } from 'vue';
import { MetaMaskLogin } from '@/web3util/events';
import MainView from '@/views/MainView.vue';
import SigninView from '@/views/SigninView.vue';
import { authState } from '@/stores/auth.js';

/** Variable */
const sellerInfo = ref(null);

/** Store */
const auth = authState();

/** function */
MetaMaskLogin();
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
