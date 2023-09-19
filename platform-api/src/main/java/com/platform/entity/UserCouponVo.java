package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class UserCouponVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //优惠券Id
    private Integer couponId;
    //优惠券数量
    private String couponNumber;
    //会员Id
    private Long userId;
    //使用时间
    private Date usedTime;
    //领取时间
    private Date addTime;
    //订单Id
    private Integer orderId;
    //来源key
    private String sourceKey;
    //分享人
    private Long referrer;
}
