<template>
  <v-app>


    <v-overlay :model-value="overlay" class="align-center justify-center" height="500" width="500" persistent>
      <!-- <loading /> -->
      
    </v-overlay>



    <v-container class="text-h4">
      <v-row>
        <v-col class="ml-4 mt-4">상세정보</v-col>

      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col>
          <v-card width="800px">
            <v-card-title>상품명</v-card-title>
            <v-card-text v-if="modify" class="mt-3">
              <v-text-field
                variant="underlined"
                placeholder="예) 삼겹살(10kg)"
                color="primary_orange"
                v-model="name"
              />
            </v-card-text>
            <v-card-title v-else> {{ name }} </v-card-title>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="800px">
            <v-card-title>상품가격</v-card-title>
            <v-card-text v-if="modify" class="mt-3">
              <v-text-field
                variant="underlined"
                placeholder="예) 10000"
                color="primary_orange"
                v-model="price"
                suffix="ELN"
              />
            </v-card-text>
            <v-card-title v-else>{{ price }} ELN</v-card-title>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <v-col class="pe-0">
          <v-card width="800px">
            <v-card-title>상품 이미지</v-card-title>
            <v-card class="mx-4 my-2">
              <v-card-title>대표 이미지</v-card-title>
              <v-card-text class="mt-2">
                <v-file-input
                  v-if="modify"
                  label="썸네일 이미지 파일"
                  id="productThumbnailImg"
                  v-on:change="imgUpload1"
                  prepend-icon="mdi-camera"
                  variant="outlined"
                />
                <v-img
                  :src="
                    productThumbnailImgURL !== ''
                      ? productThumbnailImgURL
                      : null
                  "
                  max-height="200"
                  min-width="250"
                />
              </v-card-text>
            </v-card>
            <v-card class="mx-4 my-2">
              <v-card-title>상세설명</v-card-title>
              <v-card-text class="mt-2">
                <v-file-input
                  v-if="modify"
                  label="상세설명 이미지 파일"
                  hint="상품 상세설명은 하나의 이미지로 업로드 해주세요"
                  id="productDescriptionImg"
                  v-on:change="imgUpload2"
                  prepend-icon="mdi-camera"
                  variant="outlined"
                ></v-file-input>
                <v-img
                  :src="
                    productDescriptionImgURL !== ''
                      ? productDescriptionImgURL
                      : null
                  "
                  max-height="1000"
                  min-width="250"
                />
              </v-card-text>
            </v-card>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-sheet width="800px" class="d-flex justify-center">
            <v-btn v-if="!modify" color="primary_orange" @click="toModify"
              >수정</v-btn
            >
            <v-btn v-else color="primary_orange" @click="setProduct">
              저장
            </v-btn>
            <v-btn class="ml-2" v-on:click="deleteProduct" color="blue-grey-lighten-5">
              삭제
            </v-btn>
          </v-sheet>
        </v-col>
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
      modify: false,
    };
  },
  computed: {
    ...mapState(authState, ['user']),
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
    toModify() {
      this.modify = true;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    },

    setProduct() {
      if (this.preName === this.name || this.prePrice === this.price) {

        this.overlay = !this.overlay
        axios.put(
            "https://j7d101.p.ssafy.io/api/seller/product/update",
            {
              productName: this.name,
              productSeq: this.product.productSeq,
              productPrice: this.price,
              productThumbnailImg: this.productThumbnailImgURL,
              productDescriptionImg: this.productDescriptionImgURL,
            },
            { headers: { 'Content-type': 'application/json' } }
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
        method: 'get',
        headers: { 'Content-type': 'application/json' },
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
        .then(() => (this.preName = this.product.productName))
        .then(() => (this.preprice = this.product.productPrice))
        .then(() => (this.price = this.product.productPrice));
    },
    imgUpload1() {
      let form = new FormData();
      const productThumbnailImg = document.getElementById('productThumbnailImg')
        .files[0];
      form.append('file', productThumbnailImg);
      axios
        .post('https://j7d101.p.ssafy.io/api/file', form, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((res) => (this.productThumbnailImgURL = res.data.data))
        .then(() => alert('파일이 업로드 되었습니다.'));
    },
    imgUpload2() {
      let form = new FormData();
      const productDescriptionImg = document.getElementById(
        'productDescriptionImg'
      ).files[0];
      form.append('file', productDescriptionImg);
      axios
        .post('https://j7d101.p.ssafy.io/api/file', form, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((res) => (this.productDescriptionImgURL = res.data.data))
        .then(() => alert('파일이 업로드 되었습니다.'));
    },

    deleteProduct() {
      http
        .put(`/product/${this.product.productSeq}`)
        .then(() => this.$router.push('/products'));
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
