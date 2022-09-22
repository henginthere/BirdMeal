<template>
  <v-navigation-drawer v-model="drawer" floating permanent class="elevation-1">
    <v-sheet color="back_beige lighten-4" class="pa-4">
      <v-avatar class="mb-4" color="grey darken-1" size="64"></v-avatar>

      <div>john@vuetifyjs.com</div>
    </v-sheet>

    <v-divider></v-divider>

    <v-list color="back_beige">
      <v-list-item v-for="[text, path] in links" :key="links.id" link v-on:click="movePages(path)">
        <v-list-item-content>
          <v-list-item-title>{{ text }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <template v-slot:append>
      <div class="pa-2">
        <v-btn block>
          로그아웃
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "NavBar",
  data: () => ({
    drawer: null,
    links: [
      ["홈", "/"],
      ["로그인", "/signin"],
      ["회원가입", "/signup"],
      ["마이페이지", "/mypage"],
      ["상품목록", "/products"],
      ["주문목록", "/orders"],
      ["테스트", "/test"],
    ],
  }),
  methods: {
    movePages(path) {
      this.$router.push(path);
    },
  },
};
</script>

<style lang="scss" scoped>

</style>
