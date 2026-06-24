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
