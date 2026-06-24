Component({
  data: {
    viewCart: {}
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
        const viewCart = meta.viewCart || source || {}
        this.setData({ viewCart })
      })
    }
  },
  methods: {
    onCartTap() {
      const url = '/pages/cart/cart'
      if (this._viewCtx && this._viewCtx.openDetailPage) {
        this._viewCtx.openDetailPage({ url })
        return
      }
      wx.switchTab({ url })
    }
  }
})
