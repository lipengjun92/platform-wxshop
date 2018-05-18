import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

let routes = [
  {
    name: '/',
    path: '/',
    redirect: '/index',
    meta: {
      requiresAuth: false,
      title: '重定向',
      level:0
    }
  },
  {
    name: 'index',
    path: '/index',
    meta: {
      requiresAuth: false,
      title: '首页',
      level:2
    },
    component: (resolve) => {
      require(['../view/index/index'], resolve)
    }
  },
  {
    name: 'special',
    path: '/special',
    meta: {
      requiresAuth: false,
      title: '专题',
      level:2
    },
    component: (resolve) => {
      require(['../view/special/index'], resolve)
    }
  },
  {
    name: 'category',
    path: '/category',
    meta: {
      requiresAuth: false,
      title: '分类',
      level:2
    },
    component: (resolve) => {
      require(['../view/category/index'], resolve)
    }
  },
  {
    name: 'cart',
    path: '/cart',
    meta: {
      requiresAuth: false,
      title: '购物车',
      level:2
    },
    component: (resolve) => {
      require(['../view/cart/index'], resolve)
    }
  },
  {
    name: 'center',
    path: '/center',
    meta: {
      requiresAuth: false,
      title: '个人中心',
      level:2
    },
    component: (resolve) => {
      require(['../view/center/index'], resolve)
    }
  },
  {
    name: 'search',
    path: '/search',
    meta: {
      requiresAuth: false,
      title: '搜索',
      level:0
    },
    component: (resolve) => {
      require(['../view/search/index'], resolve)
    }
  },
  {
    name: 'search-list',
    path: '/search/list',
    meta: {
      requiresAuth: false,
      title: '搜索结果',
      level:0
    },
    component: (resolve) => {
      require(['../view/search/search-list'], resolve)
    }
  },
  {
    name: 'order',
    path: '/order',
    meta: {
      requiresAuth: true,
      title: '我的订单',
      level:1
    },
    component: (resolve) => {
      require(['../view/order/index'], resolve)
    }
  },
  {
    name: 'order-confirm',
    path: '/order/confirm',
    meta: {
      requiresAuth: true,
      title: '确认订单',
      level:1
    },
    component: (resolve) => {
      require(['../view/order/order-confirm'], resolve)
    }
  },
  {
    name: 'order-pay',
    path: '/order/pay',
    meta: {
      requiresAuth: true,
      title: '订单支付',
      level:1
    },
    component: (resolve) => {
      require(['../view/order/order-pay'], resolve)
    }
  },
  {
    name: 'order-pay-status',
    path: '/order/pay/status',
    meta: {
      requiresAuth: true,
      title: '支付完成',
      level:1
    },
    component: (resolve) => {
      require(['../view/order/order-pay-status'], resolve)
    }
  },
  {
    name: 'goods',
    path: '/goods/:id',
    meta: {
      requiresAuth: false,
      title: '商品详情',
      level:0
    },
    component: (resolve) => {
      require(['../view/goods/index'], resolve)
    }
  }
]

export const router = new VueRouter({
  mode: 'history',
  routes
})