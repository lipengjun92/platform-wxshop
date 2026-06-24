const { agentRequest, buildSuccess, buildError } = require('./request')

module.exports = async function searchGoods(args) {
  try {
    const data = await agentRequest('goods/list', {
      keyword: args.keyword,
      page: args.page || 1,
      size: args.size || 6,
      categoryId: args.categoryId || 0
    }, 'POST')
    const page = (data && data.goodsList) || {}
    const records = page.records || []
    if (!records.length) {
      return buildSuccess('没有搜索到匹配商品。请换一个关键词，或告诉我你的预算和用途。', {
        goodsList: [],
        total: 0,
        keyword: args.keyword || ''
      })
    }
    return buildSuccess('已找到匹配的商品。请展示商品列表卡片，并引导用户选择一个继续查看详情。', {
      goodsList: records.map((item) => ({
        id: item.id,
        name: item.name,
        goodsBrief: item.goodsBrief || '',
        retailPrice: item.retailPrice,
        sales: item.sales || 0
      })),
      total: page.total || records.length,
      pages: page.pages || 1,
      keyword: args.keyword || ''
    }, {
      viewItems: records.map((item) => ({
        id: item.id,
        name: item.name,
        goodsBrief: item.goodsBrief || '',
        retailPrice: item.retailPrice,
        sales: item.sales || 0,
        listPicUrl: item.listPicUrl || item.primaryPicUrl || ''
      }))
    })
  } catch (err) {
    return buildError(err.message)
  }
}
