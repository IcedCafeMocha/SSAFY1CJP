<template>
  <div class="container my-5">
    <h4>게시글 작성</h4>
    <div class="d-flex mb-3">
      <div class="form-floating me-2" style="flex: 0 0 150px;">
        <select class="form-select" v-model="board.category">
          <option v-for="category in categories" :key="category.id" :value="category.category">
            {{ category.name }}
          </option>
        </select>
        <label for="category">말머리 선택</label>
      </div>
      <div class="form-floating flex-grow-1">
        <input type="text" class="form-control" id="title" placeholder="제목" v-model="board.title">
        <label for="title">제목</label>
      </div>
    </div>
    <div class="form-floating mb-3">
      <textarea class="form-control" id="content" placeholder="내용" style="height: 200px" v-model="board.content"></textarea>
      <label for="content">내용</label>
    </div>
    <div class="d-flex justify-content-end">
      <button class="btn btn-outline-primary" @click="createBoard">등록</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useBoardStore } from "@/stores/board";
import { useRoute } from 'vue-router';

const board = ref({
  title: "",
  writer: "",
  content: "",
  category: "",
  postBoardId: null
});

const categories = ref([]);
const store = useBoardStore();
const route = useRoute();

const fetchCategories = async () => {
  try {
    const postBoardId = route.params.postboardId;
    board.value.postBoardId = postBoardId; // 게시판 ID를 설정
    await store.fetchCategoriesByPostBoardId(postBoardId);
    categories.value = store.categories;
  } catch (error) {
    console.error('말머리를 가져오는 데 실패했습니다:', error);
  }
};

onMounted(() => {
  fetchCategories();
});

const createBoard = async () => {
  try {
    await store.createBoard(board.value);
  } catch (error) {
    console.error('게시글 생성에 실패했습니다:', error);
  }
};
</script>

<style scoped>
.container {
  max-width: 1200px; /* 기존 800px에서 1200px로 늘림 */
  margin: auto;
}

.form-floating {
  position: relative;
}

.me-2 {
  margin-right: 0.5rem;
}

.btn-outline-primary {
  background-color: #c3fcfc;
  border-color: #c3fcfc;
  color: #000;
}

.btn-outline-primary:hover {
  background-color: #c3fcfc;
  border-color: #c3fcfc;
  color: #000;
}
</style>
