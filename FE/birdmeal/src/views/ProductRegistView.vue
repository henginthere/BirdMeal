<template>
  <v-app>
    <v-overlay
      :model-value="overlay"
      class="align-center justify-center"
      persistent
    >
      <loading />
    </v-overlay>

    <v-container class="text-h4">
      <v-row>
        <v-col class="ml-4 mt-4 mb-2">상품 등록</v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col>
          <v-card width="800px" class="elevation-0">
            <v-card-title>상품명</v-card-title>
            <v-card-text class="mt-3">
              <v-text-field
                variant="underlined"
                placeholder="예) 삼겹살(10kg)"
                color="primary_orange"
                v-model="name"
              />
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="800px" class="elevation-0">
            <v-card-title>판매가</v-card-title>
            <v-card-text class="mt-3">
              <v-text-field
                variant="underlined"
                placeholder="예) 10000"
                color="primary_orange"
                v-model="price"
                suffix="ELN"
              />
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-card width="800px" class="elevation-0">
            <v-card-title>카테고리</v-card-title>
            <v-card-text class="mt-3">
              <v-select
                :items="categorys"
                v-model="selectCategory"
                label="카테고리"
                dense
                color="primary_orange"
                variant="underlined"
              ></v-select>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <v-col class="pe-0">
          <v-card width="800px" class="elevation-0">
            <v-card-title>상품 이미지</v-card-title>
            <v-card class="mx-4 my-2 elevation-0">
              <v-card-title>대표 이미지</v-card-title>
              <v-card-text class="mt-2">
                <v-file-input
                  label="썸네일 이미지 파일"
                  id="productThumbnailImg"
                  v-on:change="imgUpload1"
                  prepend-icon="mdi-camera"
                  variant="underlined"
                />
                <v-img
                  :src="
                    productThumbnailImgURL !== ''
                      ? productThumbnailImgURL
                      : null
                  "
                  max-height="200"
                  min-width="250"
                />
              </v-card-text>
            </v-card>
            <v-card class="mx-4 my-2 elevation-0">
              <v-card-title>상세설명 이미지</v-card-title>
              <v-card-text class="mt-2">
                <v-file-input
                  label="상세설명 이미지 파일"
                  hint="상품 상세설명은 하나의 이미지로 업로드 해주세요"
                  id="productDescriptionImg"
                  v-on:change="imgUpload2"
                  prepend-icon="mdi-camera"
                  variant="underlined"
                ></v-file-input>
                <v-img
                  :src="
                    productDescriptionImgURL !== ''
                      ? productDescriptionImgURL
                      : null
                  "
                  max-height="1000"
                  min-width="250"
                />
              </v-card-text>
            </v-card>
          </v-card>
        </v-col>
      </v-row>

      <v-row class="d-flex justify-center">
        <v-col> </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-sheet width="800px" class="d-flex justify-center">
            <v-btn color="primary_orange" v-on:click="registProduct"
              >등록하기</v-btn
            >
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import { createTrade } from '@/web3util/events';
import axios from 'axios';
import { mapState } from 'pinia';
import { authState } from '@/stores/auth';
import loading from '@/components/Loading.vue';
export default {
  data() {
    return {
      name: '',
      price: null,
      ca: '',
      selectCategory: '',
      productThumbnailImgURL: '',
      productDescriptionImgURL: '',
      overlay: false,
      category: {
        '고기': 1,
        '채소/과일': 2,
        '밀키트/간편식': 3,
        냉동식품: 4,
        과자류: 5,
        음료: 6,
        베이커리: 7,
        '쌀/반찬': 8,
        '양념/오일': 9,
      },
      categorys: [
        '고기',
        '채소/과일',
        '밀키트/간편식',
        '냉동식품',
        '과자류',
        '음료',
        '베이커리',
        '쌀/반찬',
        '양념/오일',
      ],
    };
  },
  computed: {
    ...mapState(authState, ['user']),
  },

  components: {
    loading,
  },

  methods: {
    createTrade,
    /*
      썸네일 업로드용 함수
      파일을 보내야함으로 formData를 보내는 형식으로 작성
      위의 id로 연결해둔 input:file에서 파일을 받고 거기서 .file[0]해야 보낼 수 있음
      그걸 formdata에 담고 그걸 다시 백엔드로 보냄
      이후 받아온 url을 저장
      이로직은 약간의 시간이 걸리므로 alert창을 통해 완료되었음을 명시적으로 사용자에게
      보여주기위해 마지막에 코드를 추가함
    */
    imgUpload1() {
      let form = new FormData();
      const productThumbnailImg = document.getElementById('productThumbnailImg')
        .files[0];
      form.append('file', productThumbnailImg);
      axios
        .post('https://j7d101.p.ssafy.io/api/file', form, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((res) => (this.productThumbnailImgURL = res.data.data))
        .then(() => alert('파일이 업로드 되었습니다.'));
    },

    imgUpload2() {
      let form = new FormData();
      const productDescriptionImg = document.getElementById(
        'productDescriptionImg'
      ).files[0];
      form.append('file', productDescriptionImg);
      axios
        .post('https://j7d101.p.ssafy.io/api/file', form, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((res) => (this.productDescriptionImgURL = res.data.data))
        .then(() => alert('파일이 업로드 되었습니다.'));
    },

    /*
      상품등록함수
      일단 if문으로 데이터 입력없이 요청보내는거 막음
      이후 모두 되었으면 먼저 web3를 통해 컨트랙트 생성
      컨트랙트가 생성되었으면 받아온 Ca를 저장
      그 이후에 json객체 만들어서 백엔드로 전송
      상품등록이 다되면 상품목록 페이지로 이동시킴
    */
    registProduct() {
      if (this.name === '') {
        alert('상품명을 입력해주십시오');
      } else if (this.price === null) {
        alert('가격을 입력해주십시오');
      } else if (this.selectCategory === '') {
        alert('카테고리를 선택해주십시오');
      } else if (this.productThumbnailImgURL === '') {
        alert('썸네일을 선택해주십시오');
      } else if (this.productDescriptionImgURL === '') {
        alert('상세설명 파일을 선택해주십시오');
      } else {
        this.overlay = !this.overlay;
        createTrade(this.name, this.price)
          .then(
            (res) =>
              (this.ca = res.events.TradeCreated.returnValues.tradeAddress)
          )
          .then(() =>
            axios.post(
              'https://j7d101.p.ssafy.io/api/seller/product',
              {
                categorySeq: this.category[this.selectCategory],
                sellerSeq: this.user.sellerSeq,
                productName: this.name,
                productPrice: this.price,
                productCa: this.ca,
                productThumbnailImg: this.productThumbnailImgURL,
                productDescriptionImg: this.productDescriptionImgURL,
              },
              { headers: { 'Content-type': 'application/json' } }
            )
          )
          .then(() => (this.overlay = !this.overlay))
          .then(() => this.$router.push('/products'));
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
