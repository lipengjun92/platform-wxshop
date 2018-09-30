// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

// css
import 'normalize.css'
import 'animate.css'
import '@/static/less/index.less'

// libs
import '@/libs/cube-ui'
import '@/components'
import 'amfe-flexible'

// routes
import {router} from '@/router'

// extends
import '@/extends'

// mock
import '@/service/mock'

// logic
import store from '@/logic'

import {
  AjaxPlugin,
  ToastPlugin,
  AlertPlugin
} from 'vux'

// vux插件集合
Vue.use(AjaxPlugin)
Vue.use(ToastPlugin)
Vue.use(AlertPlugin)

Vue.config.productionTip = false

const root = '/platform/api/'
// 定义一个全局混合对象
Vue.mixin({
  data() {
    return {
      api: require('./config/api.js')
    }
  },
  methods: {
    request(url, data = {}, method = "POST", header = "application/x-www-form-urlencoded") {
      let vux = this;
      let config = {
        method: method,
        url: root + url,
        data,
        params: data,
        headers: {
          'Content-Type': header,
          'X-Nideshop-Token': store.getters.token
        }
      }
      if (method == 'post') {
        config.params = ''
      } else {
        config.data = ''
      }
      return new Promise(function (resolve, reject) {
        vux.$http(config)
          .then(res => {
            if (res.status == 200) {

              if (res.data.errno == 401) {
                //需要登录后才可以操作
                vux.$vux.toast.text('请登录后继续')
                // vux.$vux.alert.show({
                //   title: '提示',
                //   content: '请登录后继续',
                //   onHide() {
                //     router.go({name: 'login'})
                //   }
                // })
              } else {
                resolve(res.data);
              }
            } else {
              reject(res.errMsg);
            }
          })
          .catch(e => {
            console.log(e)
          })
      })
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
