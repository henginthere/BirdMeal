<template>
  <v-app>
    <div>
      <v-row
        style="height: 250px"
        align="center"
        class="text-h2 ml-16 font-weight-bold"
      >
        <v-col>판매자 정보</v-col>
      </v-row>
      <v-row style="height: 100px" align="center">
        <v-col cols="2" offset-md="1">판매자 사진</v-col>
        <v-col cols="7" offset-md="1">
          <v-img width="100px" :src="sellerInfo.productThumbnailImg"></v-img></v-col>
      </v-row>
      <v-row style="height: 100px" align="center">
        <v-col cols="2" offset-md="1">판매자 닉네임</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerNickname }}</v-col>
      </v-row>
      <v-row style="height: 100px" align="center">
        <v-col cols="2" offset-md="1">판매자 이메일</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerEmail }}</v-col>
      </v-row>
      <v-row style="height: 100px" align="center">
        <v-col cols="2" offset-md="1">판매자 전화번호</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerTel }}</v-col>
      </v-row>
      <v-row style="height: 100px" align="center">
        <v-col cols="2" offset-md="1">판매자 주소</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerAddress }}</v-col>
      </v-row>
      <v-row style="height: 100px" align="center">
        <v-col cols="2" offset-md="1">판매자 정보</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerInfo }}</v-col>
      </v-row>
      <v-row style="height: 150px" align="center">
        <v-col cols="2" offset-md="1"><v-btn
        outlined
        onclick="goUpdate()"
        style="height: 40px"
        color="secondary_orange"
        >수정하기</v-btn
      ></v-col>
      </v-row>
      
    </div>
  </v-app>
</template>

<script setup>
import { ref } from 'vue';
import http from "@/api/http.js";
import { authState } from "@/stores/auth";


const auth = authState();

const sellerInfo = ref({});

function callSellerInfo() {
      http
        .get(`/${auth.user.sellerSeq}`)
        .then((res) => sellerInfo.value = res.data.data)
};

callSellerInfo()

</script>

<style lang="scss" scoped></style>
