<template>
  <v-app>

    <v-container>
      <v-row>
        <v-col
          cols="12"
          sm="2"
          md="2"
        >
          <v-text-field
            :label="product !== null ? product.productName: 상품명"
            v-model="name"
          ></v-text-field>
          <v-btn v-on:click="setName">상품명 수정하기</v-btn>
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
          <v-btn v-on:click="setPrice">상품가격 수정하기</v-btn>
        </v-col>
        <v-divider />
        <hr/>
        <v-img :src="product !== null ? productThumbnailImgURL: null" max-height="150" max-width="250"/>
        <form>
          <input type="file" name="productThumbnailImg" id="productThumbnailImg" />
          <v-btn v-on:click="imgUpload1">썸네일파일 업로드</v-btn>
          <v-btn v-on:click="updateproductThumbnailImg">썸네일 수정확정</v-btn>
        </form>
        <v-divider />
        <hr/>
        <v-img :src="product !== null ? productDescriptionImgURL: null" max-height="150" max-width="250"/>
        <form>
          <input type="file" name="productDescriptionImg" id="productDescriptionImg" />
          <v-btn v-on:click="imgUpload2">상세내용파일 업로드</v-btn>
          <v-btn v-on:click="updateProductDescriptionImg">상세내용 수정확정</v-btn>
        </form>
        <!-- <v-btn
          v-on:click="createTrade(name, Number(price))"
        > -->
        <v-divider/>
        <v-btn
          v-on:click="deleteProduct"
        >
          상품 삭제하기
        </v-btn>
        <!-- {{this.product.productCa}} -->
      </v-row>
    </v-container>

  </v-app>
</template>

<script>
  import axios from 'axios'
  import { mapState } from 'pinia';
  import {authState} from '@/stores/auth'
  import http from '../api/http';
  import {updateName, updatePrice} from "@/web3util/events.js";
  export default {

    data() {
      return {
        product: null,
        name: null,
        price: null,
        productThumbnailImgURL:'',
        productDescriptionImgURL:'',
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
      .then(()=> this.productThumbnailImgURL = this.product.productThumbnailImg)
      .then(()=> this.productDescriptionImgURL = this.product.productDescriptionImg)
      .then(()=> this.name = this.product.productName)
      .then(()=> this.price = this.product.productPrice)
      },
      imgUpload1(){
        let form = new FormData()
        const productThumbnailImg = document.getElementById("productThumbnailImg").files[0];
        form.append("file", productThumbnailImg)
        axios.post("https://j7d101.p.ssafy.io/api/file", form, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => this.productThumbnailImgURL = res.data.data)
      },
      imgUpload2(){
        let form = new FormData()
        const productDescriptionImg = document.getElementById("productDescriptionImg").files[0];
        form.append("file", productDescriptionImg)
        axios.post("https://j7d101.p.ssafy.io/api/file", form, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => this.productDescriptionImgURL = res.data.data)
      },

      setName(){
        updateName(this.name, this.product.productCa).then(()=>
        axios.put('https://j7d101.p.ssafy.io/api/seller/product/update/1', {
            productName:this.name,
            productSeq: this.product.productSeq,
        },{headers:  {"Content-type": "application/json"}}
        )).then(() => this.$router.push('/products'))
      },

      setPrice(){
        updatePrice(this.price, this.product.productCa).then(()=>
        axios.put('https://j7d101.p.ssafy.io/api/seller/product/update/2', {
            productPrice: this.price,
            productSeq: this.product.productSeq,
        },{headers:  {"Content-type": "application/json"}}
        )).then(() => this.$router.push('/products'))
      },

      updateproductThumbnailImg(){
        axios.put('https://j7d101.p.ssafy.io/api/seller/product/update/3', {
            productSeq: this.product.productSeq,
            productThumbnailImg: this.productThumbnailImgURL,
        },{headers:  {"Content-type": "application/json"}}
        ).then(() => this.$router.push('/products'))
      },

      updateProductDescriptionImg(){
        axios.put('https://j7d101.p.ssafy.io/api/seller/product/update/4', {
            productSeq: this.product.productSeq,
            productDescriptionImg: this.productDescriptionImgURL,
        },{headers:  {"Content-type": "application/json"}}
        ).then(() => this.$router.push('/products'))
      },
      deleteProduct(){
        http.put(`/product/${this.product.productSeq}`).then(res => console.log(res))
      }

    },
    created(){
      const payload = this.$route.params.productSeq
      this.getProductDetail(payload)
    }

  }
</script>

<style lang="scss" scoped>

</style>