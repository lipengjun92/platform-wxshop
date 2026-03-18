package com.platform.modules.app.request;

import lombok.Data;

@Data
public class ApiCartUpdateRequest {
    private Integer goodsId;
    private Integer productId;
    private Integer number;
    private Integer id;
}
