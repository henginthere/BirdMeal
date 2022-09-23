<template>
  <v-dialog v-model="dialog" persistent>
    <template v-slot:activator="{ props }">
      <v-btn color="secondary_orange" v-bind="props" @click="getOrderDetail">
        상세보기
      </v-btn>
    </template>

    <v-card width="75vw">
      <v-toolbar color="back_beige">
        <v-toolbar-title>주문 상세보기</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
          <v-btn @click="dialog = false"> 닫기 </v-btn>
        </v-toolbar-items>
      </v-toolbar>

      <v-container>
        <!-- 상품 정보 -->
        <v-row>
          <v-col>상품정보</v-col>
        </v-row>

        <v-row>
          <v-col cols="1" />
          <v-col cols="2"
            ><v-img width="100px" :src="order.productThumbnailImg"></v-img
          ></v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">상품명</v-col>
          <v-col>{{ order.productName }}</v-col>
        </v-row>
        <v-row>
          <v-col><v-divider thickness="3"></v-divider></v-col>
        </v-row>

        <!-- 주문 정보 -->
        <v-row>
          <v-col>주문정보</v-col>
        </v-row>

        <v-row>
          <v-col cols="1" />
          <v-col cols="2">주문번호</v-col>
          <v-col>{{ order.orderSeq }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">주문날짜</v-col>
          <v-col>{{ order.orderDate }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">수량</v-col>
          <v-col>{{ order.orderQuantity }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">결제금액</v-col>
          <v-col>{{ order.orderPrice.toLocaleString() }} ELN</v-col>
        </v-row>
        <v-row>
          <v-col><v-divider thickness="3"></v-divider></v-col>
        </v-row>

        <!-- 주문자 정보 -->
        <v-row>
          <v-col>주문자 정보</v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">이름</v-col>
          <v-col>{{ order.userSeq }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">전화번호</v-col>
          <v-col>{{ order.userAdd }}</v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">주소</v-col>
          <v-col>{{ order.userAdd }}</v-col>
        </v-row>
        <v-row>
          <v-col><v-divider thickness="3"></v-divider></v-col>
        </v-row>

        <!-- 배송 정보 -->
        <v-row>
          <v-col>배송정보 </v-col>
        </v-row>

        <v-row>
          <v-col cols="1" />
          <v-col cols="2">택배사</v-col>
          <v-col
            ><v-text-field v-model="company" solo color="green"></v-text-field
          ></v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">운송장번호</v-col>
          <v-col
            ><v-text-field v-model="number" solo color="green"></v-text-field
          ></v-col>
        </v-row>
        <v-row>
          <v-col cols="1" />
          <v-col cols="2">상품인수</v-col>
          <v-col v-if="order.orderToState">고객이 상품을 인수했습니다.</v-col>
          <v-col v-else class="text-primary"
            >고객이 상품을 기다리는 중입니다.</v-col
          >
        </v-row>

        <v-row v-if="!company || !number">
          <v-alert
            class="d-flex justify-center"
            prominent
            type="warning"
            variant="text"
            color="primary_orange"
          >
            배송정보를 입력해주세요.
          </v-alert>
        </v-row>

        <v-row>
          <v-col class="d-flex justify-center">
            <v-btn class="mr-3" color="green" @click="save">저장</v-btn>
            <v-btn @click="dialog = false">취소</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref } from 'vue';
import { defineProps } from 'vue';
import http from '@/api/http.js';

/** Store, Router, Props */
const props = defineProps(['item']);

/** Variable */
const dialog = ref(false);
const company = ref('');
const number = ref('');
const order = ref({});

/** LifeCycle Hook */

/** Function */
function getOrderDetail() {
  order.value = props.item;
  company.value = order.value.orderDeliveryCompany;
  number.value = order.value.orderDeliveryNumber;
}

function save() {
  if (!company.value || !number.value) {
    return;
  }

  let delivery = {
    orderDetailSeq: order.value.orderDetailSeq,
    orderDeliveryNumber: number.value,
    orderDeliveryCompany: company.value,
  };

  http
    .put('/order', delivery)
    .then((res) => {
      console.log(res);
      if (!res.data.success) return;
      order.value.orderDeliveryCompany = company.value;
      order.value.orderDeliveryNumber = number.value;
      dialog.value = false;
    })
    .catch((err) => {
      console.log('에러 발생', err);
    });
}
</script>
