import Vue from 'vue'
import App from './App'
import store from './store'
Vue.config.productionTip = false
// #ifdef H5 
window.QQmap = null;
// #endif
// #ifndef MP-TOUTIAO
//网络监听
setTimeout(() => {
	uni.onNetworkStatusChange(function(res) {
		// console.log(res.networkType);
		store.commit("networkChange", {
			isConnected: res.isConnected
		})
	});
}, 100)
// #endif

Vue.prototype.$eventHub = Vue.prototype.$eventHub || new Vue()
Vue.prototype.$store = store
App.mpType = 'app'

const app = new Vue({
	store,
	...App
})
app.$mount()
