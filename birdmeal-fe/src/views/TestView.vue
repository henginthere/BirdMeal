<template>
  <div>
    <h1>이건희의 테스트</h1>
    <button @click="balanceOf">balanceOf</button>
    <button @click="approve">approve</button>
    <button @click="createTrade">createTrade</button>

  </div>
</template>

<script>
  import { balanceOf, approve, createTrade} from "@/web3util/events"
  export default {
    name: 'TestView',
    methods:{
      balanceOf,
      approve,
      createTrade
    },

  }
</script>

<style lang="scss" scoped>

</style>