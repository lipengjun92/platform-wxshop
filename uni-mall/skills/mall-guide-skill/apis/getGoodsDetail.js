const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function getGoodsDetail(args) {
  try {
    const data = await agentRequest('goods/detail', { id: args.goodsId }, 'POST')
    const info = (data && data.info) || {}
    const productList = ((data && data.productList) || []).map((item) => ({
      id: item.id,
      goodsId: item.goodsId,
      retailPrice: item.retailPrice,
      goodsNumber: item.goodsNumber,
      goodsSpecificationIds: item.goodsSpecificationIds || '',
      goodsSpecificationNameValue: item.goodsSpecificationNameValue || ''
    }))
    return buildSuccess('已获取商品详情。请展示商品详情卡片，并根据价格、销量和规格引导用户继续下单。结算前必须从 productList 中选择有效的 productId。', {
      goods: {
        id: info.id,
        name: info.name,
        goodsBrief: info.goodsBrief || '',
        retailPrice: info.retailPrice,
        marketPrice: info.marketPrice,
        sales: info.sales || 0,
        listPicUrl: info.listPicUrl || info.primaryPicUrl || ''
      },
      productList
    }, {
      viewGoods: {
        id: info.id,
        name: info.name,
        goodsBrief: info.goodsBrief || '',
        retailPrice: info.retailPrice,
        marketPrice: info.marketPrice,
        sales: info.sales || 0,
        listPicUrl: info.listPicUrl || info.primaryPicUrl || '',
        productList
      }
    })
  } catch (err) {
    return buildError(err.message)
  }
}
