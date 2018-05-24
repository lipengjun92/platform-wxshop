export default {
    install(Vue) {
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