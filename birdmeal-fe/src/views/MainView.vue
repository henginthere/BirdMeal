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

      <v-toolbar-title>Birdmeal 스토어</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="mr-4">{{ auth.user.sellerNickname }}님 환영합니다.</div>
    </v-app-bar>

    <!-- nav bar -->
    <v-navigation-drawer
      v-model="drawer"
      floating
      permanent
      class="elevation-1"
    >
      <v-sheet color="grey-lighten-4">
        <v-container>
          <v-row>
            <v-col cols="3">
              <v-avatar size="40">
                <img src="../assets/birdmeal_logo.png" height="32" />
              </v-avatar>
            </v-col>
            <v-col class="d-flex flex-column">
              <div>판매수익</div>
              <div>{{ auth.userBalance }} ELN</div>
            </v-col>
          </v-row>
        </v-container>
      </v-sheet>

      <v-list>
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

    <!-- footer -->
  </v-app>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { authState } from '@/stores/auth';

/** Router, Store */
const router = useRouter();
const auth = authState();

/** Variable */
const drawer = ref(true);
const links = ref([
  ['홈', '/home'],
  ['내 상품', '/products'],
  ['상품 등록', '/product/regist'],
  ['주문 목록', '/orders'],
  ['판매자 정보', '/mypage'],
  ['꽥', '/test'],
]);

/** Function */
function movePages(path) {
  router.push(path);
}

function logout() {
  auth.logout();
}
</script>

<style lang="scss" scoped></style>
