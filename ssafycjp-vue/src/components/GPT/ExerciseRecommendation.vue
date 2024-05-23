<template>
    <div class="container my-5">
      <h2>운동 추천 설문</h2>
      <form v-if="!submitted" @submit.prevent="submitAnswers">
        <div v-for="(question, index) in questions" :key="index" class="mb-3">
          <label :for="'question-' + index" class="form-label">{{ question }}</label>
          <input type="text" class="form-control" :id="'question-' + index" v-model="answers[index]" required>
        </div>
        <button type="submit" class="btn btn-primary">제출</button>
      </form>
      <div v-else class="mt-4">
        <h3>추천 운동!</h3>
        <div v-for="(line, index) in recommendation" :key="index">
          <p>{{ line }}</p>
        </div>
        <div class="button-group">
          <button @click="resetForm" class="btn btn-secondary mt-3">다시 질문하기</button>
          <button @click="goBack" class="btn btn-secondary mt-3 ms-2">뒤로 가기</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axiosInstance from '@/utils/interceptor';
  import { useRouter } from 'vue-router';
  
  const questions = ref([
    "운동 종류를 선택해주세요. [ 1. 스포츠 2. 맨몸운동 3. 기구운동 ]",
    "운동 장소를 선택해주세요. [ 1. 실내 2. 야외 ]",
    "같이 하는 인원은 몇명인가요? [ 1. 혼자 2. 2명 3. 다수 ]",
    "운동 시간을 선택해주세요. [ 1. ~10분 2. ~30분 3. ~1시간 4. ~1시간 이상 ]",
    "운동 강도를 선택해주세요. [ 1. 강하게 2. 중간 정도 3. 널널하게 ]",
    "연령대를 선택해주세요. [ 1. ~20대 2. ~50대 3. 고령층 ]",
  ]);
  
  const answers = ref(Array(questions.value.length).fill(''));
  const recommendation = ref([]);
  const submitted = ref(false);
  const router = useRouter();
  
  const submitAnswers = async () => {
    try {
      console.log('Submitting answers:', answers.value);
      const response = await axiosInstance.post('/api/recommend-exercise', {
        questions: questions.value,
        answers: answers.value,
      });
      recommendation.value = splitRecommendation(response.data.recommendation);
      submitted.value = true;
    } catch (error) {
      console.error('운동 추천을 가져오는 데 실패했습니다:', error);
    }
  };
  
  const splitRecommendation = (text) => {
    return text.split('\n').filter(line => line.trim() !== '');
  };
  
  const resetForm = () => {
    answers.value = Array(questions.value.length).fill('');
    recommendation.value = [];
    submitted.value = false;
  };
  
  const goBack = () => {
    router.back();
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
    margin: auto;
  }
  
  h2 {
    margin-bottom: 20px;
  }
  
  .mb-3 {
    margin-bottom: 1rem;
  }
  
  .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
  }
  
  .btn-primary:hover {
    background-color: #0056b3;
    border-color: #0056b3;
  }
  
  .mt-3 {
    margin-top: 1rem;
  }
  
  .ms-2 {
    margin-left: 0.5rem;
  }
  
  .button-group {
    display: flex;
    justify-content: center; /* 버튼을 중앙으로 정렬 */
    gap: 10px; /* 버튼 간의 간격 */
  }
  </style>
  