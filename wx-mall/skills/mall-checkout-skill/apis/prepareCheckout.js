const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function prepareCheckout(args) {
  try {
    if (!wx.getStorageSync('token')) {
      return buildError('当前未登录，无法预览结算信息。请先登录后再试。')
    }
    const type = (args && args.type === 'buy') ? 'buy' : 'cart'
    const data = await agentRequest('cart/checkout', {
      addressId: (args && args.addressId) || 0,
      couponId: (args && args.couponId) || 0,
      type
    }, 'POST')
    const checkedAddress = (data && data.checkedAddress) || {}
    const checkedGoodsList = ((data && data.checkedGoodsList) || []).map((g) => ({
      goodsId: g.goodsId,
      productId: g.productId,
      goodsName: g.goodsName,
      goodsSpecifitionNameValue: g.goodsSpecifitionNameValue || '',
      retailPrice: g.retailPrice,
      number: g.number,
      listPicUrl: g.listPicUrl || ''
    }))
    const view = {
      type,
      consignee: checkedAddress.userName || checkedAddress.consignee || '',
      mobileMask: checkedAddress.mobile ? maskMobile(checkedAddress.mobile) : '',
      fullAddress: buildAddress(checkedAddress),
      checkedGoodsList,
      goodsTotalPrice: (data && data.goodsTotalPrice) || 0,
      freightPrice: (data && data.freightPrice) || 0,
      couponPrice: (data && data.couponPrice) || 0,
      orderTotalPrice: (data && data.orderTotalPrice) || 0,
      actualPrice: (data && data.actualPrice) || 0
    }
    return buildSuccess('已生成结算预览。请展示结算预览卡片，并提示用户：确认无误后点击卡片跳转到结算页提交订单（不要在 AI 中直接下单）。', {
      preview: view
    }, {
      viewCheckout: view
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

function buildAddress(addr) {
  if (!addr) return ''
  const region = addr.provinceName || addr.cityName || addr.districtName
    ? [addr.provinceName, addr.cityName, addr.districtName].filter(Boolean).join(' ')
    : (addr.fullRegion || '')
  return (region + ' ' + (addr.address || '')).trim()
}
