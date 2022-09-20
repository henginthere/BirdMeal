<template>
  <v-app>
    <v-btn @click="signup">Login Using Google</v-btn>
  </v-app>
</template>

<script>
import { googleOneTap, decodeCredential } from "vue3-google-login";
import drf from "@/api/drf.js";
export default {
  methods: {
    signup() {
      googleOneTap()
        .then((res) => {
          // get google oauth user data
          let credential = res.credential;
          let userData = decodeCredential(credential);
          let email = userData.email;
          let nickname = email.split("@")[0];

          // axios 요청
          drf
            .post("/register", {
              sellerEmail: email,
              sellerNickname: nickname,
            })
            .then(function (res) {
              // login
              drf
                .post("/login", { googleAccessToken: credential })
                .then((res) => {
                  if (res.data.success) {
                    // data state
                    let data = res.data.data;
                    let sellerSeq = data.sellerSeq;
                    let accessToken = data.tokenDto.accessToken;
                    let refreshToken = data.tokenDto.refreshToken;
                  }
                })
                .catch((error) => {
                  console.log("login err", error);
                });
            })
            .catch((error) => {
              console.log("signup error", error);
            });
        })
        .catch((error) => {
          console.log("Handle the error", error);
        });
    },
  },
};
</script>

<style lang="scss" scoped></style>
