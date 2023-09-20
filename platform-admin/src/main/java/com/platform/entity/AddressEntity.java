package com.platform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 * 表名 nideshop_address
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-10-02 14:11:24
 */
@Data
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 会员ID
     */
    private Integer userId;
    /**
     * 收货人姓名
     */
    private String userName;
    /**
     * 手机
     */
    private String telNumber;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 收货地址国家码
     */
    private String nationalCode;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区
     */
    private String countyName;
    /**
     * 详细收货地址信息
     */
    private String detailInfo;

    /**
     * 翻译会员名
     */
    private String shopUserName;
}
