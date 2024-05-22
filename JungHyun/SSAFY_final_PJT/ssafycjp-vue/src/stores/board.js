import { ref } from 'vue'
import { defineStore } from 'pinia'
import axiosInstance from '@/utils/interceptor'
import router from '@/router'

const REST_BOARD_API = `http://localhost:8080/api-board/board`

export const useBoardStore = defineStore('board', () => {
  const postboardNames = ref([])
  const categories = ref([])

  const createBoard = async function (board) {
    try {
      const token = sessionStorage.getItem('Authorization');
      await axiosInstance({
        url: REST_BOARD_API,
        method: 'POST',
        headers: {
          'Authorization': token,
        },
        data: board
      });
      console.log("board.postboardId: "+board.postboardId)
      router.push({ name: 'boardList', params: { postboardId: board.postboardId } });
    } catch (err) {
      console.log(err);
    }
  }

  // 게시판 목록 가져오기
  const fetchPostboardNames = async () => {
    try {
      const response = await axiosInstance.get(`${REST_BOARD_API}/postboardnames`)
      postboardNames.value = response.data
      console.log("DB에서 게시판 이름 목록 가져왔어!")
      console.log(postboardNames.value)
    } catch (error) {
      console.error('게시판 이름을 가져오는 데 실패했습니다:', error)
    }
  }

  // 특정 게시판의 카테고리 목록 가져오기
  const fetchCategoriesByPostBoardId = async (postBoardId) => {
    try {
      const token = sessionStorage.getItem('Authorization')
      const response = await axiosInstance.get(`${REST_BOARD_API}/categories/${postBoardId}`, {
        headers: {
          Authorization: token
        }
      })
      categories.value = response.data
      console.log("DB에서 카테고리 목록 가져왔어!", categories.value)
    } catch (error) {
      console.error('카테고리를 가져오는 데 실패했습니다:', error)
    }
  }

  // 게시글 가져오기
  const boardList = ref([])
  const getBoardList = function (postboardId = null) {
    let url = REST_BOARD_API
    if (postboardId === null) {
      // 전체 게시글 조회
      url = REST_BOARD_API
    }
    if (postboardId) {
      // 특정 게시판 게시글 조회
      url += `/${postboardId}`
    }
    axiosInstance.get(url)
      .then((response) => {
        boardList.value = response.data
      })
  }

  const board = ref({})

  // 게시글 상세 보기
  const getBoard = function (id) {
    axiosInstance.get(`${REST_BOARD_API}/detail/${id}`)
      .then((response) => {
        board.value = response.data
      })
  }

  // 게시글 수정
  const updateBoard = async function () {
    try {
      await axiosInstance.put(`${REST_BOARD_API}/${board.value.id}`, board.value)
      router.back()
    } catch (error) {
      console.error('게시글을 수정하는 데 실패했습니다:', error)
    }
  }

  // 게시글 검색
  const searchBoardList = function (searchCondition) {
    axiosInstance.get(REST_BOARD_API, {
      params: searchCondition
    })
      .then((res) => {
        boardList.value = res.data
      })
  }

  // 게시글 삭제
  const deleteBoard = function (id) {
    return axiosInstance.delete(`${REST_BOARD_API}/${id}`)
  }

  // 댓글 가져오기
  const getReplies = async (boardId) => {
    const response = await axiosInstance.get(`${REST_BOARD_API}/${boardId}/reply`)
    return response.data
  }

  // 댓글 작성
  const addReply = async (boardId, content) => {
    await axiosInstance.post(`${REST_BOARD_API}/${boardId}/reply`, { content })
  }

  // 댓글 삭제
  const deleteReply = async (replyId) => {
    await axiosInstance.delete(`${REST_BOARD_API}/reply/${replyId}`)
  }

  // 좋아요 증가
  const likeBoard = async (boardId) => {
    try {
      const token = sessionStorage.getItem('Authorization')
      await axiosInstance.put(`${REST_BOARD_API}/${boardId}/like`, { token })
    } catch (error) {
      console.error('게시글에 좋아요를 누르는 데 실패했습니다:', error)
    }
  }

  // 좋아요 취소
  const dislikeBoard = async (boardId) => {
    try {
      const token = sessionStorage.getItem('Authorization')
      await axiosInstance.put(`${REST_BOARD_API}/${boardId}/dislike`, { token })
    } catch (error) {
      console.error('게시글에 좋아요를 취소하는 데 실패했습니다:', error)
    }
  }

  // 사용자의 게시글 좋아요 여부 확인
  const checkIfUserLikedBoard = async (boardId) => {
    try {
        const token = sessionStorage.getItem('Authorization');
        const response = await axiosInstance.get(`${REST_BOARD_API}/${boardId}/liked`, { headers: { Authorization: token } });
        return response.data;
    } catch (error) {
        console.error('좋아요 상태를 확인하는 데 실패했습니다:', error);
        return false;
    }
  }

  return {
    createBoard,
    boardList,
    getBoardList,
    board,
    getBoard,
    updateBoard,
    searchBoardList,
    postboardNames,
    fetchPostboardNames,
    deleteBoard,
    getReplies,
    addReply,
    deleteReply,
    likeBoard,
    dislikeBoard,
    fetchCategoriesByPostBoardId,
    checkIfUserLikedBoard,
    categories,
  }
})
