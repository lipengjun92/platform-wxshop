export default {
    api: function () {
        return this.mock.status ? '//127.0.0.1/mock' : (process.env.NODE_ENV === 'development' ? '//47.100.0.48/admin/' : '//api.xxx.com')
    },
    mock: {
        api: '//127.0.0.1/mock',
        status: false
    }
}