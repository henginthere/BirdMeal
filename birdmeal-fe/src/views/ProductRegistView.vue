<template>
  <v-app>
    <h1 class="d-flex justify-center">상품등록페이지</h1>
    <v-container>
      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-text-field label="상품명" v-model="name"></v-text-field>
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-text-field label="가격" v-model="price"></v-text-field>
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-select
            :items="categorys"
            v-model="selectCategory"
            label="카테고리"
            dense
            outlined
          ></v-select>
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-col md="3" class="pe-0">
          <v-file-input
            label="썸네일 이미지 파일"
            id="productThumbnailImg"
            v-on:change="imgUpload1"
            prepend-icon="mdi-camera"
          ></v-file-input>
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-img
            :src="productThumbnailImgURL !== '' ? productThumbnailImgURL : null"
            max-height="200"
            min-width="250"
          />
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-col md="3" class="pe-0">
          <v-file-input
            label="상세설명 이미지 파일"
            id="productDescriptionImg"
            v-on:change="imgUpload2"
            prepend-icon="mdi-camera"
          ></v-file-input>
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-col md="3">
          <v-img
            :src="
              productDescriptionImgURL !== '' ? productDescriptionImgURL : null
            "
            max-height="1000"
            min-width="250"
          />
        </v-col>
      </v-row>
      <v-row class="d-flex justify-center">
        <v-btn color="primary_orange" v-model="sticky" v-on:click="registProduct"> 등록하기 </v-btn>
      </v-row>
      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
    </v-container>
  </v-app>
</template>

<script>
import { createTrade } from "@/web3util/events";
import axios from "axios";
import { mapState } from "pinia";
import { authState } from "@/stores/auth";
export default {
  data() {
    return {
      name: "",
      price: null,
      ca: "",
      selectCategory: "",
      productThumbnailImgURL: "",
      productDescriptionImgURL: "",
      category: { "육류": 1, "채소/과일": 2, "밀키트/간편식": 3, "냉동식품": 4, "과자류": 5, "음료": 6, "베이커리":7, "쌀/반찬":8, "양념/오일":9 },
      categorys: ["육류", "채소/과일", "밀키트/간편식", "냉동식품", "과자류", "음료","베이커리", "쌀/반찬", "양념/오일"],
    };
  },
  computed: {
    ...mapState(authState, ["user"]),
  },
  methods: {
    createTrade,
    // 썸네일 업로드용
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

    registProduct() {
      if (this.name === "") {
        alert("상품명을 입력해주십시오");
      } else if (this.price === null) {
        alert("가격을 입력해주십시오");
      } else if (this.selectCategory === "") {
        alert("카테고리를 선택해주십시오");
      } else if (this.productThumbnailImgURL === "") {
        alert("썸네일을 선택해주십시오");
      } else if (this.productDescriptionImgURL === "") {
        alert("상세설명 파일을 선택해주십시오");
      } else {
        createTrade(this.name, this.price)
          .then(
            (res) =>
              (this.ca = res.events.TradeCreated.returnValues.tradeAddress)
          )
          .then(() =>
            axios.post(
              "https://j7d101.p.ssafy.io/api/seller/product",
              {
                categorySeq: this.category[this.selectCategory],
                sellerSeq: this.user.sellerSeq,
                productName: this.name,
                productPrice: this.price,
                productCa: this.ca,
                productThumbnailImg: this.productThumbnailImgURL,
                productDescriptionImg: this.productDescriptionImgURL,
              },
              { headers: { "Content-type": "application/json" } }
            )
          )
          .then(() => this.$router.push("/products"));
      }
    },
    // test(){
    //     console.log(this.name)
    //     console.log(this.category[this.selectCategory])
    //     console.log(this.price)
    //     console.log(this.ca)
    //     console.log(this.user.sellerSeq)
    //     console.log(this.productThumbnailImgURL)
    //     console.log(this.productDescriptionImgURL)
    // }
  },
};
</script>

<style lang="scss" scoped></style>
