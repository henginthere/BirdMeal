<template>
  <v-app>
    <v-sheet class="d-flex" color="back_beige" height="484px">
      <v-container class="mt-16">
        <v-row>
          <v-col class="d-flex justify-center">
            <img class="logo" src="../assets/birdmeal_logo.png" />
          </v-col>
        </v-row>
        <v-row>
          <v-col class="d-flex justify-center">
            <h1>BirdMeal</h1>
          </v-col>
        </v-row>
        <v-row>
          <v-col class="d-flex justify-center">
            <p>아기새[:Bird]에게 모이[:Meal]를 주다</p>
          </v-col>
        </v-row>
        <v-row class="mt-8">
          <v-col class="d-flex justify-center">
            <v-btn
              prepend-icon="mdi-google"
              size="large"
              color="secondary_orange"
              @click="login"
            >
              Login Using Google
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-sheet>
  </v-app>
</template>

<script setup>
import { watchEffect } from 'vue';
import { googleOneTap } from 'vue3-google-login';
import { authState } from '@/stores/auth.js';
import { useRouter } from 'vue-router';

/** Store, Router */
const auth = authState();
const router = useRouter();

/** Variable */

/** Hooking */
watchEffect(() => {
  if (auth.user) {
    router.push('/home');
  }
});

/** Function */
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

<style scoped>
.logo {
  height: 15em;
  padding: 1.5em;
  will-change: filter;
  filter: drop-shadow(0 0 2em #f4bb7daa);
}
.logo:hover {
  filter: none;
}
</style>
