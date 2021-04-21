const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');

//获取应用实例
const app = getApp()
Page({
  data: {
    canIUseGetUserProfile: false,
    navUrl: '',
    code: ''
  },

  onLoad: function (options) {
    let that = this;
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
    if (wx.getStorageSync("navUrl")) {
      that.setData({
        navUrl: wx.getStorageSync("navUrl")
      })
    } else {
      that.setData({
        navUrl: '/pages/index/index'
      })
    }

    wx.login({
      success: function (res) {
        if (res.code) {
          that.setData({
            code: res.code
          })
        }
      }
    });
  },
  getUserProfile() {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (resp) => {
        //登录远程服务器
        this.loginByWeixin(resp)
      }
    })
  },
  bindGetUserInfo: function (e) {
    //登录远程服务器
    this.loginByWeixin(e.detail)
  },
  loginByWeixin: function (userInfo) {
    let that = this;
    if (that.data.code) {
      util.request(api.AuthLoginByWeixin, {
        code: that.data.code,
        userInfo: userInfo
      }, 'POST', 'application/json').then(res => {
        if (res.errno === 0) {
          //存储用户信息
          wx.setStorageSync('userInfo', res.data.userInfo);
          wx.setStorageSync('token', res.data.token);
          wx.setStorageSync('userId', res.data.userId);

        } else {
          // util.showErrorToast(res.errmsg)
          wx.showModal({
            title: '提示',
            content: res.errmsg,
            showCancel: false
          });
        }
      });
    }
    if (that.data.navUrl && that.data.navUrl == '/pages/index/index') {
      wx.switchTab({
        url: that.data.navUrl,
      })
    } else if (that.data.navUrl) {
      wx.redirectTo({
        url: that.data.navUrl,
      })
    }
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})