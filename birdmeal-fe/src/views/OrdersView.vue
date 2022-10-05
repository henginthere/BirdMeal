<template>
  <v-app>
    <v-container class="text-h4">
      <v-row>
        <v-col class="ml-4 mt-4 mb-2">주문 목록</v-col>
      </v-row>
    </v-container>
    <v-container class="mb-0 pb-0">
      <v-row>
        <v-col lg="2" sm="4" class="mb-0 pb-0">
          <v-select
            v-model="filter"
            :items="filter_list"
            variant="underlined"
            label="필터"
            density="comfortable"
          ></v-select>
        </v-col>
        <v-col>
          <v-switch
            v-model="order_order"
            hide-details
            color="red"
            inset
            :label="`${order_order ? '최신순' : '오래된순'}`"
          ></v-switch>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-table fixed-header height="100%">
        <thead>
          <tr>
            <th class="text-center">Info</th>
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
              <div v-if="item.orderIsCanceled">
                <v-icon color="primary"> mdi-alert-circle </v-icon>
                <p class="text-primary">주문취소</p>
              </div>
              <div v-else>
                <div
                  v-if="!item.orderDeliveryNumber || !item.orderDeliveryCompany"
                >
                  <v-icon color="red"> mdi-alert-circle </v-icon>
                  <p class="text-error">배송전</p>
                </div>
                <div v-else>
                  <div v-if="!item.orderToState">
                    <v-icon color="orange"> mdi-alert-circle </v-icon>
                    <p class="text-orange">배송중</p>
                  </div>
                  <div v-else>
                    <v-icon color="green"> mdi-alert-circle </v-icon>
                    <p class="text-green">완료</p>
                  </div>
                </div>
              </div>
            </td>
            <td class="text-center">{{ item.orderSeq }}</td>
            <td class="text-center">{{ item.productName }}</td>
            <td class="text-center">{{ item.orderQuantity }}</td>
            <td class="text-center">
              {{ item.productPrice.toLocaleString() }} ELN
            </td>
            <td class="text-center">{{ item.categoryName }}</td>
            <td align="center">
              <order-detail-item :item="item"></order-detail-item>
            </td>
          </tr>
        </tbody>
      </v-table>
      <v-pagination v-model="currentPage" :length="pageLength"></v-pagination>
    </v-container>
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
const filter_list = ref(['전체', '배송전', '배송중', '완료', '주문취소']);
const filter = ref('전체');

let orderList = [];

const currentPage = ref(1);
const itemsPerPage = 15;
const pageLength = ref(0);

const pageData = ref([]);

const order_order = ref(true);

/** LifeCycle Hook */
onMounted(async () => {
  await http.get(`/order/${auth.user.sellerSeq}`).then(function (res) {
    orderList = res.data.data;
    pageLength.value = Math.ceil(orderList.length / itemsPerPage);
    updatePageData(1);
  });
  filterOrder(order_order.value, null);
});

watch(currentPage, updatePageData);
watch(filter, filterState);
watch(order_order, filterOrder);

/** Function */
async function updatePageData(pageNum, oldNum) {
  pageLength.value = Math.ceil(orderList.length / itemsPerPage);
  pageData.value = orderList.slice(
    (pageNum - 1) * itemsPerPage,
    Math.min(pageNum * itemsPerPage, orderList.length)
  );
}

async function filterState(newVal, oldVal) {
  await http.get(`/order/${auth.user.sellerSeq}`).then(function (res) {
    orderList = res.data.data;
  });

  if (newVal == '배송전') {
    orderList = orderList.filter(
      (item) => !item.orderDeliveryNumber || !item.orderDeliveryCompany
    );
  }

  if (newVal == '배송중') {
    orderList = orderList.filter(
      (item) =>
        item.orderDeliveryNumber &&
        item.orderDeliveryCompany &&
        !item.orderToState
    );
  }

  if (newVal == '완료') {
    orderList = orderList.filter((item) => item.orderToState);
  }

  if (newVal == '주문취소') {
    orderList = orderList.filter((item) => item.orderIsCanceled);
  }

  filterOrder(order_order.value, null);
  updatePageData(1, 1);
}

async function filterOrder(newVal, oldVal) {
  if (newVal) {
    orderList = orderList.sort((a, b) => {
      return b.orderSeq - a.orderSeq;
    });
  } else {
    orderList = orderList.sort((a, b) => {
      return a.orderSeq - b.orderSeq;
    });
  }
  updatePageData(1, 1);
}
</script>

<style lang="scss" scoped>
.v-table th {
  font-size: 1em !important;
  font-weight: 400;
}
</style>
