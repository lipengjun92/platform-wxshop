package com.platform.modules.app.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
@Schema(description = "绑定手机")
public class PhoneCodeRequest {
    @Schema(description = "手机号", example = "15209831990")
    private String mobile;
    @Schema(description = "验证码", example = "1234")
    private String mobileCode;
}
