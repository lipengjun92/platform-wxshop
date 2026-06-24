# mall-checkout-skill

负责购物车清单核对和结算预览。出于安全考虑，本 skill 只做只读查询和结算预览，
真正的下单操作必须由用户在结算页 `pages/shopping/checkout/checkout` 手动确认后再发起。

- 查看购物车清单使用 `getCartSnapshot`
- 结算前的金额、地址、运费、优惠预览使用 `prepareCheckout`
- 用户确认要下单时，引导跳转到结算页，不在 AI 流程里直接调用提交订单接口
