package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:09
 */
@Data
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //订单序列号
    private String orderSn;
    //会员Id
    private Integer userId;
    //订单状态
    //订单相关状态字段设计，采用单个字段表示全部的订单状态
    //1xx 表示订单取消和删除等状态 0订单创建成功等待付款，　101订单已取消，　102订单已删除
    //2xx 表示订单支付状态　201订单已付款，等待发货
    //3xx 表示订单物流相关状态　300订单已发货， 301用户确认收货
    //4xx 表示订单退换货相关的状态　401 没有发货，退款　402 已收货，退款退货
    private Integer orderStatus;
    //发货状态 商品配送情况;0未发货,1已发货,2已收货,4退货
    private Integer shippingStatus;
    //付款状态 支付状态;0未付款;1付款中;2已付款
    private Integer payStatus;
    //收货人
    private String consignee;
    //国家
    private String country;
    //省
    private String province;
    //地市
    private String city;
    //区县
    private String district;
    //收货地址
    private String address;
    //联系电话
    private String mobile;
    //补充说明
    private String postscript;
    //快递公司Id
    private Integer shippingId;
    //快递公司名称
    private String shippingName;
    //快递单号
    private String shippingNo;
    //付款
    private String payId;
    //
    private String payName;
    //快递费用
    private BigDecimal shippingFee;
    //实际需要支付的金额
    private BigDecimal actualPrice;
    //
    private Integer integral;
    //
    private BigDecimal integralMoney;
    //订单总价
    private BigDecimal orderPrice;
    //商品总价
    private BigDecimal goodsPrice;
    //新增时间
    private Date addTime;
    //确认时间
    private Date confirmTime;
    //付款时间
    private Date payTime;
    //配送费用
    private Integer freightPrice;
    //使用的优惠券id
    private Integer couponId;
    //
    private Integer parentId;
    //优惠价格
    private BigDecimal couponPrice;
    //
    private String callbackStatus;

    //订单类型 1：普通订单 2：团购订单 3：砍价订单 4: 直接购买
    private String orderType;

    //
    private String userName;
}
