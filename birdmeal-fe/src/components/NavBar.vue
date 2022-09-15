<template>
  <v-navigation-drawer v-model="drawer" app>
    <v-sheet color="grey lighten-4" class="pa-4">
      <v-avatar class="mb-4" color="grey darken-1" size="64"></v-avatar>

      <div>john@vuetifyjs.com</div>
    </v-sheet>

    <v-divider></v-divider>

    <v-list>
      <v-list-item
        v-for="[text, path] in links"
        :key="path"
        link
        v-on:click="movePages(path)"
      >
        <v-list-item-content>
          <v-list-item-title>{{ text }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
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

<style lang="scss" scoped></style>
