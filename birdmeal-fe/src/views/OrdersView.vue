<template>
  <v-app>
    <v-container class="text-h4">
      <v-row>
        <v-col class="ml-8 mt-4 mb-0">주문 목록</v-col>
      </v-row>
    </v-container>
    <v-card elevation="0" class="mx-3">
      <v-table fixed-header height="100%">
        <thead>
          <tr>
            <th class="text-center"></th>
            <th class="text-center">주문번호</th>
            <th class="text-center">상품정보</th>
            <th class="text-center">수량</th>
            <th class="text-center">결제금액</th>
            <th class="text-center">구분</th>
            <th class="text-center"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in pageData" :key="item.id">
            <td class="text-center">
              <div
                v-if="!item.orderDeliveryNumber || !item.orderDeliveryCompany"
              >
                <v-icon color="red"> mdi-alert-circle </v-icon>
                <p class="text-error">배송정보</p>
              </div>
            </td>
            <td class="text-center">{{ item.orderSeq }}</td>
            <td class="text-center">{{ item.productName }}</td>
            <td class="text-center">{{ item.orderQuantity }}</td>
            <td class="text-center">
              {{ item.orderPrice.toLocaleString() }} ELN
            </td>
            <td class="text-center">{{ item.categoryName }}</td>
            <td align="center">
              <order-detail-item :item="item"></order-detail-item>
            </td>
          </tr>
        </tbody>
      </v-table>
    </v-card>
    <v-pagination v-model="currentPage" :length="pageLength"></v-pagination>
  </v-app>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import http from '@/api/http.js';
import { authState } from '@/stores/auth.js';

/** Components */
import OrderDetailItem from '@/components/OrderDetailItem.vue';

/** Store / Router */
const auth = authState();

/** Variable */
let orderList = [];

const currentPage = ref(1);
const itemsPerPage = 15;
const pageLength = ref(0);

const pageData = ref([]);

/** LifeCycle Hook */
onMounted(() => {
  http.get(`/order/${auth.user.sellerSeq}`).then(function (res) {
    orderList = res.data.data;
    pageLength.value = Math.ceil(orderList.length / itemsPerPage);
    updatePageData(1);
  });
});
watch(currentPage, updatePageData);

/** Function */
async function updatePageData(pageNum, oldNum) {
  if (orderList.length == 0) return;
  pageData.value = orderList.slice(
    (pageNum - 1) * itemsPerPage,
    Math.min(pageNum * itemsPerPage, orderList.length)
  );
}
</script>

<style lang="scss" scoped></style>
