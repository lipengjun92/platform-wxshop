Component({
  data: {
    viewOrders: []
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
        const viewOrders = meta.viewOrders || source.orderList || []
        this.setData({ viewOrders })
      })
    }
  },
  methods: {
    onOrderTap(e) {
      const id = e.currentTarget.dataset.id
      if (!id) return
      const url = '/pages/ucenter/orderDetail/orderDetail?id=' + id
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
