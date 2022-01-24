'use strict'

import axios from 'axios'
import store from '@/store'
import router from '@/router'
// import vueInstance from '@/main'

const openMock = process.env.VUE_APP_OPEN_MOCK === 'true'

const _axios = axios.create({
  // 若跨域请求需要带 cookie 身份识别
  withCredentials: true,
  // 设置超时时间
  timeout: 60000,
  // 基础url，会在请求url中自动添加前置链接
  baseURL: openMock ? null : '/api',
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Accept: 'application/json'
  }
})

// 请求拦截器
_axios.interceptors.request.use(
  config => {
    const token = store.getters['user/token']
    if (token) {
      // 请求头部添加token
      config.headers.Authorization = token
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器
_axios.interceptors.response.use(
  response => {
    log(response)
    const code = response.status
    if ((code >= 200 && code < 300) || code === 304) {
      return Promise.resolve(response.data)
    }
    return Promise.reject(response)
  },
  error => {
    log(error)
    if (error.response) {
      switch (error.response.status) {
        case 400:
          error.message = '请求错误!'
          break
        case 401:
          error.message = '未授权，请登录!'
          // 返回401 清除token信息并跳转到登陆页面
          store.commit('user/RESET_INFO')
          router.replace({
            path: '/index',
            query: { redirect: router.currentRoute.fullPath }
          })
          break
        case 403:
          error.message = '拒绝访问!'
          break
        case 404:
          error.message = `请求地址出错: ${error.response.config.url}!`
          break
        case 408:
          error.message = '请求超时!'
          break
        case 500:
          error.message = '服务器内部错误!'
          break
        case 501:
          error.message = '服务未实现!'
          break
        case 502:
          error.message = '网关错误!'
          break
        case 503:
          error.message = '服务不可用!'
          break
        case 504:
          error.message = '网关超时!'
          break
        case 505:
          error.message = 'HTTP版本不受支持!'
          break
        default:
          break
      }
    } else {
      // 请求超时或者网络有问题
      if (error.message.includes('timeout')) {
        error.message = '请求超时！请检查网络是否正常.'
      } else {
        error.message = '请求失败!'
      }
    }
    // vueInstance.$msg.error(error.message)
    return Promise.reject(error)
  }
)

function log (response) {
  if (openMock) {
    window.console.log({ url: response.config.url, request: JSON.parse(response.config.data || '{}'), response: response.data })
  }
}

export default _axios
