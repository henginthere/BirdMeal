<template>
  <v-container>
    <Vue3Lottie :animationData="loadingJson" :width="600"/>
    <v-row class="text-h4 my-0 py-0">
      <v-col class="my-0 py-0">
        <v-row class="d-flex justify-center align-center text-white"> 잠시만 기다려주세요... </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { Vue3Lottie } from 'vue3-lottie';
import 'vue3-lottie/dist/style.css';
import loadingJson from '@/guideImg/loading.json';

export default {
  components: {
    Vue3Lottie,
  },
  data() {
    return {
      loadingJson,
    };
  },
};
</script>

<style lang="scss" scoped></style>
