<template>
  <div v-on:click="goDetail">
    <img :src="product.productThumbnailImg"/>
    <br/>
    상품 카테고리: {{category[product.categorySeq]}}
    <br/>
    상품명: {{product.productName}}
    <br/>
    가격 : {{product.productPrice}}ELN
    <v-divider></v-divider>


  </div>
</template>

<script>
  export default {
    props: {
      product: Object,
    },
    data() {
      return {
        category:{1:"육류", 2:'채소', 3:"과일", 4:"과자", 5:"빵", 6:"음료"}
      }
    },
    methods: {
      goDetail() {
        this.$router.push({name:"product-detail", params:{product:this.product} })
      }
    }

    
  }
</script>

<style lang="scss" scoped>

</style>