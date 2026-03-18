package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.app.request.ApiOrderSubmitRequest;
import com.platform.modules.mall.entity.MallBrandEntity;
import com.platform.modules.mall.entity.MallOrderEntity;
import com.platform.modules.mall.entity.MallOrderGoodsEntity;
import com.platform.modules.mall.service.MallOrderGoodsService;
import com.platform.modules.mall.service.MallOrderService;
import com.platform.modules.wx.entity.WxUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 *
 * 描述: AppController <br>
 */
@Tag(name = "订单-AppOrderController")
@RestController
@RequestMapping("/app/order")
public class AppOrderController extends AppBaseController {
    @Autowired
    private MallOrderService orderService;
    @Autowired
    private MallOrderGoodsService orderGoodsService;
    @Autowired
    private WxPayService wxPayService;

    /**
     *
     */
    @Operation(summary = "订单首页")
    @IgnoreAuth
    @PostMapping("index")
    public RestResponse<Object> index() {
        //
        return RestResponse.ok("");
    }

    /**
     * 获取订单列表
     */
    @Operation(summary = "获取订单列表")
    @PostMapping("list")
    public RestResponse<Map<String, Object>> list(@LoginUser WxUserEntity loginUser,
                                                  @RequestParam(value = "page") Integer page,
                                                  @RequestParam(value = "size") Integer size) {
        //
        Map<String, Object> params = new HashMap<>();
        params.put("userId", loginUser.getId());
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "add_time");
        params.put("order", "desc");
        //查询列表数据
        Query<MallOrderEntity> query = new Query<>(params);
        List<MallOrderEntity> orderEntityList = orderService.queryList(query);
        int total = orderEntityList.size();
        Map<String, Object> pageData = new HashMap<>();
        //
        orderEntityList.forEach(item -> {
            Map<String, Object> orderGoodsParam = new HashMap<>();
            orderGoodsParam.put("orderId", item.getId());
            //订单的商品
            List<MallOrderGoodsEntity> goodsList = orderGoodsService.queryList(orderGoodsParam);
            Integer goodsCount = 0;
            item.setGoodsCount(goodsCount);
            for (MallOrderGoodsEntity orderGoodsEntity : goodsList) {
                goodsCount += orderGoodsEntity.getNumber();
                item.setGoodsCount(goodsCount);
            }
            item.setGoodsList(goodsList);
        });
        pageData.put("list", orderEntityList);
        pageData.put("total", total);
        pageData.put("limit", size);
        pageData.put("page", page);
        return RestResponse.ok(pageData);
    }

    /**
     * 获取订单详情
     */
    @Operation(summary = "获取订单详情")
    @PostMapping("detail")
    public RestResponse<Map<String, Object>> detail(Integer orderId, @LoginUser WxUserEntity loginUser) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        MallOrderEntity orderInfo = orderService.getById(orderId);
        if (null == orderInfo) {
            return RestResponse.fail("订单不存在");
        }

        if (!loginUser.getId().equals(orderInfo.getUserId())) {
            return RestResponse.fail("越权操作！");
        }
        Map<String, Object> orderGoodsParam = new HashMap<>();
        orderGoodsParam.put("orderId", orderId);
        //订单的商品
        List<MallOrderGoodsEntity> orderGoods = orderGoodsService.queryList(orderGoodsParam);
        //订单最后支付时间
        if (orderInfo.getOrderStatus() == 0) {
            // if (moment().subtract(60, 'minutes') < moment(orderInfo.addTime)) {
//            orderInfo.final_pay_time = moment("001234", "Hmmss").format("mm:ss")
            // } else {
            //     //超过时间不支付，更新订单状态为取消
            // }
        }

        //订单可操作的选择,删除，支付，收货，评论，退换货
        Map<String, Object> handleOption = orderInfo.getHandleOption();
        //
        resultObj.put("orderInfo", orderInfo);
        resultObj.put("orderGoods", orderGoods);
        resultObj.put("handleOption", handleOption);
        resultObj.put("shippingList", new ArrayList<>());
        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "修改订单")
    @PostMapping("updateSuccess")
    public RestResponse<Object> updateSuccess(Integer orderId, @LoginUser WxUserEntity loginUser) {
        MallOrderEntity orderInfo = orderService.getById(orderId);
        if (orderInfo == null) {
            return RestResponse.fail("订单不存在");
        } else if (orderInfo.getOrderStatus() != 0) {
            return RestResponse.fail("订单状态不正确orderStatus" + orderInfo.getOrderStatus() + "payStatus" + orderInfo.getPayStatus());
        }

        if (!loginUser.getId().equals(orderInfo.getUserId())) {
            return RestResponse.fail("越权操作！");
        }
        orderInfo.setId(orderId);
        orderInfo.setPayStatus(2);
        orderInfo.setOrderStatus(201);
        orderInfo.setShippingStatus(0);
        orderInfo.setPayTime(new Date());
        boolean num = orderService.update(orderInfo);
        if (num) {
            return RestResponse.ok("修改订单成功");
        } else {
            return RestResponse.fail("修改订单失败");
        }
    }

    /**
     * 获取订单列表
     */
    @Operation(summary = "订单提交")
    @PostMapping("submit")
    public RestResponse<Object> submit(@LoginUser WxUserEntity loginUser, @RequestBody ApiOrderSubmitRequest request) {
        Map<String, Object> resultObj = null;
        try {
            JSONObject payload = new JSONObject();
            if (request.getAddressId() != null) {
                payload.put("addressId", request.getAddressId());
            }
            if (request.getCouponId() != null) {
                payload.put("couponId", request.getCouponId());
            }
            if (request.getType() != null) {
                payload.put("type", request.getType());
            }
            if (request.getPostscript() != null) {
                payload.put("postscript", request.getPostscript());
            }
            resultObj = orderService.submit(payload, loginUser);
            if (null != resultObj) {
                Integer code = MapUtils.getInteger(resultObj, "code");
                if (code != null && code == 0) {
                    return RestResponse.ok(resultObj.get("data"));
                }
                return RestResponse.fail(MapUtils.getString(resultObj, "msg", "提交失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResponse.fail("提交失败");
    }

    /**
     * 取消订单
     */
    @Operation(summary = "取消订单")
    @PostMapping("cancelOrder")
    public RestResponse<String> cancelOrder(Integer orderId, @LoginUser WxUserEntity loginUser) {

        MallOrderEntity orderVo = orderService.getById(orderId);
        if (null == orderVo) {
            return RestResponse.fail("订单不存在！");
        }
        if (!loginUser.getId().equals(orderVo.getUserId())) {
            return RestResponse.fail("越权操作！");
        }
        if (orderVo.getOrderStatus() == 300) {
            return RestResponse.fail("已发货，不能取消");
        } else if (orderVo.getOrderStatus() == 301) {
            return RestResponse.fail("已收货，不能取消");
        }
        // 需要退款
        if (orderVo.getPayStatus() == 2) {
            try {
                WxPayRefundRequest refundRequest = new WxPayRefundRequest();
                refundRequest.setOutTradeNo(orderVo.getOrderSn());
                refundRequest.setOutRefundNo(orderVo.getOrderSn());
                refundRequest.setTotalFee(BaseWxPayRequest.yuanToFen(orderVo.getActualPrice().toString()));
                refundRequest.setRefundFee(BaseWxPayRequest.yuanToFen(orderVo.getActualPrice().toString()));
                WxPayRefundResult result = wxPayService.refund(refundRequest);
                if ("SUCCESS".equals(result.getResultCode())) {
                    if (orderVo.getOrderStatus() == 201) {
                        orderVo.setOrderStatus(401);
                    } else if (orderVo.getOrderStatus() == 300) {
                        orderVo.setOrderStatus(402);
                    }
                    orderVo.setPayStatus(4);
                    orderService.update(orderVo);
                    return RestResponse.ok("取消成功");
                }
            } catch (WxPayException e) {
                return RestResponse.fail("取消失败：" + e.getErrCode());
            }
            return RestResponse.fail("取消失败");
        } else {
            orderVo.setOrderStatus(101);
            orderService.update(orderVo);
            return RestResponse.ok("取消成功");
        }
    }

    /**
     * 确认收货
     */
    @Operation(summary = "确认收货")
    @PostMapping("confirmOrder")
    public RestResponse<String> confirmOrder(Integer orderId, @LoginUser WxUserEntity loginUser) {
        try {
            MallOrderEntity orderVo = orderService.getById(orderId);
            if (null == orderVo) {
                return RestResponse.fail("订单不存在！");
            }
            if (!loginUser.getId().equals(orderVo.getUserId())) {
                return RestResponse.fail("越权操作！");
            }
            orderVo.setOrderStatus(301);
            orderVo.setShippingStatus(2);
            orderVo.setConfirmTime(new Date());
            orderService.update(orderVo);
            return RestResponse.ok("确认收货成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.fail("提交失败");
        }
    }
}
