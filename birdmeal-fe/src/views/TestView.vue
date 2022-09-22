<template> 
  <v-app>
    <h1>이건희의 테스트</h1>
    <v-btn-group>
      <v-btn color="primary" @click="balanceOf">balanceOf</v-btn>
      <v-btn color="success" @click="approve">approve</v-btn>
      <v-btn color="secondary" @click="createTrade">createTrade</v-btn>
    </v-btn-group>

    <h1>파이니아 테스트</h1>
    {{user}}
  </v-app>
</template>

<script>
import { balanceOf, approve, createTrade } from "@/web3util/events";
import { mapState } from 'pinia';
import {authState} from '@/stores/auth'
export default {
  name: "TestView",
  methods: {
    balanceOf,
    approve,
    createTrade,
  },
  computed: {
    ...mapState(authState, ['user',])
  }
};
</script>

<style lang="scss" scoped></style>
