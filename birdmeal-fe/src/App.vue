<template>
  <v-app>
    <nav-bar></nav-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { MetaMaskLogin } from '@/web3util/events';
import NavBar from '@/components/NavBar.vue';

export default {
  name: 'App',

  data: () => ({
    //
  }),
  components: {
    NavBar,
  },
  created() {
    MetaMaskLogin();
  },
};
</script>

<style>
.v-application {
  font-family: 'GongGothic', sans-serif !important;
}
@font-face {
  font-family:'GongGothic';
  src: url('./assets/fonts/GongGothicMedium.ttf') format('truetype');
  font-weight: 400;
}

@font-face {
  font-family:'GongGothic';
  src: url('./assets/fonts/GongGothicLight.ttf') format('truetype');
  font-weight: 300;
}
</style>
