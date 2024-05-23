<template>
  <v-navigation-drawer
    v-model="drawer"
    location="left"
    temporary
    hide-overlay
    rounded="lg"
    border="0"
    class="custom-drawer"
    @mouseleave="drawer = false"
  >
  <v-list v-if="isLoading">
      <v-progress-circular indeterminate color="primary" />
    </v-list>
    <v-list v-else>
      <!-- postboardNames를 v-for로 렌더링 -->
      <v-list-item
        v-for="(name, index) in postboardNames"
        :key="index"
        @click="selectBoard(index + 1)"
      >
        <v-list-item-content>
          <v-list-item-title>{{ name }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
  <div
    class="hover-area"
    @mouseover="drawer = true"
  ></div>
</template>

<script setup>
import { ref, watch } from 'vue';

const drawer = ref(false);
const isLoading = ref(true);
const emits = defineEmits(['select-board']);

const props = defineProps({
  postboardNames: Array
});

// const items = ref([
//   { id: 1, title: '게시판1' },
//   { id: 2, title: '게시판2' },
//   // 추가 게시판 항목을 여기에 추가
// ]);

watch(() => props.postboardNames, (newValue) => {
  if (newValue.length > 0) {
    isLoading.value = false;
  }
}, { immediate: true });

const selectBoard = (postboardId) => {
  emits('select-board', postboardId);
};
</script>

<style scoped>
.hover-area {
  position: fixed;
  left: 0;
  top: 70px; /* 헤더 높이 */
  width: 20px;
  height: calc(100vh - 70px); /* 헤더 높이 뺀 전체 높이 */
  z-index: 10;
  background-color: transparent; /* 디버깅을 위해 색상을 추가할 수 있음 */
}

.custom-drawer {
  top: 70px; /* 헤더 높이 */
  height: calc(100vh - 70px); /* 헤더 높이 뺀 전체 높이 */
  background-color: rgb(241, 241, 241);
}
</style>
