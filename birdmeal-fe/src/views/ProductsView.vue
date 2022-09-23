<template>
  <v-app>
    <v-btn v-on:click="goRegist"> 상품등록하러가기</v-btn>
    <v-table fixed-header height="100%">
      <thead>
        <tr>
          <th class="text-center">이미지</th>
          <th class="text-center">상품명</th>
          <th class="text-center">가격</th>
          <th class="text-center">카테고리</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(product, idx) in products" :key="idx" v-on:click="goDetail(product.productSeq)">
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
          <td class="text-center">{{ product.productPrice.toLocaleString() }}ELN</td>
          <td class="text-center">{{ category[product.categorySeq] }}</td>
        </tr>
      </tbody>
    </v-table>
  </v-app>
</template>

<script>
  import http from '@/api/http.js'
  import ProductListItem from "@/components/ProductListItem.vue"
  import { mapState } from 'pinia';
  import {authState} from '@/stores/auth'

  export default {
    data() {
      return {
        products:null,
        category:{ 1:"육류", 2:"채소/과일", 3:"밀키트/간편식",4: "냉동식품", 5:"과자류", 6:"음료", 7:"베이커리", 8:"쌀/반찬", 9:"양념/오일" }
      }
    },
    components: {
      ProductListItem
    },
    methods: {
      callProducts() {
        http.get(`/product/${this.user.sellerSeq}`).then(res=> this.products = res.data.data)
      },
      goRegist(){
          this.$router.push('/product/regist');
      },
      goDetail(productSeq) {
        this.$router.push({name:"product-detail", params:{productSeq:productSeq} })
      },
    },
    mounted() {
      this.callProducts()

    },
    computed: {
    ...mapState(authState, ['user',])
  },
    
  }
</script>

<style lang="scss" scoped>

</style>