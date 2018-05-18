export default {
    api: function () {
        return this.mock.status ? '//127.0.0.1/mock' : (process.env.NODE_ENV === 'development' ? '//127.0.0.1:8010' : '//api.xxx.com')
    },
    mock: {
        api: '//127.0.0.1/mock',
        status: true
    }
}