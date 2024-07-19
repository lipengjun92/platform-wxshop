package com.platform.modules.app.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;

/**
 * @author pengjun
 */
@Data
@Schema(description = "APP登录")
public class LoginAppRequest {
    @Schema(description = "用户信息")
    private WxOAuth2AccessToken userInfo;
}
