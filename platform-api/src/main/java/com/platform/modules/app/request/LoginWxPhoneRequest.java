package com.platform.modules.app.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
@Schema(description = "微信手机号授权")
public class LoginWxPhoneRequest {
    @Schema(description = "code", example = "oxaA11ulr9134oBL9Xscon5at_Gc")
    private String code;
    @Schema(description = "用户ID", example = "1")
    private String userId;
}
