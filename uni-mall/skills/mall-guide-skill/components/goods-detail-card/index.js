Component({
  data: {
    salesText: 0,
    goods: {},
    productList: []
  },
  lifetimes: {
    created() {
      if (!wx.modelContext || !wx.modelContext.getContext || !wx.modelContext.getViewContext) {
        return
      }
      this._modelCtx = wx.modelContext.getContext(this)
      this._viewCtx = wx.modelContext.getViewContext(this)
      const { NotificationType } = wx.modelContext
      this._modelCtx.on(NotificationType.Result, (payload) => {
        const result = payload && payload.result ? payload.result : {}
        this.syncGoods(result)
      })
    }
  },
  methods: {
    syncGoods(result) {
      const source = result.structuredContent || {}
      const meta = result._meta || {}
      const goods = meta.viewGoods || source.goods || {}
      const productList = (goods.productList || source.productList || []).slice()
      this.setData({
        goods,
        productList,
        salesText: goods.sales || 0
      })
      this.setRelatedPage(goods)
    },
    setRelatedPage(goods) {
      if (!goods.id || !this._viewCtx || !this._viewCtx.setRelatedPage) {
        return
      }
      this._viewCtx.setRelatedPage({
        query: 'id=' + goods.id
      })
    },
    openGoodsDetail() {
      const goods = this.data.goods || {}
      if (!goods.id) {
        return
      }
      const url = '/pages/goods/goods?id=' + goods.id
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
    viewGoods: {
      type: Object,
      value: {}
    }
  },
  methods: {
    onDetailTap() {
      const id = this.data.viewGoods && this.data.viewGoods.id
      if (!id) return
      wx.navigateTo({ url: '/pages/goods/goods?id=' + id })
    }
  }
})
