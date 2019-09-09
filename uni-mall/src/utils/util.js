
const API_BASE_URL = 'https://fly2you.cn/platform/api/';
/**
 * 封封微信的的request
 */
function request(url, data = {}, method = "POST", header = "application/x-www-form-urlencoded") {
    uni.showLoading({
        title: '加载中...',
    });
    return new Promise(function (resolve, reject) {
        uni.request({
            url: API_BASE_URL + url,
            data: data,
            method: method,
            header: {
                'Content-Type': header,
                'X-Nideshop-Token': uni.getStorageSync('token')
            },
            success: function (res) {
                uni.hideLoading();
                if (res.statusCode == 200) {

                    if (res.data.errno == 401) {
                        uni.navigateTo({
                            url: '/pages/auth/btnAuth/btnAuth',
                        })
                    } else {
                        resolve(res.data);
                    }
                } else {
                    reject(res.errMsg);
                }

            },
            fail: function (err) {
                reject(err)
            }
        })
    });
}

/**
 * 调用微信登录
 */
function login() {
    return new Promise(function (resolve, reject) {
        uni.login({
            success: function (res) {
                if (res.code) {
                    resolve(res);
                } else {
                    reject(res);
                }
            },
            fail: function (err) {
                reject(err);
            }
        });
    });
}

function showErrorToast(msg) {
    uni.showToast({
        title: msg,
        image: '/static/images/icon_error.png'
    })
}

function showSuccessToast(msg) {
    uni.showToast({
        title: msg,
    })
}

module.exports = {
    request,
    login,
    showErrorToast,
    showSuccessToast,
}


