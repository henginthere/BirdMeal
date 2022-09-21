<template>
  <v-app>
    <h1>상품목록</h1>
    <v-btn v-on:click="goRegist"> 상품등록하러가기</v-btn>
    <product-list-item
          v-for="(product, idx) in products"
          :key="idx"
          :product="product"
          ></product-list-item>
  </v-app>
</template>

<script>
  import http from '@/api/http.js'
  import ProductListItem from "@/components/ProductListItem.vue"
  import { mapState } from 'pinia';
  import {authState} from '@/stores/auth'
  export default {
    data() {
      return {
        products:null,
      }
    },
    components: {
      ProductListItem
    },
    methods: {
      callProducts() {
        http.get(`/product/${this.user.sellerSeq}`).then(res=> this.products = res.data.data)
      },
      goRegist(){
          this.$router.push('/product/regist');
      }
    },
    mounted() {
      this.callProducts()
    },
    computed: {
    ...mapState(authState, ['user',])
  },
    
  }
</script>

<style lang="scss" scoped>

</style>