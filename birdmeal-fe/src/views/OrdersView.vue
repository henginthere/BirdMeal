<template>
  <v-app>
    <v-table fixed-header height="100%">
      <thead>
        <tr>
          <th class="text-center">이미지</th>
          <th class="text-center">주문번호</th>
          <th class="text-center">가격</th>
          <th class="text-center">수량</th>
          <th class="text-center">카테고리</th>
          <th class="text-center">상품명</th>
          <th class="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in pageData" :key="item.id">
          <td align="center">
            <v-card width="120">
              <v-img
                class="bg-white"
                width="120"
                :aspect-ratio="1"
                :src="item.productThumbnailImg"
                cover
              ></v-img>
            </v-card>
          </td>
          <td class="text-center">{{ item.orderSeq }}</td>
          <td class="text-center">{{ item.orderPrice }}</td>
          <td class="text-center">{{ item.orderQuantity }}</td>
          <td class="text-center">{{ item.categoryName }}</td>
          <td class="text-center">{{ item.productName }}</td>
          <td align="center"><v-btn color="birdmealPrimary">Detail</v-btn></td>
        </tr>
      </tbody>
    </v-table>
    <v-pagination v-model="currentPage" :length="pageLength"></v-pagination>
  </v-app>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import http from '@/api/http.js';
import { authState } from '@/stores/auth.js';

const auth = authState();
let orderList = [];

const currentPage = ref(1);
const itemsPerPage = 10;
const pageLength = ref(0);

const pageData = ref([]);

async function updatePageData(pageNum, oldNum) {
  if (orderList.length == 0) return;
  pageData.value = orderList.slice(
    (pageNum - 1) * itemsPerPage,
    Math.min(pageNum * itemsPerPage, orderList.length)
  );
}

onMounted(() => {
  http.get(`/order/${auth.user.sellerSeq}`).then(function (res) {
    orderList = res.data.data;
    pageLength.value = Math.ceil(orderList.length / itemsPerPage);
    updatePageData(1);
  });
});
watch(currentPage, updatePageData);
</script>

<style lang="scss" scoped></style>
