<template>
    <div class="container">
      <div class="left-pane">
        <LoginForm />
      </div>
      <div class="right-pane">
        <IntroCarousel />
      </div>
    </div>
</template>

<script setup>
import IntroCarousel from '@/components/common/IntroCarousel.vue';
import LoginForm from '@/components/common/LoginForm.vue';

</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100vh;
}

.left-pane, .right-pane {
  display: flex;
  justify-content: center;
  align-items: center;
}

.left-pane {

  height: 100%;
  flex: 3; /* 비율 3 */
  display: flex;
  justify-content: flex; /* 왼쪽으로 치우치게 함 */
  padding-left: 20px; /* 약간의 여백 추가 */
}

.right-pane {

  height: 100%;
  flex: 7; /* 비율 7 */
}


</style>
