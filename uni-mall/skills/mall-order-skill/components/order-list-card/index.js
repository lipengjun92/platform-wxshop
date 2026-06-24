Component({
  properties: {
    viewOrders: {
      type: Array,
      value: []
    }
  },
  methods: {
    onOrderTap(e) {
      const id = e.currentTarget.dataset.id
      if (!id) return
      wx.navigateTo({ url: '/pages/ucenter/orderDetail/orderDetail?id=' + id })
    }
  }
})
