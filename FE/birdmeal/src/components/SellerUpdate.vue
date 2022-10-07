<template>
  <v-dialog v-model="dialog" persistent>
    <template v-slot:activator="{ props }">
      <v-btn
        variant="outlined"
        color="secondary_orange"
        v-bind="props"
        @click="getSellerInfo"
        >정보 변경</v-btn
      >
    </template>

    <v-card width="900px">
      <v-toolbar color="back_beige">
        <v-toolbar-title>판매자 정보 수정하기</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
          <v-btn @click="dialog = false"> 닫기 </v-btn>
        </v-toolbar-items>
      </v-toolbar>

      <v-container>
        <v-row style="height: 100px" align="center">
          <v-col cols="2" offset-md="1">프로필 사진</v-col>
          <v-col cols="4" offset-md="1">
            <v-file-input
              label="이미지를 넣어주세요"
              id="sellerUpdateImg"
              v-on:change="imgUpload"
              prepend-icon="mdi-camera"
              variant="outlined"
            >
            </v-file-input>
          </v-col>
          <v-col md="2">
            <v-img
              :src="sellerInfo.sellerImg !== '' ? sellerInfo.sellerImg : null"
              max-height="70"
              min-width="250"
            />
          </v-col>
        </v-row>
        <v-row style="height: 100px" align="center">
          <v-col cols="2" offset-md="1">이메일</v-col>
          <v-col cols="7" offset-md="1">{{ sellerInfo.sellerEmail }}</v-col>
        </v-row>
        <v-row style="height: 100px" align="center">
          <v-col cols="2" offset-md="1">이름</v-col>
          <v-col cols="7" offset-md="1"
            ><v-text-field
              v-model="sellerInfo.sellerNickname"
              variant="outlined"
              color="green"
            ></v-text-field
          ></v-col>
        </v-row>
        <v-row style="height: 100px" align="center">
          <v-col cols="2" offset-md="1">전화번호</v-col>
          <v-col cols="7" offset-md="1"
            ><v-text-field
              v-model="sellerInfo.sellerTel"
              variant="outlined"
              color="green"
            ></v-text-field
          ></v-col>
        </v-row>
        <v-row style="height: 100px" align="center">
          <v-col cols="2" offset-md="1">주소</v-col>
          <v-col cols="7" offset-md="1"
            ><v-text-field
              v-model="sellerInfo.sellerAddress"
              variant="outlined"
              color="green"
            ></v-text-field
          ></v-col>
        </v-row>
        <v-row style="height: 100px" align="center">
          <v-col cols="2" offset-md="1">소개</v-col>
          <v-col cols="7" offset-md="1"
            ><v-text-field
              v-model="sellerInfo.sellerInfo"
              variant="outlined"
              color="green"
            ></v-text-field
          ></v-col>
        </v-row>
        <v-row
          v-if="
            !sellerInfo.sellerNickname ||
            !sellerInfo.sellerTel ||
            !sellerInfo.sellerAddress ||
            !sellerInfo.sellerInfo
          "
        >
          <v-alert
            class="d-flex justify-center"
            prominent
            type="warning"
            variant="text"
            color="primary_orange"
          >
            정보를 입력해주세요.
          </v-alert>
        </v-row>

        <v-row>
          <v-col class="d-flex justify-center">
            <v-btn class="mr-3" color="green" variant="flat" @click="save"
              >수정</v-btn
            >
            <v-btn color="back_beige" variant="flat" @click="dialog = false"
              >취소</v-btn
            >
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref } from 'vue';
import http from '@/api/http.js';
import axios from 'axios';

/** Store, Router, Props */
const props = defineProps(['item']);

/** Variable */
const dialog = ref(false);
const sellerInfo = ref({});

/** LifeCycle Hook */

/** Function */
function getSellerInfo() {
  sellerInfo.value = props.item;
}

function save() {
  if (
    !sellerInfo.value.sellerNickname ||
    !sellerInfo.value.sellerEmail ||
    !sellerInfo.value.sellerTel ||
    !sellerInfo.value.sellerAddress ||
    !sellerInfo.value.sellerInfo
  ) {
    return;
  }
  let delivery = {
    sellerSeq: sellerInfo.value.sellerSeq,
    sellerImg: sellerInfo.value.sellerImg,
    sellerNickname: sellerInfo.value.sellerNickname,
    sellerTel: sellerInfo.value.sellerTel,
    sellerAddress: sellerInfo.value.sellerAddress,
    sellerInfo: sellerInfo.value.sellerInfo,
  };

  http
    .put('', delivery)
    .then((res) => {
      console.log(res);
      if (!res.data.success) return;
      dialog.value = false;
    })
    .catch((err) => {
      console.log('에러 발생', err);
    });
}

function imgUpload() {
  let form = new FormData();
  const sellerUpdateImg = document.getElementById('sellerUpdateImg').files[0];
  form.append('file', sellerUpdateImg);
  axios
    .post('https://j7d101.p.ssafy.io/api/file', form, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => (sellerInfo.value.sellerImg = res.data.data))
    .then(() => alert('파일이 업로드 되었습니다.'));
}
</script>
