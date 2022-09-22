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
        <v-divider />
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
        <v-divider />
        <v-select
          :items="categorys"
          v-model = "selectCategory"
          item-text="state"
          item-value="abbr"
          label="Select"
          persistent-hint
          return-object
          single-line
        ></v-select>
        
        <hr/>
        <form>
          <input type="file" name="productThumbnailImg" id="productThumbnailImg" />
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
      </v-row>
    </v-container>
  </v-app>
</template>
 
<script>
  import { createTrade } from "@/web3util/events";
  import axios from "axios";
  import { mapState } from 'pinia';
  import {authState} from '@/stores/auth'
  export default {
    data() {
      return {
        name: '',
        price: null,
        ca:'',
        selectCategory:"",
        category:{"육류":1, '채소':2, "과일":3, "과자":4, "빵":5, "음료":6},
        categorys:["육류", '채소', "과일", "과자", "빵", "음료"]

      }
    },
    computed: {
    ...mapState(authState, ['user',])
  },
    methods: {
      registProduct(){
        let form = new FormData()
        var productThumbnailImg = document.getElementById("productThumbnailImg");
        var productDescriptionImg = document.getElementById("productDescriptionImg");
        form.append("productName", this.name)
        form.append('productPrice', this.price)
        form.append('sellerSeq', this.user.sellerSeq)
        form.append('categorySeq',this.category[this.selectCategory])
        form.append('productThumbnailImg', productThumbnailImg.files[0])
        form.append('productDescriptionImg', productDescriptionImg.files[0])
        console.log(productDescriptionImg)
        createTrade(this.name, this.price).then(res=>this.ca = res.events.TradeCreated.returnValues.tradeAddress)
        .then(() => form.append("productCa", this.ca))
        .then(() => axios.post("https://j7d101.p.ssafy.io/api/seller/product", form,{
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })).then(()=>this.$router.push('/products'))
      },
      createTrade,
    }
  }
</script>

<style lang="scss" scoped>
 
</style>