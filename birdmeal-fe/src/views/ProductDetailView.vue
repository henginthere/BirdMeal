<template>
  <v-app>
    <!-- 상품 디테일 뷰
    {{product}} -->

    <v-container>
      <v-row>
        <v-col
          cols="12"
          sm="6"
          md="3"
        >
          <v-text-field
            :label="product !== null ? product.productName: 상품명"
            v-model="name"
          ></v-text-field>
          <v-btn v-on:click="updateProduct">상품명 수정하기</v-btn>
        </v-col>
        <v-divider />
        <v-col
          cols="12"
          sm="6"
          md="3"
        >
          <v-text-field
            :label="product !== null ? product.productPrice: 상품가격 "
            v-model="price"
          ></v-text-field>
          <v-btn>상품가격 수정하기</v-btn>
        </v-col>
        <v-divider />
        <hr/>
        <v-img :src="product !== null ? product.productThumbnailImg: null" max-height="150" max-width="250"/>
        <form>
          <input type="file" name="productThumbnailImg" id="productThumbnailImg" />
        </form>
        <v-img :src="product !== null ? product.productDescriptionImg: null" max-height="150" max-width="250"/>
        <form>
          <input type="file" name="productDescriptionImg" id="productDescriptionImg" />
        </form>
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
import axios from 'axios'
import { mapState } from 'pinia';
import {authState} from '@/stores/auth'

  export default {

    data() {
      return {
        product: null,
        name: null,
        price: null,
      }
    },
    computed: {
    ...mapState(authState, ['user',])
  },

    methods: {
      getProductDetail(productSeq){
        axios({
        url: `https://j7d101.p.ssafy.io/api/product/${productSeq}`,
        method: 'get',
        headers: {"Content-type": "application/json"},
        data:{},
      }).then(res=> this.product = res.data.data)
      },
      updateProduct(){
        let form = new FormData()
        let productThumbnailImg = document.getElementById("productThumbnailImg").files[0] !== undefined ? document.getElementById("productThumbnailImg").files[0] : null;
        let productDescriptionImg = document.getElementById("productDescriptionImg").files[0] !== undefined ? document.getElementById("productDescriptionImg").files[0] : null;
        form.append("productName", this.name !== null ? this.name : this.product.productName)
        form.append('productPrice', this.name !== null ? this.price : this.product.productPrice)
        form.append('productSeq', this.product.productSeq)
        form.append('sellerEmail', this.user.sellerEmail)
        form.append('productThumbnailImg',productThumbnailImg)
        form.append('productDescriptionImg',productDescriptionImg)


        console.log(this.name !== null ? this.name : this.product.productName)
        console.log(this.name !== null ? this.price : this.product.productPrice)
        console.log(this.product.productSeq)
        console.log(this.user.sellerEmail)
        console.log(productThumbnailImg)
        console.log(productDescriptionImg)



        axios.put("https://j7d101.p.ssafy.io/api/seller/product", form, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(() => this.$router.push('/products'))
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