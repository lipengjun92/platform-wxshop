# mall-order-skill

负责订单列表查询和订单详情查询。

- 查询订单列表使用 `listOrders`
- 查询某个订单使用 `getOrderDetail`
- 若 orderId 来自用户口述，应先调用 `listOrders` 让用户确认，再调用 `getOrderDetail`
