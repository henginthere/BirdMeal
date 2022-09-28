import http from '@/api/http.js';
import { defineStore } from 'pinia';
import router from '@/router/index.js';
import { decodeCredential } from 'vue3-google-login';

export const authState = defineStore('authState', {
  state: () => {
    return {
      user: JSON.parse(localStorage.getItem('user')),
      userBalance: 0,
    };
  },
  actions: {
    async signup(credential) {
      let userData = decodeCredential(credential);
      let email = userData.email;
      let nickname = email.split('@')[0];

      const response = await http.post('/register', {
        sellerEmail: email,
        sellerNickname: nickname,
      });
    },
    async login(credential) {
      let userData = decodeCredential(credential);
      let email = userData.email;
      const response = await http.post('/login', {
        googleAccessToken: credential,
      });

      const user = {
        sellerEmail: email,
        sellerSeq: response.data.data.sellerSeq,
        accessToken: response.data.data.tokenDto.accessToken,
        refreshToken: response.data.data.tokenDto.refreshToken,
      };

      this.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    logout() {
      this.user = null;
      localStorage.removeItem('user');
      router.push('/signin');
      alert('로그아웃 되었습니다.');
    },

    setBalance(balance) {
      this.userBalance = Math.round(balance / 10 ** 18);
    },
  },
});
