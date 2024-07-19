import Vue from 'vue'
import App from '@/App'
// api: https://github.com/vuejs/vue-router
import router from '@/router'
// api: https://github.com/vuejs/vuex
import store from '@/store'
// api: https://github.com/alfhen/vue-cookie
import VueCookie from 'vue-cookie'
// api: https://github.com/ElemeFE/element
import '@/element-ui'
// api: http://www.iconfont.cn/
import '@/icons'
import '@/element-ui-theme'
import '@/assets/scss/index.scss'
// api: https://github.com/axios/axios
import httpRequest from '@/utils/httpRequest'
import baiduAnalytics from 'vue-baidu-analytics'
import VueClipboard from 'vue-clipboard2'

import {isAuth, treeDataTranslate, transOrg, transUser, transDict, openImg, transDate, subtractDate} from '@/utils'
import cloneDeep from 'lodash/cloneDeep'
import ElDict from './components/el-dict'
import ElImg from './components/el-img'
import Ueditor from './components/ueditor'
// 引入echarts
const echarts = require('echarts')

Vue.use(VueCookie)
Vue.use(ElDict)
Vue.use(ElImg)
Vue.use(Ueditor)
Vue.use(VueClipboard)

Vue.component('el-dict', ElDict)
Vue.component('el-img', ElImg)
Vue.component('ueditor', Ueditor)

Vue.config.productionTip = false

// 挂载全局

// ajax请求方法
Vue.prototype.$http = httpRequest
Vue.prototype.$echarts = echarts
// 权限方法
Vue.prototype.isAuth = isAuth
// 树形数据转换
Vue.prototype.treeDataTranslate = treeDataTranslate
// 机构翻译
Vue.prototype.transOrg = transOrg
// 用户翻译
Vue.prototype.transUser = transUser
// 数据字典翻译
Vue.prototype.transDict = transDict
// 预览图片
Vue.prototype.openImg = openImg
// 转时间
Vue.prototype.transDate = transDate
Vue.prototype.subtractDate = subtractDate

// 保存整站vuex本地储存初始状态
window.SITE_CONFIG.storeState = cloneDeep(store.state)

Vue.use(baiduAnalytics, {
  router: router,
  siteIdList: [
    '556c145b863b7d96bfcb4d8aad246054'
  ],
  isDebug: false
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
