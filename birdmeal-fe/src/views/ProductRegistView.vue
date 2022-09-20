<template>
  <v-app>
    <h1>상품등록페이지</h1>
    <v-container>
      <v-row>
        <v-col
          cols="12"
          sm="6"
          md="3"
        >
          <v-text-field
            label="상품명"
            v-model="name"
          ></v-text-field>
        </v-col>
        <v-col
          cols="12"
          sm="6"
          md="3"
        >
          <v-text-field
            label="가격"
            v-model="price"
          ></v-text-field>
        </v-col>
        <hr/>
        <!-- <v-btn
          v-on:click="createTrade(name, Number(price))"
        > -->
        <v-btn
          v-on:click="registProduct"
        >
          등록하기
        </v-btn>
        {{ca}}
      </v-row>
    </v-container>
  </v-app>
</template>
 
<script>
  import { createTrade } from "@/web3util/events";
  export default {
    data() {
      return {
        name: '',
        price: 0,
        ca:''
      }
    },
    methods: {
      registProduct(){
        createTrade(this.name, this.price).then(res=>this.ca = res.events.TradeCreated.returnValues.tradeAddress)
      },
      createTrade,
      // check() {
      //   console.log(this.name,this.price)
      // }
    }
  }
</script>

<style lang="scss" scoped>
 
</style>