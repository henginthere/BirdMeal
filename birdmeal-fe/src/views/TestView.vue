<template> 
  <v-app>
    <h1>이건희의 테스트</h1>
    <v-btn-group>
      <v-btn color="primary" @click="balanceOf">balanceOf</v-btn>
      <v-btn color="success" @click="approve">approve</v-btn>
      <v-btn color="secondary" @click="createTrade">createTrade</v-btn>
    </v-btn-group>
  </v-app>
</template>

<script>
import { balanceOf, approve, createTrade } from "@/web3util/events";
export default {
  name: "TestView",
  methods: {
    balanceOf,
    approve,
    createTrade,
  },
};
</script>

<style lang="scss" scoped></style>
