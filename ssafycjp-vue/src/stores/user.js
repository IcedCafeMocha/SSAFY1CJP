import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import router from '@/router'
import axios from 'axios'
import axiosInstance from '@/utils/interceptor'

const REST_USER_API = `http://localhost:8080/user`

export const useUserStore = defineStore('user', () => {
  const loginUserId = ref(null)
  
  const userLogin = async (id, password) => {
    console.log("로그인 들어왔어!")
    try {
      const response = await axios.post(`${REST_USER_API}/login`, {
        id: id,
        password: password
      })
      sessionStorage.setItem('Authorization', response.data['access-token'])
      router.push({ name: 'boardList' })
    } catch (error) {
      console.error('로그인 실패:', error)
    }
  }


// const userLogin = function (id, password) {
//     console.log("로그인 들어왔어!")
//     axios.post(`${REST_USER_API}/login`, {
//       id: id,
//       password: password
//     })
//       .then((res) => {
//         console.log(res.data)
//         const token = res.data["access-token"]
//         sessionStorage.setItem('Authorization', token)

//         const tokenParts = token.split('.')
//         let id = JSON.parse(atob(tokenParts[1]))['sub']
        
//         loginUserId.value = id

//         router.push({name: 'boardList'})

//       })
//       .catch(() => {
//         console.log('로그인 실패')
//     })
//   }

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
  // const userRegist = function(user) {
  //   console.log('들어와')
  //   console.log(user)
  //   axios({
  //     url: `${REST_USER_API}/user`,
  //     method:'POST',
  //     data:user
  //   })
  //   .then(()=>{
  //     router.push({name:'home'})
  //   })
  //   .catch((err)=>{
  //     console.log(err)
  //   })


  // }

  const logout = function(){
    console.log('로그아웃 테스트')
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
      console.log(token)
      if (token) {
        console.log("토큰이 있네!")
        const response = await axiosInstance.get(`${REST_USER_API}/info`, {
          headers: { Authorization: `Bearer ${token}` }
        })
        return response.data
      }
      throw new Error('토큰이 없습니다.')
    } catch (error) {
      console.error('사용자 정보 가져오기 실패:', error)
    }
  }

  return { userLogin , userImgRegist, loginUserId, userRegist, logout, getUserIdFromToken, getUserInfoFromToken}
})
