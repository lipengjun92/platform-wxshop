var util = require('../../../utils/util.js');
var user = require('../../../services/user.js');
var app = getApp();
var DEFAULT_AVATAR = 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png';

Page({
  data: {
    userInfo: {},
    hasMobile: '',
    avatarUrl: DEFAULT_AVATAR,
    nicknameText: 'Hi, 游客',
    menuItems: [
      { title: '我的订单', url: '/pages/ucenter/order/order', badge: '单' },
      { title: '优惠券', url: '/pages/ucenter/coupon/coupon', badge: '券' },
      { title: '我的收藏', url: '/pages/ucenter/collect/collect', badge: '藏' },
      { title: '我的足迹', url: '/pages/ucenter/footprint/footprint', badge: '迹' },
      { title: '地址管理', url: '/pages/ucenter/address/address', badge: '址' },
      { title: '帮助中心', url: '/pages/ucenter/help/help', badge: '助' },
      { title: '意见反馈', url: '/pages/ucenter/feedback/feedback', badge: '评' }
    ]
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数

  },
  onReady: function () {

  },
  onShow: function () {

    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');

    // 页面显示
    if (userInfo && token) {
      app.globalData.userInfo = userInfo;
      app.globalData.token = token;
    }

    this.refreshUserInfo();

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭
  },
  getUserProfile() {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    if (userInfo && token) {
      app.globalData.userInfo = userInfo;
      app.globalData.token = token;
      this.setData({
        userInfo: app.globalData.userInfo,
        avatarUrl: app.globalData.userInfo.avatar || DEFAULT_AVATAR,
        nicknameText: app.globalData.userInfo.nickname || 'Hi, 游客'
      });
      return;
    }
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
    wx.getUserProfile({
      desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (resp) => {
        //登录远程服务器
        user.loginByWeixin(resp).then(res => {
          this.setData({
            userInfo: res.data.userInfo,
            avatarUrl: res.data.userInfo.avatar || DEFAULT_AVATAR,
            nicknameText: res.data.userInfo.nickname || 'Hi, 游客'
          });
          app.globalData.userInfo = res.data.userInfo;
          app.globalData.token = res.data.token;
        }).catch((err) => {
          console.log(err)
        });
      }
    })
  },
  bindGetUserInfo(e) {
    let userInfo = wx.getStorageSync('userInfo');
    let token = wx.getStorageSync('token');
    if (userInfo && token) {
      return;
    }
    //用户按了允许授权按钮
    user.loginByWeixin(e.detail).then(res => {
      this.setData({
        userInfo: res.data.userInfo,
        avatarUrl: res.data.userInfo.avatar || DEFAULT_AVATAR,
        nicknameText: res.data.userInfo.nickname || 'Hi, 游客'
      });
      app.globalData.userInfo = res.data.userInfo;
      app.globalData.token = res.data.token;
    }).catch((err) => {
      console.log(err)
    });
  },
  exitLogin: function () {
    wx.showModal({
      title: '',
      confirmColor: '#b4282d',
      content: '退出登录？',
      success: function (res) {
        if (res.confirm) {
          wx.removeStorageSync('token');
          wx.removeStorageSync('userInfo');
          app.globalData.userInfo = {
            nickname: 'Hi, 游客',
            userName: '点击去登录',
            avatar: DEFAULT_AVATAR
          };
          util.showSuccessToast('退出成功');
          wx.switchTab({
            url: '/pages/index/index'
          });
        }
      }
    })
  },
  refreshUserInfo: function () {
    var userInfo = app.globalData.userInfo || {};
    this.setData({
      userInfo: userInfo,
      avatarUrl: userInfo.avatar || DEFAULT_AVATAR,
      nicknameText: userInfo.nickname || 'Hi, 游客'
    });
  }
})
