Component({
  data: {
    viewCheckout: {}
  },
  lifetimes: {
    created() {
      if (!wx.modelContext || !wx.modelContext.getContext) {
        return
      }
      this._modelCtx = wx.modelContext.getContext(this)
      this._viewCtx = wx.modelContext.getViewContext && wx.modelContext.getViewContext(this)
      const { NotificationType } = wx.modelContext
      this._modelCtx.on(NotificationType.Result, (payload) => {
        const result = payload && payload.result ? payload.result : {}
        const meta = result._meta || {}
        const source = result.structuredContent || {}
        const viewCheckout = meta.viewCheckout || source.preview || {}
        this.setData({ viewCheckout })
      })
    }
  },
  methods: {
    onCheckoutTap() {
      const type = this.data.viewCheckout && this.data.viewCheckout.type
      const isBuy = type === 'buy' ? 1 : 0
      const url = '/pages/shopping/checkout/checkout?isBuy=' + isBuy
      if (this._viewCtx && this._viewCtx.openDetailPage) {
        this._viewCtx.openDetailPage({ url })
        return
      }
      wx.navigateTo({ url })
    }
  }
})
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
