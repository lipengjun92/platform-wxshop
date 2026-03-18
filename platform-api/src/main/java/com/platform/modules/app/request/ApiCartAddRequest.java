package com.platform.modules.app.request;

import lombok.Data;

@Data
public class ApiCartAddRequest {
    private Integer goodsId;
    private Integer productId;
    private Integer number;
}
