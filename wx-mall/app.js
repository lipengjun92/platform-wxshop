var util = require('./utils/util.js');
var api = require('./config/api.js');
var user = require('./services/user.js');

App({
  onLaunch: function () {
    //获取用户的登录信息
    user.checkLogin().then(res => {
      console.log('app login')
      this.globalData.userInfo = wx.getStorageSync('userInfo');
      this.globalData.token = wx.getStorageSync('token');
    }).catch(() => {
      
    });
  },
  
  globalData: {
    userInfo: {
      nickName: 'Hi,游客',
      userName: '点击去登录',
      avatarUrl: 'http://p9kyr79ne.bkt.clouddn.com/1/20180531/150547696d798c.png'
    },
    token: '',
  }
})