const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function recommendGoods(args) {
  try {
    const params = {
      page: args.page || 1,
      size: args.size || 6,
      categoryId: args.categoryId || 0
    }
    const t = (args.type || 'isHot').toLowerCase()
    if (t === 'isnew') {
      params.isNew = 1
    } else {
      params.isHot = 1
    }
    const data = await agentRequest('goods/list', params, 'POST')
    const page = (data && data.goodsList) || {}
    const records = page.records || []
    if (!records.length) {
      return buildSuccess('当前没有查到可推荐商品。请换一个偏好、价格区间或关键词继续挑选。', {
        goodsList: [],
        total: 0,
        type: args.type || 'isHot'
      })
    }
    return buildSuccess('已找到一批可推荐商品。请引导用户进入商品列表页，或选择一个商品继续查看详情。', {
      goodsList: records.map((item) => ({
        id: item.id,
        name: item.name,
        goodsBrief: item.goodsBrief || '',
        retailPrice: item.retailPrice,
        sales: item.sales || 0
      })),
      total: page.total || records.length,
      pages: page.pages || 1,
      type: args.type || 'isHot'
    })
  } catch (err) {
    return buildError(err.message)
  }
}
