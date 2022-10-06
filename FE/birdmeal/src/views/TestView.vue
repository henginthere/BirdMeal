<template>
  <v-app>
    <loading />
  </v-app>
</template>

<script>
import { balanceOf, approve, createTrade } from '@/web3util/events';
import { mapState } from 'pinia';
import { authState } from '@/stores/auth';
import loading from '@/components/Loading.vue';
export default {
  name: 'TestView',
  data: () => {
    return {
      addd: 0,
    };
  },
  components: {
    loading,
  },
  methods: {
    balanceOf,
    approve,
    createTrade,
  },
  created() {
    balanceOf().then((r) => (this.addd = r));
  },
  computed: {
    ...mapState(authState, ['user', 'userBalance']),
  },
};
</script>

<style lang="scss" scoped></style>
