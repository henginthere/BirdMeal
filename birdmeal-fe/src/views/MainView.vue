<template>
  <v-app>
    <!-- app bar -->
    <v-app-bar
      color="primary_orange"
      density="compact"
      class="elevation-1 d-flex align-center"
    >
      <v-app-bar-nav-icon
        variant="text"
        @click.stop="drawer = !drawer"
      ></v-app-bar-nav-icon>

      <v-toolbar-title>Birdmeal 판매</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>

    <!-- nav bar -->
    <v-navigation-drawer
      v-model="drawer"
      floating
      permanent
      class="elevation-1"
    >
      <v-sheet color="back_beige lighten-4" class="pa-4">
        <v-avatar class="mb-4" color="grey darken-1" size="64">
          <img src="../assets/birdmeal_logo.png" height="60" />
        </v-avatar>

        <div class="">{{ userEmail }}</div>
      </v-sheet>

      <v-divider></v-divider>

      <v-list color="back_beige">
        <v-list-item
          v-for="[text, path] in links"
          :key="links.id"
          link
          v-on:click="movePages(path)"
        >
          <v-list-item-title>{{ text }}</v-list-item-title>
        </v-list-item>
      </v-list>

      <template v-slot:append>
        <div class="pa-2">
          <v-btn block color="green" @click="logout"> 로그아웃 </v-btn>
        </div>
      </template>
    </v-navigation-drawer>

    <!-- main -->
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { authState } from '@/stores/auth';

/** Router, Store */
const router = useRouter();
const auth = authState();

/** Variable */
const drawer = ref(true);
const links = ref([
  ['홈', '/home'],
  ['마이페이지', '/mypage'],
  ['상품목록', '/products'],
  ['주문목록', '/orders'],
  ['테스트', '/test'],
  ['지갑', '/wallet'],
]);

const userEmail = computed(() => {
  return auth.user ? auth.user.sellerEmail : '로그인 해주세요.';
});

/** Function */
function movePages(path) {
  router.push(path);
}

function logout() {
  auth.logout();
}
</script>

<style lang="scss" scoped></style>
