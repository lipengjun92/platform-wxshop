package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.platform.annotation.LoginUser;
import com.platform.entity.SmsConfig;
import com.platform.entity.SmsLogVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiUserService;
import com.platform.service.SysConfigService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.CharUtil;
import com.platform.utils.Constant;
import com.platform.utils.SmsUtil;
import com.platform.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * 描述: ApiIndexController <br>
 */
@Api(tags = "会员验证")
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends ApiBaseAction {
    @Autowired
    private ApiUserService userService;
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 发送短信
     */
    @ApiOperation(value = "发送短信")
    @PostMapping("smscode")
    public Object smscode(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        String phone = jsonParams.getString("phone");
        // 一分钟之内不能重复发送短信
        SmsLogVo smsLogVo = userService.querySmsCodeByUserId(loginUser.getUserId());
        if (null != smsLogVo && (System.currentTimeMillis() / 1000 - smsLogVo.getLog_date()) < 1 * 60) {
            return toResponsFail("短信已发送");
        }
        //生成验证码
        String sms_code = CharUtil.getRandomNum(4);
        //获取云存储配置信息
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            return toResponsFail("请先配置短信平台信息");
        }
        if (StringUtils.isNullOrEmpty(config.getAppid())) {
            return toResponsFail("请先配置短信平台APPID");
        }
        if (StringUtils.isNullOrEmpty(config.getAppkey())) {
            return toResponsFail("请先配置短信平台KEY");
        }
        if (StringUtils.isNullOrEmpty(config.getSign())) {
            return toResponsFail("请先配置短信平台签名");
        }
        // 发送短信
        SmsSingleSenderResult result;
        int templateId = 23;
        try {
            result = SmsUtil.crSendSms(config.getAppid(), config.getAppkey(), phone, templateId, new String[]{sms_code}, "");
        } catch (Exception e) {
            return toResponsFail("短信发送失败");
        }

        if (result.result == 0) {
            smsLogVo = new SmsLogVo();
            smsLogVo.setLog_date(System.currentTimeMillis() / 1000);
            smsLogVo.setUser_id(loginUser.getUserId());
            smsLogVo.setPhone(phone);
            smsLogVo.setSms_code(templateId);
            smsLogVo.setSms_text(sms_code);
            userService.saveSmsCodeLog(smsLogVo);
            return toResponsSuccess("短信发送成功");
        } else {
            return toResponsFail("短信发送失败");
        }
    }

    /**
     * 获取当前会员等级
     *
     * @param loginUser
     * @return
     */
    @ApiOperation(value = "获取当前会员等级")
    @PostMapping("getUserLevel")
    public Object getUserLevel(@LoginUser UserVo loginUser) {
        String userLevel = userService.getUserLevel(loginUser);
        return toResponsSuccess(userLevel);
    }

    /**
     * 绑定手机
     */
    @ApiOperation(value = "绑定手机")
    @PostMapping("bindMobile")
    public Object bindMobile(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        SmsLogVo smsLogVo = userService.querySmsCodeByUserId(loginUser.getUserId());

        String mobile_code = jsonParams.getString("mobile_code");
        String mobile = jsonParams.getString("mobile");

        if (!mobile_code.equals(smsLogVo.getSms_code())) {
            return toResponsFail("验证码错误");
        }
        UserVo userVo = userService.queryObject(loginUser.getUserId());
        userVo.setMobile(mobile);
        userService.update(userVo);
        return toResponsSuccess("手机绑定成功");
    }
}
