Component({
  properties: {
    viewItems: {
      type: Array,
      value: []
    }
  },
  methods: {
    onGoodsTap(e) {
      const id = e.currentTarget.dataset.id
      if (!id) return
      wx.navigateTo({
        url: '/pages/goods/goods?id=' + id
      })
    }
  }
})
