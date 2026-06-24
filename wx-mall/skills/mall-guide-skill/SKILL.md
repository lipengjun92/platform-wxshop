# mall-guide-skill

负责商品推荐、商品搜索和商品详情解释。

- 当用户表达模糊购物意图时，优先调用 `recommendGoods` 推荐商品
- 当用户明确给出商品名或关键词时，优先调用 `searchGoods`
- 用户决定查看某个商品后，调用 `getGoodsDetail`
- 用户决定购买某个商品后，将 `goodsId`、`productId`、`number` 交给 `mall-checkout-skill`
