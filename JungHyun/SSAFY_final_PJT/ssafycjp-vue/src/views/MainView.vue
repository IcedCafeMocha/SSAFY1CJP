<template>
  <v-app>
    <TheHeaderNav @go-main="goMain" />
    <div class="main-layout">
      <NavigationDrawer :postboardNames="postboardNames" @select-board="selectBoard" /> 
      <div class="content">
        <div v-if="user && !currentBoardId">
          <v-row class="mt-4">
              <v-col cols="6">
                <v-card outlined>
                  <v-card-title>
                    목표 체중까지 화이팅!
                  </v-card-title>
                  <v-card-text>
                    <v-row>
                      <v-col cols="6">
                        
                        <div>현재 체중: {{ user.nowWeight }}</div>
                      </v-col>
                    </v-row>
                    <v-row class="mt-2">
                      <v-col cols="6">
                
                        <div>목표 체중: {{ user.goalWeight }} </div>
                      </v-col>
                    </v-row>
                    <v-row class="mt-2">
                      <v-col cols="6">
                        <div>현재 BMI는:<h2>{{bmi}}</h2> 입니다!</div>
                      </v-col>

                    </v-row>

                  </v-card-text>
                </v-card>
              </v-col>
              <v-col cols="6">
                <v-card outlined>
                  <v-card-title>
                    <div>연속 접속</div>
                  </v-card-title>
                  <v-card-text>
                    <v-row class="mt-2">
                      <v-col cols="6">
                        <Attendance />
                      </v-col>
                    </v-row>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
        </div>
            <hr/>
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
import { ref, onMounted, watch, computed } from 'vue'
import { useBoardStore } from '@/stores/board'

import { useRoute, useRouter } from 'vue-router'
import Attendance from '@/components/user/Attendance.vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore();
const store = useBoardStore()
const postboardNames = ref([])
const currentBoardId = ref(null)
const routerKey = ref('main')
const route = useRoute()
const router = useRouter()
const initialLoad = ref(true)
const user = ref(null)
const bmi = computed(() => {
    if (user.value && user.value.nowWeight && user.value.height) {
      return (user.value.nowWeight / (user.value.height / 100 * user.value.height / 100)).toFixed(2);
    }
    return '정보를 입력해주세요 !.! ';
  });
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
  user.value = await userStore.getUserInfoFromToken();
  console.log(user.value)
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
