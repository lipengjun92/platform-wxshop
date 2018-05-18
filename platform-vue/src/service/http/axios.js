import axios from 'axios'
import qs from 'qs'
import conf from '../../config/index'


axios.defaults.timeout = 10000;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.baseURL = conf.api();


axios.interceptors.request.use((config) => {
    if (config.method === 'post') {
        config.data = qs.stringify(config.data);
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

axios.interceptors.response.use((res) => {
    return Promise.resolve(res.data)
}, (error) => {
    return Promise.reject(error);
});


export function post(config) {
    return new Promise((resolve, reject) => {
        axios.post(config.url, config.params)
            .then(response => {
                resolve(response);
            }, err => {
                reject(err);
            })
            .catch((error) => {
                reject(error)
            })
    })
}