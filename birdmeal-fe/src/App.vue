<template>
  <v-app>
    <nav-bar></nav-bar>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

import {MetaMaskLogin} from "@/web3util/events"
import NavBar from "@/components/NavBar.vue";

export default {
  name: 'App',

  data: () => ({
    //
  }),
  components:{
    NavBar
  },
  created() {
    MetaMaskLogin()
  }
}
</script>
