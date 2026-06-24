Component({
  properties: {
    viewCheckout: {
      type: Object,
      value: {}
    }
  },
  methods: {
    onCheckoutTap() {
      const type = this.data.viewCheckout && this.data.viewCheckout.type
      const isBuy = type === 'buy' ? 1 : 0
      wx.navigateTo({ url: '/pages/shopping/checkout/checkout?isBuy=' + isBuy })
    }
  }
})
