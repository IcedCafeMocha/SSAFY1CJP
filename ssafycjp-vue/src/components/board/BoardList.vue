<template>
  <div class="container">
    <h2>{{ boardName }}</h2>
    <br>
    <h4>게시글 목록</h4>
    <hr />
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="search-input-wrapper">
        <BoardSearchInput />
      </div>
      <button class="btn btn-pastel" @click="goToCreateBoard" v-if="!isMainBoard">글쓰기</button>
    </div>
    <div class="board-list">
      <table class="table table-hover text-center">
        <thead>
          <tr>
            <th>번호</th>
            <th>말머리</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>좋아요</th>
            <th>등록일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="board in currentPageBoardList" :key="board.id">
            <td>{{ board.id }}</td>
            <td :class="{'notice-category': board.categoryName === '공지'}">{{ board.categoryName }}</td>
            <td>
              <RouterLink :to="`/main/detail/${board.id}`" :boardId="board.id" class="custom-link">
                {{ board.title }}
              </RouterLink>
            </td>
            <td>{{ board.writer }}</td>
            <td>{{ board.viewCnt }}</td>
            <td>{{ board.like }} <span v-if="board.like >= 100">🔥</span></td>
            <td>{{ formatDate(board.regDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <nav aria-label="Page navigation">
      <ul class="pagination d-flex justify-content-center">
        <li class="page-item">
          <a
            class="page-link"
            @click.prevent="currentPage--"
            :class="{ disabled: currentPage <= 1 }"
            href="#"
            >&lt;</a
          >
        </li>
        <li
          class="page-item"
          :class="{ active: currentPage === page }"
          v-for="page in pageCount"
          :key="page"
        >
          <a class="page-link" href="#" @click.prevent="clickPage(page)">{{
            page
          }}</a>
        </li>
        <li class="page-item">
          <a
            class="page-link"
            @click.prevent="currentPage++"
            :class="{ disabled: currentPage >= pageCount }"
            href="#"
            >&gt;</a
          >
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import BoardSearchInput from './BoardSearchInput.vue';

const props = defineProps({
  postboardId: {
    type: Number,
    default: null,
  },
  postboardNames: {
    type: Array,
    required: true,
  },
});

const store = useBoardStore();
const router = useRouter();

const fetchBoards = (boardId) => {
  if (boardId !== null) {
    store.getBoardList(boardId);
  } else {
    store.getBoardList(); // 모든 게시글 가져오기
  }
};

onMounted(() => {
  fetchBoards(props.postboardId);
});

watch(
  () => props.postboardId,
  (newValue) => {
    fetchBoards(newValue);
  },
  { immediate: true }
);

const perPage = 10;
const currentPage = ref(1);

const pageCount = computed(() => {
  return Math.ceil(store.boardList.length / perPage);
});

const clickPage = function (page) {
  currentPage.value = page;
};

const currentPageBoardList = computed(() => {
  return store.boardList.slice(
    (currentPage.value - 1) * perPage,
    currentPage.value * perPage
  );
});

const boardName = computed(() => {
  if (props.postboardId !== null && props.postboardNames.length > 0) {
    return props.postboardNames[props.postboardId - 1];
  }
  return '메인';
});

const isMainBoard = computed(() => props.postboardId === null);

const formatDate = (dateArray) => {
  if (!dateArray || !Array.isArray(dateArray)) return '';
  const [year, month, day, hour, minute, second] = dateArray;
  return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}:${String(second).padStart(2, '0')}`;
};

const goToCreateBoard = () => {
  router.push({ name: 'boardCreate' });
};
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: auto;
}

.board-list {
  margin-top: 20px; /* 검색창과 게시글 리스트 사이에 간격 추가 */
}

.search-input-wrapper {
  flex: 1;
  margin-right: 10px;
}

.table {
  width: 100%;
  margin-bottom: 1rem;
  color: #212529;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 0.75rem;
  vertical-align: top;
  border-top: 1px solid #dee2e6;
}

.table thead th {
  vertical-align: bottom;
  border-bottom: 2px solid #dee2e6;
}

.table tbody + tbody {
  border-top: 2px solid #dee2e6;
}

.table-hover tbody tr:hover {
  color: #495057;
  background-color: rgba(0, 0, 0, 0.075);
}

.table th {
  text-align: inherit;
  background-color: #f8f9fa;
  border-bottom: 2px solid #dee2e6;
}

.table td {
  padding: 0.75rem;
  border-top: 1px solid #dee2e6;
  background-color: #fff;
}

.pagination {
  display: flex;
  padding-left: 0;
  list-style: none;
  border-radius: 0.25rem;
}

.page-item {
  margin: 0 5px;
}

.page-link {
  position: relative;
  display: block;
  padding: 0.5rem 0.75rem;
  margin-left: -1px;
  line-height: 1.25;
  color: #000;
  background-color: #fff;
  border: 1px solid #dee2e6;
}

.page-link:hover {
  z-index: 2;
  color: #000;
  text-decoration: none;
  background-color: #e9ecef;
  border-color: #dee2e6;
}

.page-item.active .page-link {
  z-index: 1;
  color: #000;
  background-color: #c3fcfc;
  border-color: #c3fcfc;
}

.page-item.disabled .page-link {
  color: #6c757d;
  pointer-events: none;
  background-color: #fff;
  border-color: #dee2e6;
}

/* RouterLink 스타일 재정의 */
.custom-link {
  color: inherit;
  text-decoration: none;
}

.custom-link:hover {
  color: inherit;
  text-decoration: underline;
}

.btn-pastel {
  background-color: #c3fcfc;
  border-color: #c3fcfc;
  color: #000;
}

.btn-pastel:hover {
  background-color: #c3fcfc;
  border-color: #c3fcfc;
  color: #000;
}

.notice-category {
  color: red;
}

h2 {
  width: fit-content;
  background: linear-gradient(to top, #c3fcfc 20%, transparent 30%);
}
</style>
