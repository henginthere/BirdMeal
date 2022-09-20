<template>
  <v-app>
    상품목록
  </v-app>
</template>

<script>
  import axios from "axios"
  import drf from "@/api/drf.js"
  export default {
    data() {
      return {
        products:null,
      }
    },
    mounted() {
      callProducts() {
        axios({
          url: drf.HOST+`/products/${seller-seq}`
        })
      }
    }
    
  }
</script>

<style lang="scss" scoped>

</style>