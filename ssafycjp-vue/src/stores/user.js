import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import router from '@/router'
import axios from 'axios'
import axiosInstance from '@/utils/interceptor'

const REST_USER_API = `http://localhost:8080/user`

export const useUserStore = defineStore('user', () => {
  const loginUserId = ref(null)

  // 로그인 
  const userLogin = async (id, password) => {
    try {
      const response = await axios.post(`${REST_USER_API}/login`, {
        id: id,
        password: password
      })
      sessionStorage.setItem('Authorization', response.data['access-token'])
      router.push({ name: 'main' })
    } catch (error) {
      console.error('로그인 실패:', error)
      router.push({ name: 'FailedWithLogin' })
    }
  }
  // 회원 가입
  const userRegist = async (user) => {
    try {
      await axios.post(`${REST_USER_API}/signup`, user)
      router.push({ name: 'home' })
    } catch (error) {
      console.error('회원가입 실패:', error)
    }
  }

  // 회원 가입 시 이미지 처음 저장
  const userImgRegist = async (img, userId) => {
    try {
      const formData = new FormData();
      formData.append('userId', userId);
      formData.append('file', img);
  
      await axios({
        url: `${REST_USER_API}/userImg`,
        method: 'POST',
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: formData
      });
    } catch (error) {
      console.error('이미지 저장 실패:', error);
    }
  };
// 회원 정보 수정
const saveInfoChanges = async (userId,field,changeValue) => {
  try{
    const token = sessionStorage.getItem('Authorization')
    if (token) {
      const data = {
        userId: userId,
        field: field,
        changeValue: changeValue
      };
      const response = await axiosInstance({
        url: `${REST_USER_API}/changeInfo`,
        method: 'PUT',
        headers: { 
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        data: JSON.stringify(data)
      });
      alert('정보가 수정되버렸지 모야');
} else {
  throw new Error('토큰이 없습니다.')
}
  } catch(err){
    console.log(err)
  }
};

// 회원 프로필 사진 수정
const updateUserProfileImage = async (userId, imageFile) => {
  try {
      const formData = new FormData();
      formData.append('userId', userId);
      formData.append('file', imageFile);

      await axiosInstance({
          url: `${REST_USER_API}/updateProfileImage`,
          method: 'PUT',
          headers: {
              'Content-Type': 'multipart/form-data'
          },
          data: formData
      });
      alert('Profile image updated successfully');
  } catch (error) {
      console.error('Failed to update profile image:', error);
  }
};

// UserId를 통해 프로필 이미지 가져오기
const fetchProfileImageUrl = async (userId) => {
try {
    const response = await axiosInstance.get(`${REST_USER_API}/profileImage/${userId}`);
    if (response.status === 200) {
      console.log(response.data)
        return response.data;  // 이미지 파일의 경로를 반환

    } else {
        throw new Error('Image not found');
    }
} catch (error) {
    console.error('Error fetching profile image:', error);
    return '/path/to/default/image.jpg';  // 기본 이미지 경로
}
};

  // 로그아웃
  const logout = function(){
    console.log('로그아웃 시도한 아이디: ' + getUserIdFromToken())
    // 로그아웃 했으니 세션스토리지에 저장된 토큰 제거!
    sessionStorage.removeItem('Authorization')
    // 로그인 아이디도 초기화!
    loginUserId.value = null
    console.log('로그아웃 성공!')
    router.push({name: 'home'})
  }

  // 아예 세션의 토큰에서 id 가져오도록 변경!
  const getUserIdFromToken = () => {
    const token = sessionStorage.getItem('Authorization')
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]))
      return payload.sub
    }
    return null
  };

  // const getUserIdFromToken = (token) => {
  //   const payloadBase64Url = token.split('.')[1];
  //   const payloadBase64 = payloadBase64Url.replace(/-/g, '+').replace(/_/g, '/');
  //   const payloadJson = atob(payloadBase64);
  //   const payload = JSON.parse(payloadJson);
  //   return payload.sub; 
  // };

  // 토큰 이용해서 유저 정보 가져오기!
  const getUserInfoFromToken = async () => {
    try {
      const token = sessionStorage.getItem('Authorization')
      if (token) {
        const response = await axiosInstance.get(`${REST_USER_API}/info`, {
          headers: { Authorization: `Bearer ${token}` }
        })
        return response.data
      }
      router.push({ name: 'Error401' })
      throw new Error('토큰이 없습니다.')
    } catch (error) {
      console.error('사용자 정보 가져오기 실패:', error)
    }
  }

  return { userLogin , userImgRegist,fetchProfileImageUrl, updateUserProfileImage, saveInfoChanges,loginUserId, userRegist, logout, getUserIdFromToken, getUserInfoFromToken}})
