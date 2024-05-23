<template>
    <div class="container">
      <h4>게시글 수정</h4>
      <div class="form-floating mb-3">
        <input type="text" class="form-control" id="title" placeholder="제목" v-model="store.board.title">
        <label for="title">Title</label>
      </div>
      <div class="form-floating mb-3">
        <textarea class="form-control" id="content" placeholder="내용" style="height: 200px" v-model="store.board.content"></textarea>
        <label for="content">Content</label>
      </div>
      <div class="d-flex justify-content-end">
        <button class="btn btn-outline-success mx-3" @click="updateBoard">수정</button>
        <button class="btn btn-outline-danger" @click="backButton">뒤로</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useBoardStore } from '@/stores/board';
  import { useRoute, useRouter } from 'vue-router';
  import { onMounted } from 'vue';
  
  const store = useBoardStore();
  const router = useRouter();
  const route = useRoute();
  
  const updateBoard = async () => {
    try {
      await store.updateBoard(route.params.id, store.board);
      router.go(-1); // 이전 페이지로 이동
    } catch (error) {
      console.error('게시글을 수정하는 데 실패했습니다:', error);
    }
  };
  
  const backButton = () => {
    router.go(-1);
  };
  
  onMounted(() => {
    store.getBoard(route.params.id);
  });
  </script>
  
  <style scoped></style>
  