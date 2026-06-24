const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function getOrderDetail(args) {
  try {
    if (!wx.getStorageSync('token')) {
      return buildError('当前未登录，无法查询订单。请先登录后再试。')
    }
    if (!args.orderId) {
      return buildError('缺少 orderId，请先从订单列表中选择一个订单。')
    }
    const data = await agentRequest('order/detail', { orderId: args.orderId }, 'POST')
    const orderInfo = (data && data.orderInfo) || {}
    const orderGoods = ((data && data.orderGoods) || []).map((g) => ({
      id: g.id,
      goodsId: g.goodsId,
      goodsName: g.goodsName,
      listPicUrl: g.listPicUrl || g.goodsLogo || '',
      number: g.number,
      retailPrice: g.retailPrice
    }))
    const view = {
      id: orderInfo.id,
      orderSn: orderInfo.orderSn || '',
      orderStatusText: orderInfo.orderStatusText || '',
      addTime: orderInfo.addTime || '',
      actualPrice: orderInfo.actualPrice,
      goodsPrice: orderInfo.goodsPrice,
      freightPrice: orderInfo.freightPrice,
      couponPrice: orderInfo.couponPrice,
      consignee: orderInfo.consignee || '',
      mobileMask: orderInfo.mobile ? maskMobile(orderInfo.mobile) : '',
      fullAddress: orderInfo.fullRegion ? (orderInfo.fullRegion + ' ' + (orderInfo.address || '')) : (orderInfo.address || ''),
      orderGoods
    }
    return buildSuccess('已获取订单详情。请展示订单详情卡片，并根据订单状态给出可执行的下一步操作建议。', {
      orderInfo: view,
      handleOption: (data && data.handleOption) || {}
    }, {
      viewOrder: view
    })
  } catch (err) {
    return buildError(err.message)
  }
}

function maskMobile(mobile) {
  const s = String(mobile)
  if (s.length < 7) return s
  return s.slice(0, 3) + '****' + s.slice(-4)
}
