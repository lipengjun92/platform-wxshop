import axios from 'axios'
import conf from '../../config'
import {requestInterceptor} from './request-interceptor'
import {responseInterceptor} from './response-interceptor'


axios.defaults.timeout = 10000;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.baseURL = conf.api();


axios.interceptors.request.use((req) => {
	return requestInterceptor(req)
}, (error) => {
	return Promise.reject(error);
});

axios.interceptors.response.use((res) => {
	return responseInterceptor(res)
}, (error) => {
	return Promise.reject(error);
});


export const post = (config) => {
	return axios.post(config.url, config.params)
}
export const get = (config) => {
	return axios.get(config.url, config.params)
}