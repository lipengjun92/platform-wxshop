// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import App from './App'
import 'normalize.css';
import components from './components';
import plugin from './plugin';
import {router} from './router'
import './assets/icon/index.css';
import './assets/css/reset.css';
import  { ToastPlugin } from 'vux'
import './service/mock'
import store from './vuex'

Vue.use(components);
Vue.use(plugin);
Vue.use(ToastPlugin)

FastClick.attach(document.body)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app-box')
