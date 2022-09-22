<template>
  <v-app>
    <v-btn @click="login">Login Using Google</v-btn>
  </v-app>
</template>

<script setup>
import { googleOneTap } from 'vue3-google-login';
import { authState } from '@/stores/auth.js';

const auth = authState();

function login() {
  googleOneTap()
    .then((res) => {
      auth.signup(res.credential);
      auth.login(res.credential);
    })
    .catch((error) => {
      console.log('Handle the error', error);
    });
}
</script>

<style lang="scss" scoped></style>
