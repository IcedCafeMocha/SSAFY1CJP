<template>
  <div class="container">
  <div class="signup-box">
    <h2 style="color: black">회원가입</h2>
    
      <form @submit.prevent="userRegist">
        <div class="input-group">
          <label for="userId">아이디 <a style="color: red;">*</a></label>
          <input type="text" id="userId" placeholder="아이디를 입력해주세요" v-model.trim="user.id" required>
        </div>
        <div class="input-group">
          <label for="password">비밀번호 <a style="color: red;">*</a></label>
          <input type="password" id="password" placeholder="비밀번호를 입력해주세요" v-model.trim="user.password" required>
        </div>
        <div class="input-group">
          <label for="username">이름 <a style="color: red;">*</a></label>
          <input type="text" id="username" placeholder="이름을 입력해주세요" v-model.trim="user.name" required>
        </div>
        <div class="input-group">
          <label for="useremail">이메일 <a style="color: red;">*</a></label>
          <input type="text" id="useremail" placeholder="이메일을 입력해주세요" v-model.trim="user.email" required>
        </div>
        <div class="input-group">
          <label for="userage">나이</label>
          <input type="number" id="userage" placeholder="나이를 입력해주세요" v-model.trim="user.age" >
        </div>
        <div class="input-group">
          <label for="profileimg">프로필 사진</label>
          <input type="file" id="profileimg" @change="inputFileUpload" accept="image/*">
        </div>
        <div class="input-group">
          <label>성별을 선택해주세요</label>
          <input type="radio" id="male" value="M" v-model="user.sex">
          <label for="male">남성</label> 
          <input type="radio" id="female" value="F" v-model="user.sex">
          <label for="female">여성</label>
        </div>
        <div class="input-group">
          <label for="nowWeight">현재 체중</label>
          <input type="text" id="nowWeight" placeholder="현재 몸무게를 입력해주세요" v-model.trim="user.nowWeight" >
        </div>
        <div class="input-group">
          <label for="goalWeight">목표 체중</label>
          <input type="number" id="goalWeight" placeholder="목표 몸무게를 입력해주세요" v-model.trim="user.goalWeight" >
        </div>
        <div class="input-group">
          <label for="userHeight">현재 키</label>
          <input type="number" id="userHeight" placeholder="키를 입력해주세요" v-model.trim="user.height" >
        </div>
        <button type="submit" class="signup-button">회원 가입</button>
      </form>
    </div>
    </div>
</template>

<script setup>
import { h, ref } from 'vue';
import { useUserStore } from '@/stores/user';

const store = useUserStore();

const user = ref({
  id:"",
  password:"",
  name:"",
  age:"",
  sex: "",
  goalWeight:"",
  nowWeight:"",
  height:""
})

const img = ref()

const inputFileUpload = (event) => {
  img.value = event.target.files[0];
};

const userRegist = function(){

  store.userRegist(user.value)
  console.log(user.value.id)
  if(img.value !== undefined){
    store.userImgRegist(img.value, user.value.id)
  }
}
  
</script>

<style scoped>

/* 이거 까만 글씨!! */
  label{
    color: black;
  }
  input{
    color: black;
  }



.container {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  left: 15%;
  top: 20%;

}
.signup-box {
  background-color: whitesmoke;
  padding: 20px;
  border-radius: 8px;
  text-align: left;
  width: 400px;
  color: white;
}

.signup-button {
  width: 100%;
  padding: 10px;
  background-color: skyblue;
  border: none;
  border-radius: 4px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

.input-group {
  margin-bottom: 15px;
}

.input-group input[type="text"],
.input-group input[type="password"],
.input-group input[type="number"],
.input-group input[type="file"] {
  width: calc(100% - 24px); /* input box의 길이를 늘림 */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box; /* padding 포함한 width 계산 */
}
</style>