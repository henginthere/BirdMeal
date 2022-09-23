<template>
  <v-app>
    <v-container>
      <v-row>
        <v-col >
          <v-card width="600px">
            <v-card-title>
              <v-icon color="primary_orange">mdi-alert-box</v-icon>
              판매자 정보를 입력해주세요
            </v-card-title>
            <v-card-text>
              판매자 정보를 통해 상품을 판매할 수 있습니다.
            </v-card-text>
            <v-card-actions>
              <v-btn variant="outlined" color="primary_orange" class="ml-2"
                >판매자 정보 등록하기</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup></script>

<style lang="scss" scoped></style>
