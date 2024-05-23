<template>
  <TheHeaderNav />
  <div v-if="user">
    <v-app>
      <v-container>
        <v-card class="mx-auto mt-5" max-width="800">
          <v-card-title>
            <h3>회원 정보</h3>
          </v-card-title>

          <v-card-text>
            <div class="userInfo">
              <v-row>
                <v-col class="text-center">
                  <v-list-item-title>ID: {{ user.id }}</v-list-item-title>
                  
                  <v-avatar size="300">
                    <!-- 바꾸려는 이미지 가져오기 -->
                    <div class="preview" v-if="previewFlag">
               
                      <img :src="previewImage" alt="Profile Preview">
                    </div>
                    <!-- 회원 정보에서 기존 이미지를 -->
                    <img v-else class="profileImg" :src="imageUrl" alt="Profile">
        
                  </v-avatar>
                  <hr>
                  <input type="file" accept="image/*" @change="changePreviewImage"/>
                  <v-btn color="primary" @click="uploadProfileImage">프로필 사진 변경</v-btn>
                </v-col>
              </v-row>
              <v-card class="mx-auto mt-5" max-width="400">
                <v-row>
                  <v-col cols="6">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title class="info-item">이름: {{ user.name }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="6" class="text-right">
                    <v-btn @click="editField('name')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="9">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title class="info-item">e-mail: {{ user.email }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="3" class="text-right">
                    <v-btn @click="editField('email')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title>비밀번호</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="6" class="text-right">
                    <v-btn @click="editField('password')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title class="info-item">나이: {{ user.age }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="6" class="text-right">
                    <v-btn @click="editField('age')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title v-if="user.goalWeight" class="info-item">목표 체중: {{ user.goalWeight }}</v-list-item-title>
                        <v-list-item-title v-else class="info-item">목표 체중: {{ user.goalWeight }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="6" class="text-right">
                    <v-btn @click="editField('goalWeight')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title v-if="user.nowWeight" class="info-item">현재 체중: {{ user.nowWeight }}</v-list-item-title>
                        <v-list-item-title v-else class="info-item">현재 체중: {{ user.nowWeight }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="6" class="text-right">
                    <v-btn @click="editField('nowWeight')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="6">
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title v-if="user.height" class="info-item">현재 키: {{ user.height }}</v-list-item-title>
                        <v-list-item-title v-else class="info-item">현재 키: {{ user.height }}</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </v-col>
                  <v-col cols="6" class="text-right">
                    <v-btn @click="editField('height')" color="primary">수정</v-btn>
                  </v-col>
                </v-row>

              </v-card>
            </div>

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
                        <div>현재 BMI는:<h2>{{ bmi }}</h2> 입니다!</div>
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
          </v-card-text>
        </v-card>

        <v-dialog v-model="showModal" persistent max-width="400">
          <v-card>
            <v-card-title>
              <span class="headline">정보 수정</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12">
                    <v-text-field v-model="tempValue" label="새로운 값"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="showModal = false">취소</v-btn>
              <v-btn color="blue darken-1" text @click="saveChanges">저장</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>
    </v-app>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useUserStore } from "@/stores/user";
import Attendance from '@/components/user/Attendance.vue'
import TheHeaderNav from '@/components/common/TheHeaderNav.vue'

const store = useUserStore();
const user = ref(null);
const showModal = ref(false);
const currentField = ref('');
const tempValue = ref('');
const previewFlag = ref(false);
const previewImage = ref('');
const imageUrl = computed(() => store.imageUrl);

const editField = ref((field) => {
  currentField.value = field;
  tempValue.value = user.value[field];
  showModal.value = true;
})

const changePreviewImage = (e) =>{
  const files = e.target.files;
  if(files.length>0){
    const file = files[0]
    const reader = new FileReader()

    reader.onload = (e) =>{
      previewImage.value = e.target.result
    }

    reader.readAsDataURL(file)
    previewFlag.value = true;
  }
}

const uploadProfileImage = async () => {
  const fileInput = document.querySelector('input[type="file"]');
  const file = fileInput.files[0];
  console.log(file)
  if (file) {
    await store.userImgRegist(file,user.value.id);
    previewFlag.value = false; // 업로드 후 미리보기 초기화
  }
};

const saveChanges = ref(() => {
  if (currentField.value) {
    store.saveInfoChanges(user.value.id, currentField.value, tempValue.value)
    user.value[currentField.value] = tempValue.value;
    currentField.value = '';
    tempValue.value = '';
    showModal.value = false;
  }
})

const bmi = computed(() => {
  if (user.value && user.value.nowWeight) {
    return (user.value.nowWeight / (user.value.height / 100 * user.value.height / 100)).toFixed(2);
  }
  return 'N/A';
});

onMounted(async () => {
  user.value = await store.getUserInfoFromToken();
  console.log(user.value)
});
</script>

<style>
.v-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.userInfo {
  display: flex;
  justify-content: space-around;
  align-items: center;
}



</style>
