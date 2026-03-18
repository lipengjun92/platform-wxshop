package com.platform.modules.app.request;

import com.platform.modules.app.entity.FullUserInfo;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
public class ApiAuthLoginByWeixinRequest {
    private String code;
    private FullUserInfo userInfo;
}
