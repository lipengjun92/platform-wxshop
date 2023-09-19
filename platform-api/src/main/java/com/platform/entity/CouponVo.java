package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class CouponVo implements Serializable {
    private static final long serialVersionUID = 1L;

    // 优惠券主键
    private Integer id;
    // 用户优惠券主键
    private Integer userCouponId;
    //优惠券名称
    private String name;
    //金额
    private BigDecimal typeMoney;
    //发放方式 0：按订单发放 1：按用户发放 2:商品转发送券 3：按商品发放 4:新用户注册 5：线下发放 6评价好评红包（固定或随机红包） 7包邮
    private Integer sendType;
    //最小金额
    private BigDecimal minAmount;
    //最大金额
    private BigDecimal maxAmount;
    //发放时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date sendStartDate;
    //发放时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date sendEndDate;
    //使用开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date useStartDate;
    //使用结束时间
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date useEndDate;
    //最小商品金额
    private BigDecimal minGoodsAmount;
    //优惠券说明
    private String couponTxt;
    //优惠券会员Id
    private String userId;
    //优惠券编码
    private String couponNumber;
    //可用 1:可用 0：不可用
    private Integer enabled = 0;
    //转发次数
    private Integer minTransmitNum;
    //优惠券状态 1 可用 2 已用 3 过期
    private Integer couponStatus = 1;
}
