// 独立分包内的轻量请求工具，地址与主包 config/api.js 保持一致。
const API_BASE_URL = wx.getStorageSync('apiBaseUrl')

function agentRequest(url, data = {}, method = 'POST') {
  return new Promise((resolve, reject) => {
    wx.request({
      url: API_BASE_URL + url,
      data,
      method,
      header: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'token': wx.getStorageSync('token') || ''
      },
      success(res) {
        if (res.statusCode !== 200) {
          reject(new Error('服务器开小差了，请稍后再试'))
          return
        }
        if (!res.data || res.data.code !== 0) {
          reject(new Error((res.data && res.data.msg) || '请求失败'))
          return
        }
        resolve(res.data.data)
      },
      fail() {
        reject(new Error('网络不给力，请稍后再试'))
      }
    })
  })
}

function buildSuccess(contentText, data, meta) {
  const result = {
    content: [{ type: 'text', text: contentText }],
    structuredContent: data || {}
  }
  if (meta && Object.keys(meta).length) {
    result._meta = meta
  }
  return result
}

function buildError(message) {
  return {
    isError: true,
    content: [{ type: 'text', text: message }]
  }
}

module.exports = { agentRequest, buildSuccess, buildError }
