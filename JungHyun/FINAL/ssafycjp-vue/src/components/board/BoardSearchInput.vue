<template>
    <div class="search-container">
      <div class="input-group">
        <div class="input-group-prepend">
          <button
            class="btn btn-pastel dropdown-toggle"
            type="button"
            id="dropdownMenuButton"
            data-bs-toggle="dropdown"
            aria-haspopup="true"
            aria-expanded="false"
          >
            {{ searchTypeLabel }}
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item" href="#" @click.prevent="setSearchType('title')">제목</a>
            <a class="dropdown-item" href="#" @click.prevent="setSearchType('content')">내용</a>
            <a class="dropdown-item" href="#" @click.prevent="setSearchType('writer')">작성자</a>
          </div>
        </div>
        <input type="text" v-model="searchText" placeholder="검색어를 입력하세요" class="form-control" />
        <button @click="onSearch" class="btn btn-pastel">검색</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  
  const searchText = ref('');
  const searchType = ref('title');
  const searchTypeLabel = computed(() => {
    if (searchType.value === 'title') return '제목';
    if (searchType.value === 'content') return '내용';
    if (searchType.value === 'writer') return '작성자';
    return '검색 유형';
  });
  
  const setSearchType = (type) => {
    searchType.value = type;
  };
  
  const onSearch = () => {
    // 검색 로직 추가
    console.log(`Searching for ${searchText.value} in ${searchType.value}`);
  };
  </script>
  
  <style scoped>
  .search-container {
    display: flex;
    flex-direction: column;
    width: 100%;
  }
  
  .input-group {
    display: flex;
  }
  
  .input-group-prepend {
    margin-right: 5px;
  }
  
  .form-control {
    flex: 1;
  }
  
  .dropdown-toggle::after {
    margin-left: 0.5em;
  }
  
  .btn-pastel {
    background-color: #c3fcfc; 
    border-color: #c3fcfc; 
    color: #000;
  }
  
  .btn-pastel:hover {
    background-color: #c3fcfc;
    border-color: #c3fcfc;
    color: #000;
  }
  </style>
  