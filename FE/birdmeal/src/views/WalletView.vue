<template> 
    <h1>gd</h1>
    <v-btn @click="createWallet">지갑 생성</v-btn>

</template>
  
<script setup>
import { ref } from 'vue';
import http from '../api/http';
import { authState } from "@/stores/auth";
import { web3 } from '../web3util/web3Config';

  const auth = authState();

  const wallet = ref({});

  const check = ref(false);

  function createWallet(){
    wallet.value = web3.eth.accounts.create();
    check.value = true;
  }

  function registerWallet() {
    let delivery ={
    sellerSeq : auth.user.sellerSeq,
    sellerWallet : wallet.value.accounts
    };

    http.put('/wallet', delivery).then((res) => {
      console.log(res);
      }).catch((err) => { 
        console.log('에러 발생', err);
    });
  }
  
  </script>