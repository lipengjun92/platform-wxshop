import http from '../service/http'

export default {
    install(Vue) {
        Vue.prototype.$http = http;

        Vue.mixin({
            methods: {
                goto(value) {
                    this.$router.push(value);
                },
                back() {
                    this.$router.go(-1);
                }
            }
        })
    }
}