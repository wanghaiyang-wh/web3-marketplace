import axios from 'axios'
import { useUserStore } from '@/stores/user'

const baseURL = import.meta.env.VITE_API_URL || ''

const request = axios.create({
  baseURL,
  timeout: 10000
})

request.interceptors.request.use(
  (config) => {
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code !== 0) {
      const errorMsg = res.message || res.msg || '请求失败'
      console.error('API Error:', errorMsg)
      return Promise.reject(new Error(errorMsg))
    }
    return res.data
  },
  (error) => {
    const errorMsg = error.response?.data?.message || error.message || '网络请求失败'
    console.error('Request Error:', errorMsg)
    return Promise.reject(new Error(errorMsg))
  }
)

export default request
