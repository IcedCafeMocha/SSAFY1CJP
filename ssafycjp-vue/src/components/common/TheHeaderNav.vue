<template>
  <div id="container">
    <header class="header">
      <nav class="nav-left">
        <RouterLink to="/main">
          <div class="imgContainer" @click="goMain">
            <img src="@/assets/logo5.png" alt="Main Image" class="main-image">
          </div>
        </RouterLink>
      </nav>

      <nav class="nav-center">
        <RouterLink :to="{ name: 'exerciseRecommendation' }">운동 추천</RouterLink>
        <RouterLink :to="{ name: 'mypage' }">AI 트레이너와의 상담</RouterLink>
      </nav>

      <nav class="info">
        <div class="info-item">
          <h6 class="infoContainer" v-if="user" style="font-style: italic;">{{ user.name }} 님</h6>
        </div>
        <div class="info-item">
          <RouterLink :to="{ name: 'mypage' }"><h6 class="infoContainer">마이페이지</h6></RouterLink>
        </div>
        <div class="info-item">
          <button @click="logout" class="logout-button"><h6 class="infoContainer">로그아웃</h6></button>
        </div>
      </nav>
    </header>
    <div class="blackContainer"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import { useRouter } from 'vue-router';

const Ustore = useUserStore();
const user = ref(null);
const router = useRouter();

const logout = function() {
  Ustore.logout();
  router.push({ name: 'home' });
};

const goMain = function() {
  router.push({ name: 'main' });
};

onMounted(async () => {
  user.value = await Ustore.getUserInfoFromToken();
  console.log("TheHeaderNav에 가져온 user 정보야! 여기서 이름만 가져다 쓸거야!")
  console.log(user.value);
});
</script>

<style scoped>
#container {
  width: 100%;
  height: 100px; /* 네비게이션 바 높이 증가 */
  background-color: #f8f9fa; /* 밝은 배경색 */
  font-size: 25px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px; /* 좌우 패딩 추가 */
  background-color: #9fe4e4;
}

.nav-left {
  margin-left: 10px; /* 이미지 왼쪽 여백 조정 */
}

.nav-center {
  flex: 1;
  text-align: center;
  font-size: 20px; /* 텍스트 크기 증가 */
  margin-top: -30px;
}

.nav-center a {
  margin: 0 5%;
}

.navb a, .nav-center a, .info a {
  font-weight: bold;
  text-decoration: none;
  color: white;
  font-size: 30px;
}

.navb a.router-link-exact-active, .nav-center a.router-link-exact-active {
  color: black;
  background: linear-gradient(to top, #ffffff 20%, transparent 30%);
}

.info {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-top: -30px;
}

.info div {
  margin: 0 10px;
}

.info-item {
  display: flex;
  align-items: center;
  color: white;
}

.info h6 {
  margin: 0;
}

a {
  text-decoration: none;
  color: #333333;
}

a:hover {
  color: black;
}

.main-image {
  max-height: 140px; 
  object-fit: contain;
}

.logout-button {
  background: none;
  border: none;
  font-weight: bold;
  color: white;
  cursor: pointer;
}

.logout-button:hover {
  color: black;
}

.infoContainer {
  color: black;
  font-size: large;
  font-weight: 1000;
  background: linear-gradient(to top, #ffffff 20%, transparent 30%);
}

img {
  position: relative;
  z-index: 1;
}

.blackContainer {
  position: absolute;
  z-index: 0;
  background-color: white;
  width: 100%;
  height: 50px;
  top: 100px;
}

</style>
