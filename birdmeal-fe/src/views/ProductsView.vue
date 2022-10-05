<template>
  <v-app>
    <v-container class="text-h4">
      <v-row>
        <v-col class="ml-4 mt-4 mb-0">내 상품</v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col lg="2" sm="4" class="mb-0 pb-0">
          <v-select
            v-model="select"
            :items="category"
            variant="underlined"
            label="카테고리"
            density="comfortable"
          ></v-select>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-table fixed-header height="100%">
        <thead>
          <tr>
            <th class="text-center">이미지</th>
            <th class="text-center">상품명</th>
            <th class="text-center">가격</th>
            <th class="text-center">카테고리</th>
            <th class="text-center">등록일</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="(product, idx) in products" :key="idx">
            <tr
              v-if="select == '전체' || category[product.categorySeq] == select"
              v-on:click="goDetail(product.productSeq)"
            >
              <td align="center">
                <v-card width="120">
                  <v-img
                    class="bg-white"
                    width="220"
                    :aspect-ratio="1"
                    :src="product.productThumbnailImg"
                    cover
                  ></v-img>
                </v-card>
              </td>
              <td class="text-center">{{ product.productName }}</td>
              <td class="text-center">
                {{ product.productPrice.toLocaleString() }}ELN
              </td>
              <td class="text-center">{{ category[product.categorySeq] }}</td>
              <td class="text-center">
                {{ dateFormat(product.productCreateDate) }}
              </td>
            </tr>
          </template>
        </tbody>
      </v-table>
    </v-container>
  </v-app>
</template>

<script>
import http from '@/api/http.js';
import { mapState } from 'pinia';
import { authState } from '@/stores/auth';

export default {
  data() {
    return {
      products: null,
      category: [
        '전체',
        '고기',
        '채소/과일',
        '밀키트/간편식',
        '냉동식품',
        '과자류',
        '음료',
        '베이커리',
        '쌀/반찬',
        '양념/오일',
      ],
      select: '전체',
    };
  },
  methods: {
    /* 
        페이지 렌더링 전에 api호출을 통해 상품의 목록을 모두 가져온다
        이후 받아온 상품의 목록은 data의 product에 저장
      */
    callProducts() {
      http
        .get(`/product/${this.user.sellerSeq}`)
        .then((res) => (this.products = res.data.data));
    },
    goRegist() {
      this.$router.push('/product/regist');
    },
    /*
        라우터 이동시 디테일 페이지는 상품의 seq로 페이지의 url에 라우팅을 해두었으므로
        params로 productSeq를 넘겨줘야함
      */
    goDetail(productSeq) {
      this.$router.push({
        name: 'product-detail',
        params: { productSeq: productSeq },
      });
    },
    dateFormat(date) {
      return (
        date.substring(0, 4) +
        '.' +
        date.substring(4, 6) +
        '.' +
        date.substring(6, 8)
      );
    },
  },
  mounted() {
    this.callProducts();
  },
  /*
      전역 state에서 user정보를 가져오기 위해 computed에 mapState를 사용해서 등록
    */
  computed: {
    ...mapState(authState, ['user']),
  },
};
</script>

<style lang="scss" scoped>
.v-table th {
  font-size: 1em !important;
  font-weight: 400;
}
</style>
