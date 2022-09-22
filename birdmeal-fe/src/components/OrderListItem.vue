<template>
  <v-card class="mx-auto" max-width="344" variant="outlined">
    <v-card-item>
      <div>
        <div class="text-overline mb-1">
          {{ order.categoryName }}
        </div>
        <div class="text-h6 mb-1">
          {{ order.productName }}
        </div>
        <div class="text-caption">상풍입니다.</div>
      </div>
    </v-card-item>

    <v-card-actions>
      <v-btn variant="outlined"> Button </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup>
defineProps(['order']);
</script>

<style lang="scss" scoped></style>
