package com.platform.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyGoodsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer goodsId;
    private Integer productId;
    private Integer number;
    private String name;
}
