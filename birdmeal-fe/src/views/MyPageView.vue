<template>
  <v-app>
    <my-page-seller-info>
    </my-page-seller-info>
    <v-btn v-on:click="goUpdate"> 수정하기</v-btn>
  </v-app>
</template>

<script>
import MyPageSellerInfo from "../components/MyPageSellerInfo.vue"
  export default {
    methods: {
        goUpdate() {
            this.$router.push("/mypage/update");
        }
    },
    components: { MyPageSellerInfo }
}
</script>

<style lang="scss" scoped>

</style>