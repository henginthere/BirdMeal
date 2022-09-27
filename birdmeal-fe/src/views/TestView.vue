<template> 
  <v-app>
    <h1>이건희의 테스트</h1>
    <v-btn-group>
      <v-btn color="primary" @click="balanceOf">balanceOf</v-btn>
      <v-btn color="success" @click="approve">approve</v-btn>
      <v-btn color="secondary" @click="createTrade">createTrade</v-btn>
    </v-btn-group>
    {{addd}}
    <br/>
    {{userBalance}}
    <h1>파이니아 테스트</h1>
    {{user}}
    <br/>
    <loading />


  </v-app>
</template>

<script>
import { balanceOf, approve, createTrade, } from "@/web3util/events";
import { mapState } from 'pinia';
import {authState} from '@/stores/auth'
import loading from '@/components/Loading.vue'
export default {
  name: "TestView",
  data: () => {
    return {
      addd:0
    }
  },
  components:{
    loading
  },
  methods: {
    balanceOf,
    approve,
    createTrade,
  },
  created(){
    balanceOf().then(r => this.addd = r)
  },
  computed: {
    ...mapState(authState, ['user','userBalance'])

  }
};
</script>

<style lang="scss" scoped></style>
