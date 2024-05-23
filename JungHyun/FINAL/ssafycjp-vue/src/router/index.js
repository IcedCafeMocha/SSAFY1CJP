import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SignupView from '@/views/SignupView.vue'
import MyPageView from '@/views/MyPageView.vue'
import MainView from '@/views/MainView.vue'

import BoardCreate from '@/components/board/BoardCreate.vue'
import BoardList from '@/components/board/BoardList.vue'
import BoardUpdate from '@/components/board/BoardUpdate.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'

import Error401 from '@/views/Error401.vue';
import FailedWithLogin from '@/views/FailedWithLogin.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView
    },
    {
      path: '/401',
      name: 'Error401',
      component: Error401
    },
    {
      path: '/FailedWithLogin',
      name: 'FailedWithLogin',
      component: FailedWithLogin
    },
    {
      path: '/main',
      name: 'main',
      component: MainView,
      children: [
        { 
          path: '', 
          name: 'allBoardList', 
          component: BoardList,
        },
        { 
          path: ':postboardId', 
          name: 'boardList', 
          component: BoardList,
        },
        { 
          path: ':postboardId/create',
          name: 'boardCreate', 
          component: BoardCreate 
        },
        { 
          path: 'update/:id', 
          name: 'boardUpdate', 
          component: BoardUpdate 
        },
        { 
          path: 'detail/:id', 
          name: 'boardDetail', 
          component: BoardDetail 
        },
      ],
    },
    // {
    //   path: '/board',
    //   name: 'board',
    //   component: BoardView,
    //   children: [
    //     {
    //       path: '',
    //       name: 'boardList',
    //       component: BoardList
    //     },
    //     {
    //       path: 'create',
    //       name: 'boardCreate',
    //       component: BoardCreate
    //     },
    //     {
    //       path: 'update',
    //       name: 'boardUpdate',
    //       component: BoardUpdate
    //     },
    //     {
    //       path: ':id',
    //       name: 'boardDetail',
    //       component: BoardDetail
    //     },
    //   ]
    // },
  ]
})

// 라우터 가드
// 유효 토큰 검증해서 로그인 되어 있는 상태면 로그인 페이지로 가지 못하게끔 한다.
router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem('Authorization');
  if (to.name === 'home' && token) {
    next({ name: 'main' });
  } else {
    next();
  }
});

export default router
