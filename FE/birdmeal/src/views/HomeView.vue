<template>
  <v-app>
    <v-container>
      <v-row>
        <v-col v-if="!seller">
          <v-card width="600px">
            <v-card-title>
              <v-icon color="primary_orange">mdi-alert-box</v-icon>
              판매자 정보를 입력해주세요
            </v-card-title>
            <v-card-text>
              판매자 정보를 통해 상품을 판매할 수 있습니다.
            </v-card-text>
            <v-card-actions>
              <v-btn
                variant="outlined"
                color="primary_orange"
                class="ml-2"
                @click="router.push('/mypage')"
                >판매자 정보 입력</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col v-if="seller">
          <v-card width="600px">
            <v-card-title>
              <v-icon color="primary_orange">mdi-alert-box</v-icon>
              상품을 판매해 보세요
            </v-card-title>
            <v-card-text>
              판매자 정보를 통해 상품을 판매할 수 있습니다. <br />
              현재 {{ sellCnt }}개의 상품을 판매중입니다.
            </v-card-text>
            <v-card-actions>
              <v-btn
                variant="outlined"
                color="primary_orange"
                class="ml-2"
                @click="router.push('/product/regist')"
                >상품 판매하기</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col>
          <v-card width="600px">
            <v-card-title>
              <v-icon color="green">mdi-alert-box</v-icon>
              지갑이 없으신가요?
            </v-card-title>
            <v-card-text>
              메타마스크를 이용해 지갑을 만들어 보세요. <br />　
            </v-card-text>
            <v-card-actions>
              <v-btn
                variant="outlined"
                color="green"
                class="ml-2"
                @click="router.push('/guide')"
                >메타마스크 가이드</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="600px">
            <v-card-title>
              <v-icon color="blue-darken-3">mdi-alert-box</v-icon>
              고객이 배송을 기다리는 중입니다.
            </v-card-title>
            <v-card-text>
              {{ orderCnt }}개의 처리되지 않은 주문이 있습니다. <br />　
            </v-card-text>
            <v-card-actions>
              <v-btn
                variant="outlined"
                color="blue-darken-3"
                class="ml-2"
                @click="router.push('/orders')"
                >주문 관리</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import http from '@/api/http.js';
import { authState } from '@/stores/auth';
import { useRouter } from 'vue-router';

/** State, Router */
const auth = authState();
const router = useRouter();

/** Variable */
const seller = ref(false);
const sellCnt = ref(0);
const orderCnt = ref(0);

/** Hook */
onMounted(() => {
  http.get(`/info/${auth.user.sellerSeq}`).then((res) => {
    seller.value = res.data.success;
  });
  http
    .get(`/product/${auth.user.sellerSeq}`)
    .then((res) => (sellCnt.value = res.data.data.length));
  http.get(`/order/${auth.user.sellerSeq}`).then(function (res) {
    orderCnt.value = res.data.data.filter(
      (item) => !item.orderDeliveryNumber || !item.orderDeliveryCompany
    ).length;
  });
});
</script>

<style lang="scss" scoped></style>
