<template>
  <v-app>
    <nav-bar></nav-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup>
import { MetaMaskLogin, balanceOf } from '@/web3util/events';
import {authState} from '@/stores/auth.js'
import NavBar from '@/components/NavBar.vue';


const auth = authState();

MetaMaskLogin().then(balanceOf).then(r=> {console.log(r); auth.setBalance(r)});



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
