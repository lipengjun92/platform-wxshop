const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function getCartSnapshot() {
  try {
    if (!wx.getStorageSync('token')) {
      return buildError('当前未登录，无法读取购物车。请先登录后再试。')
    }
    const data = await agentRequest('cart/index', {}, 'POST')
    const cartList = ((data && data.cartList) || []).map((c) => ({
      id: c.id,
      goodsId: c.goodsId,
      productId: c.productId,
      goodsName: c.goodsName,
      goodsSpecifitionNameValue: c.goodsSpecifitionNameValue || '',
      retailPrice: c.retailPrice,
      number: c.number,
      checked: !!c.checked,
      listPicUrl: c.listPicUrl || ''
    }))
    const total = (data && data.cartTotal) || {}
    return buildSuccess('已获取购物车清单。请展示购物车卡片，并引导用户在结算前确认勾选商品。', {
      cartList,
      goodsCount: total.goodsCount || 0,
      checkedGoodsCount: total.checkedGoodsCount || 0,
      goodsAmount: total.goodsAmount || 0,
      checkedGoodsAmount: total.checkedGoodsAmount || 0
    }, {
      viewCart: {
        cartList,
        goodsCount: total.goodsCount || 0,
        checkedGoodsCount: total.checkedGoodsCount || 0,
        checkedGoodsAmount: total.checkedGoodsAmount || 0
      }
    })
  } catch (err) {
    return buildError(err.message)
  }
}
