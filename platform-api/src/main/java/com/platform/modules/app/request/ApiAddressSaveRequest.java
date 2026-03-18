package com.platform.modules.app.request;

import lombok.Data;

@Data
public class ApiAddressSaveRequest {
    private Integer id;
    private String userName;
    private String postalCode;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String detailInfo;
    private String nationalCode;
    private String telNumber;
    private Boolean isDefault;
}
