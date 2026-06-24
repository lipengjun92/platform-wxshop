const listOrders = require('./apis/listOrders')
const getOrderDetail = require('./apis/getOrderDetail')

if (wx.modelContext && wx.modelContext.createSkill) {
  const skill = wx.modelContext.createSkill('/skills/mall-order-skill')
  skill.registerAPI('listOrders', listOrders)
  skill.registerAPI('getOrderDetail', getOrderDetail)
}
