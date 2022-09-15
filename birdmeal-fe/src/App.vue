<template>
  <v-app>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

import {MetaMaskLogin} from "@/web3util/events"

export default {
  name: 'App',

  data: () => ({
    //
  }),
  created() {
      MetaMaskLogin()
    }
}
</script>
