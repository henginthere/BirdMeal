import drf from '@/api/drf.js';
import { defineStore } from 'pinia';
import router from '@/router/index.js';
import { decodeCredential } from 'vue3-google-login';

export const authState = defineStore('authState', {
  state: () => {
    return {
      user: JSON.parse(localStorage.getItem('user')),
    };
  },
  actions: {
    async signup(credential) {
      let userData = decodeCredential(credential);
      let email = userData.email;
      let nickname = email.split('@')[0];

      const response = await drf.post('/register', {
        sellerEmail: email,
        sellerNickname: nickname,
      });
      
      console.log('signup', response.data.success);
    },
    async login(credential) {

      const response = await drf.post('/login', {
        googleAccessToken: credential,
      });

      const user = {
        sellerSeq: response.data.data.sellerSeq,
        accessToken: response.data.data.tokenDto.accessToken,
        refreshToken: response.data.data.tokenDto.refreshToken,
      };

      this.user = user;
      localStorage.setItem('user', JSON.stringify(user));
      console.log('login', response.data.success);
      // router.push('/');
    },
    logout() {
      this.user = null;
      localStorage.removeItem('user');
      router.push('/login');
    },
  },
});
