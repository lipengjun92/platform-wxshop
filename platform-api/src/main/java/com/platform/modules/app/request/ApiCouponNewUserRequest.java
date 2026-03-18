package com.platform.modules.app.request;

import lombok.Data;

@Data
public class ApiCouponNewUserRequest {
    private String phone;
    private Integer smscode;
}
