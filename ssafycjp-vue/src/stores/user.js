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

  const userRegist = async (user) => {
    try {
      await axios.post(`${REST_USER_API}/signup`, user)
      router.push({ name: 'home' })
    } catch (error) {
      console.error('회원가입 실패:', error)
    }
  }

  const userImgRegist = async (img, userId) => {
    try {
      const formData = new FormData()
      formData.append('userId', userId)
      formData.append('file', img)
  
      await axios({
        url: `${REST_USER_API}/userImg`,
        method: 'POST',
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: formData
      })
    } catch (error) {
      console.error('이미지 저장 실패:', error)
    }
  }

  const logout = function(){
    console.log('로그아웃 시도한 아이디: ' + getUserIdFromToken())
    // 로그아웃 했으니 세션스토리지에 저장된 토큰 제거!
    sessionStorage.removeItem('Authorization')
    sessionStorage.removeItem('hasRefreshed')
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
  }

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

  return { userLogin , userImgRegist, loginUserId, userRegist, logout, getUserIdFromToken, getUserInfoFromToken}
})
