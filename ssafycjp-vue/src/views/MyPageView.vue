<template>
    <TheHeaderNav/>
    <v-app>
      <v-container>
        <v-card class="mx-auto mt-5" max-width="800">
          <v-card-title>
            <h3>회원 정보</h3>
            <v-col cols="6">
            </v-col>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title>ID: {{ user.id }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
        </v-card-title>

        <v-card-text>
            <div class="userInfo">
            <v-row>
              <v-col class="text-center">
                <v-avatar size="300">
                  <!-- 이미지 가져오기 -->
                  <img class="profileImg" src="@/assets/오승준_3x4.jpg" alt="Profile">
                </v-avatar>
              </v-col>
            </v-row>
            <v-card class="mx-auto mt-5 " max-width="400">
                <v-row>
                <v-col cols="6">
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title>이름: {{ user.name }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-col>
              <v-col cols="6" class="text-right">
                <v-btn @click="editField('name')" color="primary">수정</v-btn>
              </v-col>
            </v-row>
            <v-row>
                <v-col cols="6">
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title>e-mail: {{ user.email }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-col>
              <v-col cols="6" class="text-right">
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
                            <v-list-item-title>나이: {{ user.age }}</v-list-item-title>
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
                            <v-list-item-title>목표 체중: {{ user.goalWeight }}</v-list-item-title>
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
                            <v-list-item-title>현재 체중: {{ user.weight }}</v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-col>
              <v-col cols="6" class="text-right">
                <v-btn @click="editField('weight')" color="primary">수정</v-btn>
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
                        <div>현재 체중: {{ user.weight }}</div>
                      </v-col>
                      <v-col cols="6" class="text-right">
                        <v-btn @click="editField('weight')" color="primary">수정</v-btn>
                      </v-col>
                    </v-row>
                    <v-row class="mt-2">
                      <v-col cols="6">
                        <div>목표 체중: {{ user.goalweight }}</div>
                      </v-col>
                      <v-col cols="6" class="text-right">
                        <v-btn @click="editField('goalweight')" color="primary">수정</v-btn>
                      </v-col>
                    </v-row>
  
                    <v-row class="mt-2">
                      <v-col cols="6">
                        <div>현재 BMI는:<h2>{{ user.bmi }}</h2> 입니다!</div>
                      </v-col>
   
                    </v-row>
          
                  </v-card-text>
                </v-card>
              </v-col>
              <v-col cols="6">
                <v-card outlined>
                  <v-card-title>
                    오늘 하루도 화이팅!!
                  </v-card-title>
                  <v-card-text>
                    <div>연속 접속</div>
                    <v-row justify="center" class="mt-2">
                      <v-col cols="6">
                        <div class="display-2 text-center">{{ user.streak }}</div>
                      </v-col>
                    </v-row>
                    <div class="text-center">일차에요!!</div>
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
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  import { useUserStore } from "@/stores/user";
  import router from '@/router';
  
  import TheHeaderNav from '@/components/common/TheHeaderNav.vue'

  export default {
  components: {
    TheHeaderNav
  },
  setup() {
    const userStore = useUserStore();
    const user = ref({
      id: '',
      name: '',
      email: '',
      password: '',
      age: 0,
      weight: '',
      goalWeight: '',
      bmi: '',
      streak: 0
    });
    const showModal = ref(false);
    const currentField = ref('');
    const tempValue = ref('');

    onMounted(async () => {
      user.value = await userStore.getUserInfoFromToken();
      console.log(user.value);
    });

    const editField = (field) => {
      currentField.value = field;
      tempValue.value = user.value[field];
      showModal.value = true;
    };

    const saveChanges = () => {
      if (currentField.value) {
        user.value[currentField.value] = tempValue.value;
        currentField.value = '';
        tempValue.value = '';
        showModal.value = false;
      }
    };

    return {
      user,
      showModal,
      currentField,
      tempValue,
      editField,
      saveChanges
    };
  }
};





  // export default {
  //   data() {
  //     return {
  //       user: {
  //         id: 'user123',
  //         name: '홍길동',
  //         email: 'hong@domain.com',
  //         password: '********',
  //         age: 30,
  //         weight: '70kg',
  //         goalWeight: '65kg',
  //         bmi: '22',
  //         streak: 31,
  //       },
  //       showModal: false,
  //       currentField: '',
  //       tempValue: '',
  //       fieldLabels: {
  //         id: 'ID',
  //         name: '이름',
  //         email: 'e-mail',
  //         password: '비밀번호',
  //         age: 'age',
  //         weight: '현재 체중',
  //         goalWeight: '목표 체중',
  //         bmi: 'BMI',
  //         streak: '연속 접속',
  //       },
  //       editableFields: ['name', 'email', 'password', 'age', 'weight', 'goalWeight', 'bmi', 'streak']
  //     };
  //   },
  //   methods: {
  //     editField(field) {
  //       this.currentField = field;
  //       this.tempValue = this.user[field];
  //       this.showModal = true;
  //     },
  //     saveChanges() {
  //       if (this.currentField) {
  //         this.user[this.currentField] = this.tempValue;
  //         this.currentField = '';
  //         this.tempValue = '';
  //         this.showModal = false;
  //       }
  //     }
  //   }
  // };
  </script>
  
  <style>
  .v-avatar img {
    object-fit: cover;
  }

  .userInfo {
    display: flex;
  justify-content: space-around;
  align-items: center;
  }
  </style>