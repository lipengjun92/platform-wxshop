var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../services/user.js');
var app = getApp();

Page({
    data: {
        canIUseGetUserProfile: false,
        userInfo: {},
        hasMobile: ''
    },
    onLoad: function (options) {
        // 页面初始化 options为页面跳转所带来的参数
        if (wx.getUserProfile) {
            this.setData({
                canIUseGetUserProfile: true
            })
        }
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

        this.setData({
            userInfo: app.globalData.userInfo,
        });

    },
    onHide: function () {
        // 页面隐藏

    },
    onUnload: function () {
        // 页面关闭
    },
    getUserProfile() {
        // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
        // 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
        wx.getUserProfile({
            desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
            success: (resp) => {
                //登录远程服务器
                user.loginByWeixin(resp).then(res => {
                    this.setData({
                        userInfo: res.data.userInfo
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
                userInfo: res.data.userInfo
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
                    wx.switchTab({
                        url: '/pages/index/index'
                    });
                }
            }
        })

    }
})