package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API测试接口
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2017-03-23 15:47
 */
@Tag(name = "测试接口-AppTestController")
@RestController
@RequestMapping("/app/test")
public class AppTestController {

    @Autowired
    private WxUserService userService;

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息")
    @PostMapping("userInfo")
    public RestResponse<WxUserEntity> userInfo(@LoginUser WxUserEntity user) {
        return RestResponse.ok(user);
    }

    /**
     * 忽略Token验证测试
     */
    @Operation(summary = "忽略Token验证测试")
    @IgnoreAuth
    @PostMapping("notToken")
    public RestResponse<String> notToken() {
        return RestResponse.ok("无需token也能访问。。。");
    }

    /**
     * 根据手机号查询用户信息接口测试方法
     *
     * @param mobile
     * @return
     */
    @Operation(summary = "根据手机号查询用户信息")
    @IgnoreAuth
    @PostMapping("userListByMobile")
    public RestResponse<WxUserEntity> userList(String mobile) {
        WxUserEntity userEntity = userService.getOne(new QueryWrapper<WxUserEntity>().eq("PHONE", mobile), false);
        return RestResponse.ok(userEntity);
    }
}
