<template>
    <v-app>
      <TheHeaderNav />
      <v-main>
        <v-container>
          <div class="main-layout" @mouseleave="drawer = false">
            <div
              class="hover-area"
              @mouseover="drawer = true"
            ></div>
            <v-navigation-drawer
              v-model="drawer"
              location="left"
              permanent
              temporary
              scrim="true, rgba(110, 0, 0, 0.5)"
              hide-overlay
              rounded="lg"
              border="0"
              class="custom-drawer"
            >
              <v-list>
                <v-list-item
                  v-for="(item, index) in items"
                  :key="index"
                  @click="navigateToBoard(item.title)"
                >
                  <v-list-item-content>
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-navigation-drawer>
            <div class="content">
              <RouterView />
            </div>
          </div>
        </v-container>
      </v-main>
    </v-app>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
  
  const items = ref([
    { title: '베스트 글' },
    { title: '전체 게시글' },
    { title: '자유 게시판' },
    { title: '오운완' },
    { title: '다이어트 일기' },
    { title: '운동 함께 모임' },
    { title: '새로운 액티비티 추천' },
  ]);
  
  const drawer = ref(false);
  
  const router = useRouter();
  const navigateToBoard = (boardName) => {
    router.push({ name: '', params: { 게시판 } });
  };
  </script>
  
  <style scoped>
  .main-layout {
    display: flex;
     /* 전체 화면 높이 */
    height: 100vh;
    width: 100px;
    position: relative;
  }
  
  .hover-area {
    position: absolute;
    left: -70px;
    width: 50px;
    margin-top: 12px;
    height: 100%;
    z-index: 10;
    background-color: blue;
  }
  
  .content {
    margin-top: 12px;
    flex: 1;
    padding: 20px;
    background-color: red;
  }

  .custom-drawer{
    background-color: rgb(227, 227, 227);
  }
  
  </style>
  