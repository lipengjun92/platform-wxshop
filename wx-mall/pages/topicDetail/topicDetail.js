var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  data: {
    id: 0,
    topic: {},
    topicList: [],
    commentCount: 0,
    commentList: []
  },onShareAppMessage: function() {//自定义函数 用户点击右上角分享给好友,要先在分享好友这里设置menus的两个参数,才可以分享朋友圈 
    wx.showShareMenu({ 
        withShareTicket: true, 
        menus: ['shareAppMessage', 'shareTimeline'] 
      }) 
    return { 
        title: this.data.topic.title, 
        desc: this.data.topic.content, 
        imageUrl: this.data.topic.scene_pic_url, 
        path: '/pages/topicDetail/topicDetail?id='+this.data.id 
    } 
  },onShareTimeline: function () {//用户点击右上角分享朋友圈 
  return { 
      title: this.data.topic.title, 
      query: { 
        id:this.data.id 
      }, 
      imageUrl: this.data.topic.scene_pic_url 
    } 
  },onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    var that = this;
    that.setData({
      id: parseInt(options.id)
    });

    util.request(api.TopicDetail, { id: that.data.id}).then(function (res) {
      if (res.errno === 0) {

        that.setData({
          topic: res.data,
        });

        WxParse.wxParse('topicDetail', 'html', res.data.content, that);
      }
    });

    util.request(api.TopicRelated, { id: that.data.id}).then(function (res) {
      if (res.errno === 0) {

        that.setData({
          topicList: res.data
        });
      }
    });
  },
  getCommentList(){
    let that = this;
    util.request(api.CommentList, { valueId: that.data.id, typeId: 1, size: 5 }).then(function (res) {
      if (res.errno === 0) {

        that.setData({
          commentList: res.data.data,
          commentCount: res.data.count
        });
      }
    });
  },
  postComment (){
    wx.navigateTo({
      url: '/pages/commentPost/commentPost?valueId='+this.data.id + '&typeId=1',
    })
  },
  onReady: function () {

  },
  onShow: function () {
    // 页面显示
    this.getCommentList();
  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})