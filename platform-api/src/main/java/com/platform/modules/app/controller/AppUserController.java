package com.platform.modules.app.controller;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.*;
import com.platform.config.RedisTemplateUtil;
import com.platform.modules.app.request.ApiUserBindMobileRequest;
import com.platform.modules.app.request.ApiUserSmsCodeRequest;
import com.platform.modules.sys.entity.SmsConfig;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员操作接口
 * @author pengjun
 */
@Tag(name = "会员操作-AppUserController")
@RestController
@RequestMapping("/app/user")
public class AppUserController extends AppBaseController {
    private final WxUserService userService;
    private final SysConfigService sysConfigService;
    private final RedisTemplateUtil redisTemplateUtil;

    public AppUserController(WxUserService userService, SysConfigService sysConfigService, RedisTemplateUtil redisTemplateUtil) {
        this.userService = userService;
        this.sysConfigService = sysConfigService;
        this.redisTemplateUtil = redisTemplateUtil;
    }

    /**
     * 发送短信验证码
     */
    @Operation(summary = "发送短信")
    @PostMapping("smscode")
    public RestResponse<String> smscode(@LoginUser WxUserEntity loginUser, @RequestBody ApiUserSmsCodeRequest request) {
        String phone = request.getPhone();
        if (StringUtils.isNullOrEmpty(phone)) {
            return RestResponse.fail("手机号不能为空");
        }
        String cacheKey = Constant.PRE_SMS + loginUser.getId();
        if (redisTemplateUtil.get(cacheKey) != null) {
            return RestResponse.fail("短信已发送");
        }
        String smsCode = CharUtil.getRandomNum(4);
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            return RestResponse.fail("请先配置短信平台信息");
        }
        if (StringUtils.isNullOrEmpty(config.getAppid())) {
            return RestResponse.fail("请先配置短信平台APPID");
        }
        if (StringUtils.isNullOrEmpty(config.getAppkey())) {
            return RestResponse.fail("请先配置短信平台KEY");
        }
        if (StringUtils.isNullOrEmpty(config.getSign())) {
            return RestResponse.fail("请先配置短信平台签名");
        }
        try {
            SmsSingleSenderResult result = SmsUtil.crSendSms(config.getAppid(), config.getAppkey(), 0, "86", phone, smsCode);
            if (result.result != 0) {
                return RestResponse.fail("短信发送失败");
            }
        } catch (Exception e) {
            return RestResponse.fail("短信发送失败");
        }
        redisTemplateUtil.set(cacheKey, smsCode, 60);
        return RestResponse.ok("短信发送成功");
    }

    /**
     * 获取当前会员等级
     */
    @Operation(summary = "获取当前会员等级")
    @PostMapping("getUserLevel")
    public RestResponse<String> getUserLevel(@LoginUser WxUserEntity loginUser) {
        return RestResponse.ok(StringUtils.isNullOrEmpty(loginUser.getUserLevelId()) ? "普通会员" : loginUser.getUserLevelId());
    }

    /**
     * 绑定手机
     */
    @Operation(summary = "绑定手机")
    @PostMapping("bindMobile")
    public RestResponse<String> bindMobile(@LoginUser WxUserEntity loginUser, @RequestBody ApiUserBindMobileRequest request) {
        String mobileCode = request.getMobileCode();
        String mobile = request.getMobile();
        if (StringUtils.isNullOrEmpty(mobile) || StringUtils.isNullOrEmpty(mobileCode)) {
            return RestResponse.fail("参数不完整");
        }
        String cacheKey = Constant.PRE_SMS + loginUser.getId();
        Object smsCode = redisTemplateUtil.get(cacheKey);
        if (smsCode == null || !mobileCode.equals(smsCode.toString())) {
            return RestResponse.fail("验证码错误");
        }
        WxUserEntity userEntity = userService.getById(loginUser.getId());
        if (userEntity == null) {
            return RestResponse.fail("用户不存在");
        }
        userEntity.setPhone(mobile);
        userService.updateById(userEntity);
        redisTemplateUtil.del(cacheKey);
        return RestResponse.ok("手机绑定成功");
    }
}
