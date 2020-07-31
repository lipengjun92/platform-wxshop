import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		//app版本
		version: "1.0.0",
		//当前是否有网络连接
		networkConnected: true
	},
	mutations: {
		networkChange(state, payload) {
			state.networkConnected = payload.isConnected
		}
	}
})

export default store
