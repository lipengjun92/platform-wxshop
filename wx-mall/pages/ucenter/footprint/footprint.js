var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');



var app = getApp();

Page({
  data: {
    footprintList: [],
  },
  onPullDownRefresh(){
    // 增加下拉刷新数据的功能
    var self = this;
    this.getFootprintList();
  },
  getFootprintList() {
    let that = this;
    var tmpFootPrint;
    util.request(api.FootprintList).then(function (res) {
      if (res.errno === 0) {

        if (res.data.data != undefined){
          tmpFootPrint = res.data.data;
        } else {
          tmpFootPrint = [];
        }
       
        that.setData({
          footprintList: tmpFootPrint 
        });
      }
    });
  },
  deleteItem (event){
    let that = this;
    let footprint = event.currentTarget.dataset.footprint;
    var touchTime = that.data.touch_end - that.data.touch_start;
    //如果按下时间大于350为长按
    if (touchTime > 350) {
      wx.showModal({
        title: '',
        content: '要删除所选足迹？',
        success: function (res) {
          if (res.confirm) {
            util.request(api.FootprintDelete, { footprintId: footprint.id }).then(function (res) {
              if (res.errno === 0) {
                wx.showToast({
                  title: res.errmsg,
                  icon: 'success',
                  duration: 2000,
                  complete:function(){
                    that.getFootprintList();
                  }
                });                
              } else{
                util.showErrorToast(res.errmsg);
              }
            });
          }
        }
      });
    } else {
      wx.navigateTo({
        url: '/pages/goods/goods?id=' + footprint.goods_id,
      });
    }
    
  },
  onLoad: function (options) {
    this.getFootprintList();
  },
  onReady: function () {

  },
  onShow: function () {

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭
  },
  //按下事件开始  
  touchStart: function (e) {
    let that = this;
    that.setData({
      touch_start: e.timeStamp
    })
  },
  //按下事件结束  
  touchEnd: function (e) {
    let that = this;
    that.setData({
      touch_end: e.timeStamp
    })
  },
})