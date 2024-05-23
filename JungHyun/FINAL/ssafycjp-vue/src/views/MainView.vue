<template>
  <v-app>
    <TheHeaderNav @go-main="goMain" />
    <div class="main-layout">
      <NavigationDrawer :postboardNames="postboardNames" @select-board="selectBoard" /> 
      <div class="content">
        <BoardList v-if="initialLoad" :postboardId="currentBoardId" :postboardNames="postboardNames" />
        <RouterView v-else :postboardId="currentBoardId" :postboardNames="postboardNames" :key="routerKey" />
      </div>
    </div>

  </v-app>
</template>

<script setup>
import TheHeaderNav from '@/components/common/TheHeaderNav.vue'
import NavigationDrawer from '@/components/common/NavigationDrawer.vue'
import BoardList from '@/components/board/BoardList.vue'
import { ref, onMounted, watch } from 'vue'
import { useBoardStore } from '@/stores/board'
import { useRoute, useRouter } from 'vue-router'

const store = useBoardStore()
const postboardNames = ref([])
const currentBoardId = ref(null)
const routerKey = ref('main')
const route = useRoute()
const router = useRouter()
const initialLoad = ref(true)

const selectBoard = (boardId) => {
  initialLoad.value = false
  currentBoardId.value = boardId
  routerKey.value = `board-${boardId}`
  router.push({ name: 'boardList', params: { postboardId: boardId } })
}

const goMain = () => {
  initialLoad.value = false
  currentBoardId.value = null
  routerKey.value = 'main'
  router.push({ name: 'main' })
}

onMounted(async () => {
  await store.fetchPostboardNames()
  postboardNames.value = store.postboardNames
  if (route.name === 'main' && !route.params.postboardId) {
    currentBoardId.value = null
    routerKey.value = 'main'
    store.getBoardList()
    nextTick(() => {
      initialLoad.value = false
    })
  } else {
    initialLoad.value = false
  }
})

watch(
  () => route.params.postboardId,
  (newPostboardId) => {
    currentBoardId.value = newPostboardId
    routerKey.value = `board-${newPostboardId}`
  },
  { immediate: true }
)
</script>

<style scoped>
.main-layout {
  display: flex;
  height: calc(100vh - 70px); /* 헤더 높이를 뺀 높이 */
  margin-top: 70px; /* 헤더 높이 */
}

.content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}
</style>
