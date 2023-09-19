package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 nideshop_user_coupon
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-19 15:40:33
 */
@Data
public class UserCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //优惠券Id
    private Integer couponId;
    //优惠券数量
    private String couponNumber;
    //会员Id
    private Integer userId;
    //使用时间
    private Date usedTime;
    //领取时间
    private Date addTime;
    //订单Id
    private Integer orderId;
}
