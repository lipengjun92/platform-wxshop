Component({
  options: {
    styleIsolation: 'apply-shared'
  },
  externalClasses: ['ext-class'],
  properties: {
    context: {
      type: String,
      value: ''
    },
    text: {
      type: String,
      value: 'AI 助手'
    },
    bottom: {
      type: String,
      value: '140rpx'
    },
    right: {
      type: String,
      value: '20rpx'
    },
    left: {
      type: String,
      value: ''
    },
    top: {
      type: String,
      value: ''
    },
    zIndex: {
      type: String,
      value: '99'
    }
  },
  data: {
    supported: false,
    containerStyle: ''
  },
  lifetimes: {
    attached() {
      this.setData({
        supported: !!wx.openAgent
      });
      this.syncContainerStyle();
    }
  },
  observers: {
    bottom() {
      this.syncContainerStyle();
    },
    right() {
      this.syncContainerStyle();
    },
    left() {
      this.syncContainerStyle();
    },
    top() {
      this.syncContainerStyle();
    },
    zIndex() {
      this.syncContainerStyle();
    }
  },
  methods: {
    syncContainerStyle() {
      const style = [];
      if (this.data.bottom) {
        style.push('bottom:' + this.data.bottom);
      }
      if (this.data.right) {
        style.push('right:' + this.data.right);
      }
      if (this.data.left) {
        style.push('left:' + this.data.left);
      }
      if (this.data.top) {
        style.push('top:' + this.data.top);
      }
      if (this.data.zIndex) {
        style.push('z-index:' + this.data.zIndex);
      }
      this.setData({
        containerStyle: style.join(';')
      });
    },
    openAgent() {
      if (!wx.openAgent) {
        wx.showToast({
          title: '当前微信版本暂不支持 AI 助手',
          icon: 'none',
          duration: 2000
        });
        return;
      }
      wx.openAgent({
        context: this.data.context || '',
        fail: (res) => {
          wx.showToast({
            title: (res && res.errMsg) || '打开 AI 助手失败',
            icon: 'none',
            duration: 2000
          });
        }
      });
    }
  }
});
