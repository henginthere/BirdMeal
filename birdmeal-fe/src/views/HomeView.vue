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
              <v-btn variant="outlined" color="primary_orange" class="ml-2"
                >판매자 정보 등록하기</v-btn
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
              판매자 정보를 통해 상품을 판매할 수 있습니다.
            </v-card-text>
            <v-card-actions>
              <v-btn variant="outlined" color="primary_orange" class="ml-2"
                >상품 판매하기</v-btn
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

/** State */
const auth = authState();

/** Variable */
const seller = ref(false);

/** Hook */
onMounted(() => {
  http.get(`/info/${auth.user.sellerSeq}`).then((res) => {
    seller.value = res.data.success;
  });
});
</script>

<style lang="scss" scoped></style>
