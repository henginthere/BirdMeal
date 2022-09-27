<template>
  <v-app>

    <v-overlay :model-value="overlay" class="align-center justify-center" height="500" width="500" persistent>
      <loading />
    </v-overlay>

    <v-container>
      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-text-field
            :label="product !== null ? product.productName : 상품명"
            v-model="name"
          ></v-text-field>
        </v-col>
        <v-col md="2">
          <v-btn color="primary_orange" v-on:click="setProduct">수정하기</v-btn>
        </v-col>
      </v-row>

      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-text-field
            :label="product !== null ? product.productPrice : 상품가격"
            v-model="price"
          ></v-text-field>
        </v-col>
        <v-col md="2">
        </v-col>
      </v-row>




      <v-row class="d-flex justify-center">

        <v-col md="3" class="ps-16 pe-0">
        <v-img
          :src="product !== null ? productThumbnailImgURL : null"
          max-height="200"
          max-width="250"
        />
        </v-col>
        <v-col md="3" class="pt-10 ps-0 ">
              <input
                type="file"
                name="productThumbnailImg"
                id="productThumbnailImg"
                v-on:change="imgUpload1"
              />
        </v-col>
      </v-row>

      <v-row class="mt-10 d-flex justify-center">
        <v-col md="3" class=" ps-16 pe-0">
          <v-img
            :src="product !== null ? productDescriptionImgURL : null"
            max-height="1000"
            max-width="250"
          />
        </v-col>
        <v-col md="3" class="pt-16 ps-0">
          <input
            type="file"
            name="productDescriptionImg"
            id="productDescriptionImg"
            v-on:change="imgUpload2"
          />
        </v-col>
      </v-row>
      <v-row class=" d-flex justify-center">
        <v-btn class=" mt-16 ms-5" v-on:click="deleteProduct" color="primary_orange" > 상품 삭제하기 </v-btn>

      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import axios from "axios";
import { mapState } from "pinia";
import { authState } from "@/stores/auth";
import http from "../api/http";
import { updateProduct } from "@/web3util/events.js";
import loading from '@/components/Loading.vue'
export default {
  data() {
    return {
      product: null,
      preName: null,
      prePrice: null,
      name: null,
      price: null,
      productThumbnailImgURL: "",
      productDescriptionImgURL: "",
      overlay:false,
    };
  },
  computed: {
    ...mapState(authState, ["user"]),
  },

  components:{
    loading
  },

  

  methods: {

    /*
      버튼 하나만으로 수정하기 가능하게 하자
      1. 상품명이나 상품가격이 변경되었다면 web3의 setProduct를 호출 후 axios요청을 보내야함
      2. 그게아니고 이미지만 바뀌었으면 axios요청만 보내도됨 
    */
    

    setProduct() {
      if (this.preName === this.name || this.prePrice === this.price) {
        this.overlay = !this.overlay
        axios.put(
            "https://j7d101.p.ssafy.io/api/seller/product/update",
            {
              productName: this.name,
              productSeq: this.product.productSeq,
              productPrice: this.price,
              productThumbnailImg:this.productThumbnailImgURL,
              productDescriptionImg: this.productDescriptionImgURL
            },
            { headers: { "Content-type": "application/json" } }
          )
          .then(() => this.overlay = !this.overlay)
          .then(() => this.$router.push("/products"))
      }
      else{
        this.overlay = !this.overlay
        updateProduct(this.name, this.price, this.product.productCa)
        .then(()=> axios.put(
            "https://j7d101.p.ssafy.io/api/seller/product/update",
            {
              productName: this.name,
              productSeq: this.product.productSeq,
              productPrice: this.price,
              productThumbnailImg:this.productThumbnailImgURL,
              productDescriptionImg: this.productDescriptionImgURL
            },
            { headers: { "Content-type": "application/json" } }
          ))
          .then(() => this.overlay = !this.overlay)
          .then(() => this.$router.push("/products"))
      }
    },







    getProductDetail(productSeq) {
      axios({
        url: `https://j7d101.p.ssafy.io/api/product/${productSeq}`,
        method: "get",
        headers: { "Content-type": "application/json" },
        data: {},
      })
        .then((res) => (this.product = res.data.data))
        .then(
          () => (this.productThumbnailImgURL = this.product.productThumbnailImg)
        )
        .then(
          () =>
            (this.productDescriptionImgURL = this.product.productDescriptionImg)
        )
        .then(() => (this.name = this.product.productName))
        .then(() => this.preName = this.product.productName)
        .then(() => (this.preprice = this.product.productPrice))
        .then(() => (this.price = this.product.productPrice))
    },
    imgUpload1() {
      let form = new FormData();
      const productThumbnailImg = document.getElementById("productThumbnailImg")
        .files[0];
      form.append("file", productThumbnailImg);
      axios
        .post("https://j7d101.p.ssafy.io/api/file", form, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => (this.productThumbnailImgURL = res.data.data))
        .then(() => alert("파일이 업로드 되었습니다."));
    },
    imgUpload2() {
      let form = new FormData();
      const productDescriptionImg = document.getElementById(
        "productDescriptionImg"
      ).files[0];
      form.append("file", productDescriptionImg);
      axios
        .post("https://j7d101.p.ssafy.io/api/file", form, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => (this.productDescriptionImgURL = res.data.data))
        .then(() => alert("파일이 업로드 되었습니다."));
    },




    deleteProduct() {
      http
        .put(`/product/${this.product.productSeq}`)
        .then(() => this.$router.push("/products"));
    },
  },

  /*
    payload에 지금 페이지url에 상품seq를 저장
    이후 렌더링 시 해당 상품의 정보를 받아오기 위해
    api호출
  */
  created() {
    const payload = this.$route.params.productSeq;
    this.getProductDetail(payload);
  },
};
</script>

<style lang="scss" scoped></style>
