package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-03-26 17:27
 */
@Tag(name = "会员注册-AppRegisterController")
@RestController
@RequestMapping("/app/register")
public class AppRegisterController {
    @Autowired
    private WxUserService userService;

    /**
     * 注册
     */
    @Operation(summary = "注册")
    @IgnoreAuth
    @PostMapping("register")
    public RestResponse<String> register(String mobile, String password) {
        AbstractAssert.isBlank(mobile, "手机号不能为空");
        AbstractAssert.isBlank(password, "密码不能为空");

        WxUserEntity wxUserEntity = new WxUserEntity();
        wxUserEntity.setPhone(mobile);
        userService.save(wxUserEntity);

        return RestResponse.ok("注册成功");
    }
}
