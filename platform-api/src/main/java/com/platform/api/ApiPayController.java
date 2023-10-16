package com.platform.api;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.cache.J2CacheUtils;
import com.platform.entity.OrderGoodsVo;
import com.platform.entity.OrderVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiOrderGoodsService;
import com.platform.service.ApiOrderService;
import com.platform.service.WeixinPayService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.ResourceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 *
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * 描述: ApiIndexController <br>
 */
@Api(tags = "商户支付-ApiPayController")
@RestController
@RequestMapping("/api/pay")
@Slf4j
public class ApiPayController extends ApiBaseAction {
    @Autowired
    private ApiOrderService orderService;
    @Autowired
    private ApiOrderGoodsService orderGoodsService;
    @Autowired
    private WeixinPayService weixinPayService;

    /**
     * 获取支付的请求参数
     */
    @ApiOperation(value = "获取支付的请求参数")
    @PostMapping("prepay")
    public Object payPrepay(@LoginUser UserVo loginUser, Integer orderId) {
        OrderVo orderInfo = orderService.queryObject(orderId);

        if (null == orderInfo) {
            return toResponsObject(400, "订单已取消", "");
        }

        if (orderInfo.getPayStatus() == 2) {
            return toResponsObject(400, "订单已支付，请不要重复操作", "");
        }

        try {
            //调用统一下单接口，并组装生成支付所需参数对象
            WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();

            // 商品描述
            request.setBody("微同商城-支付");
            //订单的商品
            Map<String, Object> orderGoodsParam = new HashMap<>(4);
            orderGoodsParam.put("orderId", orderId);
            List<OrderGoodsVo> orderGoods = orderGoodsService.queryList(orderGoodsParam);
            if (null != orderGoods) {
                String body = "微同商城-";
                for (OrderGoodsVo goodsVo : orderGoods) {
                    body = body + goodsVo.getGoodsName() + "、";
                }
                body = body.substring(0, body.length() - 1);
                // 商品描述
                request.setBody(body);
            }
            // 商户订单编号
            request.setOutTradeNo(orderInfo.getOrderSn());
            //支付金额
            request.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getActualPrice().toString()));
            request.setSpbillCreateIp(getClientIp());
            // 回调地址
            request.setNotifyUrl(ResourceUtil.getConfigByName("wx.notifyUrl"));
            // 交易类型APP
            request.setTradeType(ResourceUtil.getConfigByName("wx.tradeType"));
            request.setOpenid(loginUser.getWeixinOpenid());
            WxPayMpOrderResult data = weixinPayService.createOrder(request);
            // 业务处理
            orderInfo.setPayId(data.getPackageValue());
            // 付款中
            orderInfo.setPayStatus(1);
            orderService.update(orderInfo);

