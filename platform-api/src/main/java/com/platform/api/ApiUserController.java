package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.entity.SmsLogVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiUserService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.CharUtil;
import com.platform.utils.sms.SmsAlidayu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends ApiBaseAction {
    @Autowired
    private ApiUserService userService;

    /**
     */
    @IgnoreAuth
    @RequestMapping("info")
    public Object info(@LoginUser UserVo loginUser, String mobile) {
        Map param = new HashMap();
        param.put("mobile", mobile);
        UserVo user = userService.queryByMobile(mobile);
        user.setPassword("");
        return user;
    }

    /**
     * 保存用户头像
     */
    @RequestMapping("saveAvatar")
    public Object saveAvatar(@LoginUser UserVo loginUser, String avatar) {
        return null;
    }

    /**
     * 发送短信
     */
    @RequestMapping("smscode")
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
        String smsTemplateCode = "SMS_94340007";
        String msgContent = "您的验证码是：" + sms_code + "，请在页面中提交验证码完成验证。";
        String param = "{\"code\":\"" + sms_code + "\"}";
        // 发送短信
//        String rpt = "0";
        String rpt = SmsAlidayu.sendTplShortMessage(param, phone, smsTemplateCode);
        if (rpt == null || rpt.equals("0") == false) {
            return toResponsFail("短信发送失败");
        } else {
            smsLogVo = new SmsLogVo();
            smsLogVo.setLog_date(System.currentTimeMillis() / 1000);
            smsLogVo.setUser_id(loginUser.getUserId());
            smsLogVo.setPhone(phone);
            smsLogVo.setSms_code(sms_code);
            smsLogVo.setSms_text(msgContent);
            userService.saveSmsCodeLog(smsLogVo);
            return toResponsSuccess("短信发送成功");
        }
    }

    /**
     * 获取当前会员等级
     *
     * @param loginUser
     * @return
     */
    @RequestMapping("getUserLevel")
    public Object getUserLevel(@LoginUser UserVo loginUser) {
        String userLevel = userService.getUserLevel(loginUser);
        return toResponsSuccess(userLevel);
    }
}