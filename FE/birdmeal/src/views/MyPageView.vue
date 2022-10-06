<template>
  <v-app>
    <v-container class="text-h4">
      <v-row>
        <v-col class="ml-4 mt-4 mb-0">판매자 정보</v-col>
      </v-row>
    </v-container>
    <v-card class="mx-4" width="900px">
      <v-container>
        <v-row class="my-4">
          <v-col cols="2">프로필 사진</v-col>
          <v-col cols="7">
            <v-img width="130px" :src="sellerInfo.sellerImg"></v-img
          ></v-col>
        </v-row>
        <v-row class="my-4">
          <v-col cols="2">업체명</v-col>
          <v-col cols="7">{{ sellerInfo.sellerNickname }}</v-col>
        </v-row>
        <v-row class="my-4">
          <v-col cols="2">이메일</v-col>
          <v-col cols="7">{{ sellerInfo.sellerEmail }}</v-col>
        </v-row>
        <v-row class="my-4">
          <v-col cols="2">전화번호</v-col>
          <v-col cols="7">{{ sellerInfo.sellerTel }}</v-col>
        </v-row>
        <v-row class="my-4">
          <v-col cols="2">주소</v-col>
          <v-col cols="7">{{ sellerInfo.sellerAddress }}</v-col>
        </v-row>
        <v-row class="my-4">
          <v-col cols="2">소개</v-col>
          <v-col cols="7">{{ sellerInfo.sellerInfo }}</v-col>
        </v-row>
        <v-row class="my-4">
          <v-col class="d-flex justify-center">
            <seller-update :item="sellerInfo"></seller-update>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-app>
</template>

<script setup>
import { ref } from 'vue';
import http from '@/api/http.js';
import { authState } from '@/stores/auth';

import SellerUpdate from '../components/SellerUpdate.vue';

const auth = authState();

const sellerInfo = ref({});

function callSellerInfo() {
  http
    .get(`/${auth.user.sellerSeq}`)
    .then((res) => (sellerInfo.value = res.data.data));
}

callSellerInfo();
</script>

<style lang="scss" scoped></style>
