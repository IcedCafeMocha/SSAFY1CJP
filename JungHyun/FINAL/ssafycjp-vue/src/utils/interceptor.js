import axios from 'axios';
import router from '@/router';

const instance = axios.create({
  baseURL: 'http://localhost:8080',
});

instance.interceptors.request.use(
  config => {
    const token = sessionStorage.getItem('Authorization');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
    response => response,
    error => {
      if (error.response && error.response.status === 401) {
        router.push({ name: 'Error401' });
      }
      return Promise.reject(error);
    }
  );

export default instance;