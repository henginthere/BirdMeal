<template>
  <v-app>
    <div>
      <v-row
        style="height: 150px"
        align="center"
        class="text-h2 ml-16 mt-8 font-weight-bold"
      >
        <v-col>판매자 정보</v-col>
      </v-row>
      <v-row style="height: 170px" align="center">
        <v-col cols="2" offset-md="1">판매자 사진</v-col>
        <v-col cols="7" offset-md="1">
          <v-img width="130px" :src="sellerInfo.sellerImg"></v-img></v-col>
      </v-row>
      <v-row style="height: 80px" align="center">
        <v-col cols="2" offset-md="1">판매자 닉네임</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerNickname }}</v-col>
      </v-row>
      <v-row style="height: 80px" align="center">
        <v-col cols="2" offset-md="1">판매자 이메일</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerEmail }}</v-col>
      </v-row>
      <v-row style="height: 80px" align="center">
        <v-col cols="2" offset-md="1">판매자 전화번호</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerTel }}</v-col>
      </v-row>
      <v-row style="height: 80px" align="center">
        <v-col cols="2" offset-md="1">판매자 주소</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerAddress }}</v-col>
      </v-row>
      <v-row style="height: 80px" align="center">
        <v-col cols="2" offset-md="1">판매자 정보</v-col>
        <v-col cols="7" offset-md="1">{{ sellerInfo.sellerInfo }}</v-col>
      </v-row>
      <v-row style="height: 150px" align="center">
        <v-col cols="2" offset-md="1">
          <seller-update :item="sellerInfo"></seller-update>
          </v-col>
      </v-row>
      
    </div>
  </v-app>
</template>

<script setup>
import { ref } from 'vue';
import http from "@/api/http.js";
import { authState } from "@/stores/auth";

import SellerUpdate from "../components/SellerUpdate.vue";


const auth = authState();

const sellerInfo = ref({});

function callSellerInfo() {
      http
        .get(`/${auth.user.sellerSeq}`)
        .then((res) => sellerInfo.value = res.data.data)
};

function goUpdate() {
  this.$router.push('/product/update');
};

callSellerInfo()

</script>

<style lang="scss" scoped></style>
