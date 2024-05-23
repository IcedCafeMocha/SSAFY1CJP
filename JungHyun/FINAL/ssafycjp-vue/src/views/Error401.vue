<template>
    <v-container>
      <v-card class="mx-auto mt-5" max-width="800" outlined>
        <v-card-title>
          <h3>401 Unauthorized</h3>
        </v-card-title>
        <v-card-text>
          <p>접근 권한이 없습니다. 다시 로그인 해주세요.</p>
          <v-btn @click="goToHome">로그인 페이지로 이동</v-btn>
        </v-card-text>
      </v-card>
    </v-container>
  </template>
  
  <script>
  export default {
    methods: {
      goToHome() {
        this.$router.push({ name: 'home' });
      }
    }
  };
  </script>