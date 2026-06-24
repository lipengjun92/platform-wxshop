Component({
  properties: {
    viewCart: {
      type: Object,
      value: {}
    }
  },
  methods: {
    onCartTap() {
      wx.switchTab({ url: '/pages/cart/cart' })
    }
  }
})
