package com.platform.modules.app.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
@Schema(description = "手机号")
public class PhoneRequest {
    @Schema(description = "手机号", example = "15209831990")
    private String phone;
}
