const recommendGoods = require('./apis/recommendGoods')
const searchGoods = require('./apis/searchGoods')
const getGoodsDetail = require('./apis/getGoodsDetail')

if (wx.modelContext && wx.modelContext.createSkill) {
  const skill = wx.modelContext.createSkill('/skills/mall-guide-skill')
  skill.registerAPI('recommendGoods', recommendGoods)
  skill.registerAPI('searchGoods', searchGoods)
  skill.registerAPI('getGoodsDetail', getGoodsDetail)
}
