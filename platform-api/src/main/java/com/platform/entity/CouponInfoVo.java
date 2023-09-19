package com.platform.entity;

import lombok.Data;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-09-28 11:13<br>
 * 描述: CouponInfoVo <br>
 */
@Data
public class CouponInfoVo {
    private String msg; // 显示信息
    private Integer type = 0; // 是否凑单 0否 1是
}
