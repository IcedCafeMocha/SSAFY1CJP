<template>
  <div class="container my-5">
    <div class="post-details">
      <h2 class="post-title">{{ board.title }}</h2>
      <div class="post-meta">
        <span class="post-writer">작성자 : {{ board.writer }}</span>
        <span class="post-info">
          <span class="post-date">{{ formatDate(board.regDate) }}</span>
          <span class="post-views">조회수: {{ board.viewCnt }}</span>
        </span>
      </div>
      <hr />
      <div class="post-content">
        <p>{{ board.content }}</p>
      </div>
      <hr />
      <div class="post-actions">
        <div class="like-section">
          <button class="btn btn-like" :class="{'liked': hasLiked}" @click="toggleLike">
            좋아요
          </button>
          <span class="like-count">{{ board.like }}</span>
        </div>
        <div class="action-buttons">
          <button class="btn btn-outline-secondary" @click="moveUpdate">수정</button>
          <button class="btn btn-outline-danger" @click="deleteBoard">삭제</button>
        </div>
      </div>
      <hr />
      <ReplyList :boardId="board.id" />
      <hr/>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted, ref } from 'vue'
import { useBoardStore } from '@/stores/board'
import ReplyList from '@/components/Reply/ReplyList.vue'

const route = useRoute()
const router = useRouter()
const store = useBoardStore()

const board = computed(() => store.board)
const hasLiked = ref(false)

const fetchBoardDetails = async () => {
  try {
    await store.getBoard(route.params.id)
    hasLiked.value = await store.checkIfUserLikedBoard(route.params.id)
  } catch (error) {
    console.error('게시글 정보를 가져오는 데 실패했습니다:', error)
  }
}

const moveUpdate = () => {
  router.push({ name: 'boardUpdate', params: { id: route.params.id } })
}

const deleteBoard = async () => {
  try {
    await store.deleteBoard(route.params.id)
    router.go(-1)
  } catch (error) {
    console.error('게시글 삭제에 실패했습니다:', error)
  }
}

const toggleLike = async () => {
  try {
    if (hasLiked.value) {
      await store.dislikeBoard(route.params.id)
    } else {
      await store.likeBoard(route.params.id)
    }
    hasLiked.value = !hasLiked.value
    fetchBoardDetails() // 추천 후 게시글 정보를 다시 가져와서 업데이트
  } catch (error) {
    console.error('좋아요 상태 변경에 실패했습니다:', error)
  }
}

const formatDate = (dateArray) => {
  if (!dateArray || !Array.isArray(dateArray)) return ''
  const [year, month, day, hour, minute, second] = dateArray
  return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}:${String(second).padStart(2, '0')}`
}

onMounted(() => {
  fetchBoardDetails()
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: auto;
}

.post-details {
  padding: 20px;
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.post-title {
  font-size: 2rem;
  margin-bottom: 10px;
}

.post-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: #777;
  margin-bottom: 20px;
}

.post-meta .post-info {
  display: flex;
  gap: 10px;
}

.post-content {
  font-size: 1rem;
  line-height: 1.6;
  margin-bottom: 20px;
}

.post-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.like-section {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
}

.btn-like {
  font-size: 0.9rem;
  background-color: #fff;
  border: 1px solid #28a745;
  color: #28a745;
}

.btn-like.liked {
  background-color: #28a745;
  color: #fff;
}

.like-count {
  margin-left: 10px;
  font-size: 1rem;
  color: #28a745;
}

.action-buttons {
  display: flex;
  gap: 10px;
}
</style>
