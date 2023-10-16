const API_BASE_URL = 'https://fly2you.cn/platform-framework/api/';
// const API_BASE_URL = 'http://localhost:8080/platform-framework/api/';
module.exports = {
    IndexUrlNewGoods: API_BASE_URL + 'index/newGoods', //新品首发
    IndexUrlHotGoods: API_BASE_URL + 'index/hotGoods', //热卖商品
    IndexUrlTopic: API_BASE_URL + 'index/topic', //专题精选
    IndexUrlBrand: API_BASE_URL + 'index/brand', //品牌制造商
    IndexUrlCategory: API_BASE_URL + 'index/category', //首页底部的分类及商品列表
    IndexUrlBanner: API_BASE_URL + 'index/banner', //首页banner
    IndexUrlChannel: API_BASE_URL + 'index/channel', //banner下的分类
    Catalog: API_BASE_URL + 'catalog/',  //分类数据接口
    CatalogList: API_BASE_URL + 'catalog/index',  //分类目录全部分类数据接口
    CatalogCurrent: API_BASE_URL + 'catalog/current',  //分类目录当前分类数据接口

    AuthLoginByWeixin: API_BASE_URL + 'auth/login_by_weixin', //微信登录

    GoodsCount: API_BASE_URL + 'goods/count',  //统计商品总数
    GoodsList: API_BASE_URL + 'goods/list',  //获得商品列表
    GoodsCategory: API_BASE_URL + 'goods/category',  //获得分类数据
    GoodsDetail: API_BASE_URL + 'goods/detail',  //获得商品的详情
    GoodsHot: API_BASE_URL + 'goods/hot',  //人气推荐
    GoodsRelated: API_BASE_URL + 'goods/related',  //商品详情页的关联商品（大家都在看）

    BrandList: API_BASE_URL + 'brand/list',  //品牌列表
    BrandDetail: API_BASE_URL + 'brand/detail',  //品牌详情

    CartList: API_BASE_URL + 'cart/index', //获取购物车的数据
    CartAdd: API_BASE_URL + 'cart/add', // 添加商品到购物车
    BuyAdd: API_BASE_URL + 'buy/add', // 直接购买
    CartUpdate: API_BASE_URL + 'cart/update', // 更新购物车的商品
    CartDelete: API_BASE_URL + 'cart/delete', // 删除购物车的商品
    CartChecked: API_BASE_URL + 'cart/checked', // 选择或取消选择商品
    CartGoodsCount: API_BASE_URL + 'cart/goodscount', // 获取购物车商品件数
    CartCheckout: API_BASE_URL + 'cart/checkout', // 下单前信息确认

    OrderSubmit: API_BASE_URL + 'order/submit', // 提交订单
    PayPrepayId: API_BASE_URL + 'pay/prepay', //获取微信统一下单prepay_id

    CollectList: API_BASE_URL + 'collect/list',  //收藏列表
    CollectAddOrDelete: API_BASE_URL + 'collect/addordelete',  //添加或取消收藏

    CommentList: API_BASE_URL + 'comment/list',  //评论列表
    CommentCount: API_BASE_URL + 'comment/count',  //评论总数
    CommentPost: API_BASE_URL + 'comment/post',   //发表评论

    TopicList: API_BASE_URL + 'topic/list',  //专题列表
    TopicDetail: API_BASE_URL + 'topic/detail',  //专题详情
    TopicRelated: API_BASE_URL + 'topic/related',  //相关专题

    SearchIndex: API_BASE_URL + 'search/index',  //搜索页面数据
    SearchHelper: API_BASE_URL + 'search/helper',  //搜索帮助
    SearchClearHistory: API_BASE_URL + 'search/clearhistory',  //搜索帮助

    AddressList: API_BASE_URL + 'address/list',  //收货地址列表
    AddressDetail: API_BASE_URL + 'address/detail',  //收货地址详情
    AddressSave: API_BASE_URL + 'address/save',  //保存收货地址
    AddressDelete: API_BASE_URL + 'address/delete',  //删除收货地址

    RegionList: API_BASE_URL + 'region/list',  //获取区域列表

    OrderList: API_BASE_URL + 'order/list',  //订单列表
    OrderDetail: API_BASE_URL + 'order/detail',  //订单详情
    OrderCancel: API_BASE_URL + 'order/cancelOrder',  //取消订单
    OrderConfirm: API_BASE_URL + 'order/confirmOrder',  //确认收货

    FootprintList: API_BASE_URL + 'footprint/list',  //足迹列表
    FootprintDelete: API_BASE_URL + 'footprint/delete',  //删除足迹

    FeedbackAdd: API_BASE_URL + 'feedback/save', //添加反馈
    SmsCode: API_BASE_URL + 'user/smscode', //发送短信
    BindMobile: API_BASE_URL + 'user/bindMobile', //绑定手机
    Login: API_BASE_URL + 'auth/login', //账号登录
    Code: API_BASE_URL + 'auth/', //静默登录
    Register: API_BASE_URL + 'auth/register', //注册
    CouponList: API_BASE_URL + 'coupon/list', // 优惠券列表
    GoodsCouponList: API_BASE_URL + 'coupon/listByGoods', // 商品优惠券列表
    OrderQuery: API_BASE_URL + 'pay/query',//微信查询订单状态

    HelpTypeList: API_BASE_URL + 'helpissue/typeList', //查看帮助类型列表
    HelpIssueList: API_BASE_URL + 'helpissue/issueList', //查看问题列表
};
