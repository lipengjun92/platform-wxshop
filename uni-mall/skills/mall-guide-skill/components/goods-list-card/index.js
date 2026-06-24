Component({
  data: {
    goodsList: [],
    source: {},
    hasMore: false
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
        this.syncGoodsList(result)
      })
    }
  },
  methods: {
    syncGoodsList(result) {
      const source = result.structuredContent || {}
      const meta = result._meta || {}
      const viewItems = meta.viewItems || source.goodsList || []
      const goodsList = viewItems.map((item) => ({
        id: item.id,
        listPicUrl: item.listPicUrl || '',
        name: item.name,
        goodsBrief: item.goodsBrief || '点击查看商品详情',
        retailPrice: item.retailPrice,
        salesText: item.sales || 0
      }))
      this.setData({
        goodsList: goodsList.slice(0, 3),
        source,
        hasMore: goodsList.length > 3
      })
      this.setRelatedPage(source)
    },
    setRelatedPage(source) {
      if (!this._viewCtx) {
        return
      }
      if (!this._viewCtx.setRelatedPage) {
        return
      }
      if (source.keyword) {
        this._viewCtx.setRelatedPage({
          query: 'keyword=' + source.keyword
        })
        return
      }
      if (source.type) {
        this._viewCtx.setRelatedPage({
          query: 'type=' + source.type
        })
      }
    },
    openGoodsDetail(e) {
      const goodsId = e.currentTarget.dataset.id
      if (!goodsId) {
        return
      }
      const url = '/pages/goods/goods?id=' + goodsId
      if (this._viewCtx && this._viewCtx.openDetailPage) {
        this._viewCtx.openDetailPage({ url })
        return
      }
      wx.navigateTo({ url })
    },
    openMore() {
      const source = this.data.source || {}
      let url = '/pages/category/category'
      if (source.keyword) {
        url += '?keyword=' + source.keyword
      } else if (source.type) {
        url += '?type=' + source.type
      }
      if (this._viewCtx && this._viewCtx.openDetailPage) {
        this._viewCtx.openDetailPage({ url })
        return
      }
      wx.navigateTo({ url })
    }
  }
})
