package com.platform.modules.app.vo;

import com.platform.modules.wx.entity.WxUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author pengjun
 */
@Data
@Schema(description = "登录返回信息")
public class LoginVo {
    @Schema(description = "用户信息")
    private WxUserEntity userInfo;

    @Schema(description = "token", example = "1")
    private String token;

    @Schema(description = "用户ID", example = "1")
    private String userId;
}
