Component({
  properties: {
    viewOrder: {
      type: Object,
      value: {}
    }
  },
  methods: {
    onDetailTap() {
      const id = this.data.viewOrder && this.data.viewOrder.id
      if (!id) return
      wx.navigateTo({ url: '/pages/ucenter/orderDetail/orderDetail?id=' + id })
    }
  }
})
