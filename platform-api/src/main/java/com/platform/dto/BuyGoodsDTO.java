package com.platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyGoodsDTO implements Serializable {
    private Integer goodsId;
    private Integer productId;
    private Integer number;
    private String name;
}
