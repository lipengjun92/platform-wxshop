package com.platform.modules.app.request;

import lombok.Data;

import java.util.Map;

/**
 * @author pengjun
 */
@Data
public class ApiOrderSubmitRequest {
    private Integer addressId;
    private Integer couponId;
    private String type;
    private String postscript;
}
