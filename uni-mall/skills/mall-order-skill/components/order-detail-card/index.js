Component({
  data: {
    viewOrder: {}
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
        const viewOrder = meta.viewOrder || source.orderInfo || {}
        this.setData({ viewOrder })
      })
    }
  },
  methods: {
    onDetailTap() {
      const id = this.data.viewOrder && this.data.viewOrder.id
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
