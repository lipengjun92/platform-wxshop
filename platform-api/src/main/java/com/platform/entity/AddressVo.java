package com.platform.entity;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:39
 */
@Data
public class AddressVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //会员ID
    private Long userId;
    //收货人姓名
    private String userName;
    //手机
    private String telNumber;
    //邮编
    private String postalCode;
    //收货地址国家码
    private String nationalCode;
    //省
    private String provinceName;
    //市
    private String cityName;
    //区
    private String countyName;
    //详细收货地址信息
    private String detailInfo;

    //默认
    private Integer isDefault = 0;

    private String fullRegion;

    public String getFullRegion() {
        if (StringUtils.isEmpty(fullRegion)) {
            fullRegion = getProvinceName() + getCityName() + getCountyName();
        }
        return fullRegion;
    }
}