            return toResponsObject(0, "微信统一订单下单成功", data);
        } catch (WxPayException e) {
            e.printStackTrace();
            return toResponseFail("下单失败,error=" + e.getMessage());
        }
    }

    /**
     * 微信查询订单状态
     */
    @ApiOperation(value = "查询订单状态")
    @PostMapping("query")
    public Object orderQuery(@LoginUser UserVo loginUser, Integer orderId) {
        if (orderId == null) {
            return toResponseFail("订单不存在");
        }

        OrderVo orderDetail = orderService.queryObject(orderId);
        try {
            WxPayOrderQueryResult result = weixinPayService.queryOrder("", orderDetail.getOrderSn());

            String tradeState = result.getTradeState();
            if ("SUCCESS".equals(tradeState)) {
                // 更改订单状态
                OrderVo orderInfo = new OrderVo();
                orderInfo.setId(orderId);
                orderInfo.setPayStatus(2);
                orderInfo.setOrderStatus(201);
                orderInfo.setShippingStatus(0);
                orderInfo.setPayTime(new Date());
                orderService.update(orderInfo);
                return toResponseSuccess("支付成功");
            } else if ("USERPAYING".equals(tradeState)) {
                // 重新查询 正在支付中
                Integer num = (Integer) J2CacheUtils.get(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId + "");
                if (num == null) {
                    J2CacheUtils.put(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId + "", 1);
                    this.orderQuery(loginUser, orderId);
                } else if (num <= 3) {
                    J2CacheUtils.remove(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId);
                    this.orderQuery(loginUser, orderId);
                } else {
                    return toResponseFail("查询失败,error=" + tradeState);
                }
            } else {
                // 失败
                return toResponseFail("查询失败,error=" + tradeState);
            }
        } catch (WxPayException e) {
            e.printStackTrace();
            return toResponseFail("下单失败,error=" + e.getMessage());
        }
        return toResponseFail("查询失败，未知错误");
    }

    /**
     * 微信订单回调接口
     *
     * @return
     */
    @ApiIgnore
    @IgnoreAuth
    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            InputStream in = request.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            //xml数据
            String reponseXml = new String(out.toByteArray(), "utf-8");
            WxPayOrderNotifyResult result = weixinPayService.parseOrderNotifyResult(reponseXml);
            String resultCode = result.getResultCode();
            String outTradeNo = result.getOutTradeNo();
            if ("FAIL".equalsIgnoreCase(resultCode)) {
                log.error("订单" + outTradeNo + "支付失败");
                response.getWriter().write(setXml("SUCCESS", "OK"));
            } else if ("SUCCESS".equalsIgnoreCase(resultCode)) {
                log.error("订单" + outTradeNo + "支付成功");
                // 业务处理
                OrderVo orderInfo = orderService.queryObjectByOrderSn(outTradeNo);
                orderInfo.setPayStatus(2);
                orderInfo.setOrderStatus(201);
                orderInfo.setShippingStatus(0);
                orderInfo.setPayTime(new Date());
                orderService.update(orderInfo);
                response.getWriter().write(setXml("SUCCESS", "OK"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单退款请求
     */
    @ApiOperation(value = "订单退款请求")
    @PostMapping("refund")
    public Object refund(Integer orderId) {
        //
        OrderVo orderInfo = orderService.queryObject(orderId);

        if (null == orderInfo) {
            return toResponsObject(400, "订单已取消", "");
        }

        if (orderInfo.getOrderStatus() == 401 || orderInfo.getOrderStatus() == 402) {
            return toResponsObject(400, "订单已退款", "");
        }
        WxPayRefundRequest request = new WxPayRefundRequest();
        request.setOutTradeNo(orderInfo.getOrderSn());
        request.setOutRefundNo(orderInfo.getOrderSn());
        request.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getActualPrice().toString()));
        request.setRefundFee(BaseWxPayRequest.yuanToFen(orderInfo.getActualPrice().toString()));
        request.setRefundDesc("用户取消订单");
        WxPayRefundResult result;
        try {
            result = weixinPayService.refund(request);
        } catch (WxPayException e) {
            return toResponsObject(400, "退款失败：" + e.getErrCode(), "");
        }

        if ("SUCCESS".equals(result.getResultCode())) {
            if (orderInfo.getOrderStatus() == 201) {
                orderInfo.setOrderStatus(401);
            } else if (orderInfo.getOrderStatus() == 300) {
                orderInfo.setOrderStatus(402);
            }
            orderService.update(orderInfo);
            return toResponsObject(400, "成功退款", "");
        } else {
            return toResponsObject(400, "退款失败", "");
        }
    }


    //返回微信服务
    public static String setXml(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml>";
    }

    //模拟微信回调接口
    public static String callbakcXml(String orderNum) {
        return "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type> <is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid> <out_trade_no><![CDATA[" + orderNum + "]]></out_trade_no>  <result_code><![CDATA[SUCCESS]]></result_code> <return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign><sub_mch_id><![CDATA[10000100]]></sub_mch_id> <time_end><![CDATA[20140903131540]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id></xml>";
    }
}
