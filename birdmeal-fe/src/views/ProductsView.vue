<template>
  <v-app>
    상품목록
  </v-app>
</template>

<script>
  import drf from "@/api/drf.js"
  export default {
    data() {
      return {
        products:null,
      }
    },
    methods() {
      callProducts() {
        drf.get(`/products/${this.user-seq}`)
      }
    }
    
  }
</script>

<style lang="scss" scoped>

</style>