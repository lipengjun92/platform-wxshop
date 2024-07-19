package com.platform.modules.app.request;

import com.platform.modules.app.entity.FullUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
@Schema(description = "微信小程序手机号授权")
public class LoginWxRequest {
    @Schema(description = "code", example = "oxaA11ulr9134oBL9Xscon5at_Gc")
    private String code;
    @Schema(description = "用户信息")
    private FullUserInfo userInfo;
}
