const customizedReturn = function (success, data, msg, code) {
    return {
        success: success,
        data: data,
        msg: msg,
        code: code
    }
}

export default {
    userLogin() {
        return customizedReturn(true, {
            token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MjE3NzE0MDEsImV4cCI6MTUyNDM2MzQwMSwiZGF0YSI6eyJ1c2VyX2lkIjoxMX19.Np56XU0S_b5NI54DwRdaJA-1bWkYX2RVzLFRlyXA1uA'
        }, '登录成功', 10000)
    }
}