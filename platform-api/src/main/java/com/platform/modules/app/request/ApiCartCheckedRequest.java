package com.platform.modules.app.request;

import lombok.Data;

@Data
public class ApiCartCheckedRequest {
    private String productIds;
    private Integer isChecked;
}
