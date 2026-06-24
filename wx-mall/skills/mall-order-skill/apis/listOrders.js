const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function listOrders(args) {
  try {
    if (!wx.getStorageSync('token')) {
      return buildError('当前未登录，无法查询订单。请先登录后再试。')
    }
    const data = await agentRequest('order/list', {
      page: args.page || 1,
      size: args.size || 6
    }, 'POST')
    const list = (data && data.list) || []
    if (!list.length) {
      return buildSuccess('暂无订单。', {
        orderList: [],
        total: 0,
        page: (data && data.page) || 1
      })
    }
    const mapped = list.map((o) => ({
      id: o.id,
      orderSn: o.orderSn || '',
      orderStatusText: o.orderStatusText || '',
      actualPrice: o.actualPrice,
      addTime: o.addTime || '',
      goodsCount: (o.goodsList && o.goodsList.length) || o.goodsCount || 0,
      goodsList: (o.goodsList || []).map((g) => ({
        id: g.id,
        goodsId: g.goodsId,
        goodsName: g.goodsName,
        listPicUrl: g.listPicUrl || g.goodsLogo || '',
        number: g.number,
        retailPrice: g.retailPrice
      }))
    }))
    return buildSuccess('已获取订单列表。请展示订单列表卡片，并引导用户继续查看某个订单详情。', {
      orderList: mapped,
      total: (data && data.total) || mapped.length,
      page: (data && data.page) || 1
    }, {
      viewOrders: mapped
    })
  } catch (err) {
    return buildError(err.message)
  }
}
