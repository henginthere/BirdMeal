<template>
  <v-app>
    상품 디테일 뷰
    {{product}}
  </v-app>
</template>

<script>
  export default {
    props: {
      product: Object,
    },
  }
</script>

<style lang="scss" scoped>

</style>