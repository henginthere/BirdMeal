
<template>
  <v-app>
    <div>{{orderList}}</div>
  </v-app>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import http from '@/api/http.js'
import { authState } from '@/stores/auth.js'
const auth = authState();
const orderList = ref({})

function getList() {
  http.get(`/order/${auth.user.sellerSeq}`).then(function (res) {
    orderList.value = res.data.data
    console.log(orderList.value[0])
  })
}

onMounted(() => {
  console.log("mounted");
  getList();
})
</script>

<style lang="scss" scoped>

</style>