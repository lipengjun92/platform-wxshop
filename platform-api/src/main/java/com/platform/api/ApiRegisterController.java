package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.service.ApiUserService;
import com.platform.utils.R;
import com.platform.validator.AbstractAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2017-03-26 17:27
 */
@Api(tags = "会员注册-ApiRegisterController")
@RestController
@RequestMapping("/api/register")
public class ApiRegisterController {
    @Autowired
    private ApiUserService userService;

    /**
     * 注册
     */
    @ApiOperation(value = "注册")
    @IgnoreAuth
    @PostMapping("register")
    public R register(String mobile, String password) {
        AbstractAssert.isBlank(mobile, "手机号不能为空");
        AbstractAssert.isBlank(password, "密码不能为空");

        userService.save(mobile, password);

        return R.ok();
    }
}
