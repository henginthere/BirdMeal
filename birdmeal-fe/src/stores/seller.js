import { defineStore } from "pinia";

export const sellerState = defineStore("sellerState", {
  state: () => {
    return {
      sellerSeq: 0,
      accessToken: "",
      refreshToken: "",
    };
  },
  getters: {
    getSellerSeq: (state) => state.sellerSeq,
    getAccessToken: (state) => state.accessToken,
    getRefreshToken: (state) => state.refreshToken,
  },
  actions: {},
});
