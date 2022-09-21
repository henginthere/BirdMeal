<template>
  <v-app>
    상품 디테일 뷰
    {{product}}
  </v-app>
</template>

<script>
import axios from 'axios'


  export default {

    data() {
      return {
        product: null,
      }
    },

    methods: {
      getProductDetail(productSeq){
        axios({
        url: `https://j7d101.p.ssafy.io/api/product/${productSeq}`,
        method: 'get',
        headers: {"Content-type": "application/json"},
        data:{},
      }).then(res=> this.product = res.data.data)
      }
    },


    created(){
      const payload = this.$route.params.productSeq
      console.log(payload)
      this.getProductDetail(payload)
    }

  }
</script>

<style lang="scss" scoped>

</style>