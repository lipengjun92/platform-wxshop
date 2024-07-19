package com.platform.modules.app.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
@Schema(description = "微信公众号登录")
public class LoginWxMpRequest {
    @Schema(description = "code", example = "oxaA11ulr9134oBL9Xscon5at_Gc")
    private String code;
    @Schema(description = "公众号appId", example = "wxeca4341756496160")
    private String mpAppId;
}
