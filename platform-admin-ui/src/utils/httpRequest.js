import Vue from 'vue'
import axios from 'axios'
import router from '@/router'
import merge from 'lodash/merge'
import {clearLoginInfo} from '@/utils'
import {
  Message,
  Loading
} from 'element-ui'

// 超时时间
axios.defaults.timeout = 30000
// 跨域请求，允许保存cookie
axios.defaults.withCredentials = true
axios.defaults.headers = {'Content-Type': 'application/json; charset=utf-8'}
// 非生产环境 && 开启代理, 接口前缀统一使用[/platform]前缀做代理拦截!
const BASE_URL = process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/platform' : window.SITE_CONFIG.baseUrl
// 对面暴露的基础请求路径
axios.BASE_URL = BASE_URL

/**
 * 请求拦截
 */
let loading
let showError = true
axios.interceptors.request.use(config => {
  let showLoading = true
  if (config.loading === false) {
    showLoading = false
  }
  if (config.showError === false) {
    showError = false
  }
  if (showLoading) {
    loading = Loading.service({
      text: 'Loading...',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }
  // 请求头带上token
  config.headers['token'] = Vue.cookie.get('token') || ''
  // 请求地址处理
  config.url = BASE_URL + config.url
  const type = config.method
  const defaults = {}
  if (type === 'post') {
    // post请求参数处理
    // json: 'application/json; charset=utf-8'
    // form: 'application/x-www-form-urlencoded; charset=utf-8'
    if (config.headers['Content-Type'] !== 'multipart/form-data') {
      config.data = JSON.stringify(config.data)
    }
  } else if (type === 'get') {
    // get请求参数处理
    config.params = merge(defaults, config.params)
  }
  return config
}, error => {
  return Promise.reject(error)
})

/**
 * 响应拦截
 */
axios.interceptors.response.use(response => {
  loading.close()
  // 401, token失效
  if (response.data && response.data.code === 401) {
    clearLoginInfo()
    router.push({name: 'login'})
  }
  if (!(response.data && response.data.code === 0)) {
    if (showError) {
      Message({
        message: response.data.msg,
        type: 'error',
        showClose: true,
        dangerouslyUseHTMLString: true,
        duration: 5000
      })
    }
  }
  return response
}, error => {
  loading.close()
  Message({
    message: '网络异常，请稍后重试',
    type: 'error',
    showClose: true,
    duration: 3000
  })
  return Promise.reject(error)
})

export default axios
