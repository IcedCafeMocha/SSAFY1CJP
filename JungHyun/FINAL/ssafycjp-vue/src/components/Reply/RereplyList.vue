<template>
  <div class="replies-section">
    <h4>답글</h4>
    <div v-for="rereply in rereplies" :key="rereply.id" class="rereply">
      <div class="rereply-meta">
        <span class="reply-writer">{{ rereply.writer }}</span>
        <span class="reply-date">{{ formatDate(rereply.regDate) }}</span>
      </div>
      <div class="rereply-content">
        <p>{{ rereply.content }}</p>
      </div>
      <div class="rereply-actions">
        <button class="btn btn-outline-secondary btn-sm" @click="editRereply(rereply.id)">수정</button>
        <button class="btn btn-outline-danger btn-sm" @click="deleteRereply(rereply.id)">삭제</button>
      </div>
    </div>
    <div class="form-floating mb-3">
      <textarea class="form-control" id="newRereply" placeholder="답글을 입력하세요" style="height: 100px" v-model="newRereply"></textarea>
      <label for="newReply">새로운 답글</label>
    </div>
    <div class="d-flex justify-content-end">
      <button class="btn btn-outline-primary" @click="addRereply">등록</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useBoardStore } from '@/stores/board';



const props = defineProps({
  replyId: {
    type: Number,
    required: true
  },
  
});

const store = useBoardStore();
const rereplies = ref([]);
const newRereply = ref("");

const fetchRereplies = async () => {

  try {
    rereplies.value = await store.getRereplies(props.replyId);
  } catch (error) {
    console.error('답글을 가져오는 데 실패했습니다:', error);
  }
};

const addRereply = async () => {
  try {
    await store.addRereply(props.replyId, newRereply.value);
    newRereply.value = "";
    fetchRereplies();
  } catch (error) {
    console.error('답글을 추가하는 데 실패했습니다:', error);
  }
};

const editRereply = (rereplyId) => {
  console.log(`Edit reply ${rereplyId}`);
  // 댓글 수정 로직 추가
};

const deleteRereply = async (rereplyId) => {
  try {
    await store.deleteRereply(rereplyId);
    fetchRereplies();
  } catch (error) {
    console.error('댓글 삭제에 실패했습니다:', error);
  }
};

const formatDate = (dateArray) => {
  if (!dateArray || !Array.isArray(dateArray)) return '';
  const [year, month, day, hour, minute, second] = dateArray;
  return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')} ${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}:${String(second).padStart(2, '0')}`;
};

onMounted(() => {
  fetchRereplies();
});
</script>

<style scoped>
.rereplies-section {
  margin-top: 20px;
  padding: 20px;
  background: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.rereply {
  margin-bottom: 15px;
}

.rereply-meta {
  font-size: 0.9rem;
  color: #555;
  display: flex;
  justify-content: space-between;
}

.rereply-content {
  margin: 5px 0;
}

.rereply-actions {
  display: flex;
  gap: 5px;
  justify-content: flex-end;
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
