const getCartSnapshot = require('./apis/getCartSnapshot')
const prepareCheckout = require('./apis/prepareCheckout')

if (wx.modelContext && wx.modelContext.createSkill) {
  const skill = wx.modelContext.createSkill('/skills/mall-checkout-skill')
  skill.registerAPI('getCartSnapshot', getCartSnapshot)
  skill.registerAPI('prepareCheckout', prepareCheckout)
}
