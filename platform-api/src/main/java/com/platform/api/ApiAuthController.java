package com.platform.api;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.platform.annotation.IgnoreAuth;
import com.platform.entity.FullUserInfo;
import com.platform.entity.UserInfo;
import com.platform.entity.UserVo;
import com.platform.service.ApiUserService;
import com.platform.service.TokenService;
import com.platform.service.WeixinMaService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.CharUtil;
import com.platform.utils.R;
import com.platform.utils.ResourceUtil;
import com.platform.validator.AbstractAssert;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * API登录授权
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2017-03-23 15:31
 */
@Slf4j
@Api(tags = "登录授权-ApiAuthController")
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController extends ApiBaseAction {
    @Autowired
    private ApiUserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WeixinMaService weixinMaService;

    /**
     * 登录
     */
    @IgnoreAuth
    @PostMapping("login")
    @ApiOperation(value = "登录接口")
    public R login(String mobile, String password) {
        AbstractAssert.isBlank(mobile, "手机号不能为空");
        AbstractAssert.isBlank(password, "密码不能为空");

        //用户登录
        long userId = userService.login(mobile, password);

        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return R.ok(map);
    }

    /**
     * 微信登录
     */
    @ApiOperation(value = "微信登录")
    @IgnoreAuth
    @PostMapping("login_by_weixin")
    public Object loginByWeixin() {
        JSONObject jsonParam = this.getJsonRequest();
        FullUserInfo fullUserInfo = null;
        String code = "";
        if (!StringUtils.isNullOrEmpty(jsonParam.getString("code"))) {
            code = jsonParam.getString("code");
        }
        if (null != jsonParam.get("userInfo")) {
            fullUserInfo = jsonParam.getObject("userInfo", FullUserInfo.class);
        }
        if (null == fullUserInfo) {
            return toResponseFail("登录失败");
        }

        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        UserInfo userInfo = fullUserInfo.getUserInfo();

        try {
            WxMaJscode2SessionResult session = weixinMaService.getUserService().getSessionInfo(code);
            // 用户信息校验
            log.info("》》》微信返回sessionData：" + session.toString());

            if (!weixinMaService.getUserService().checkUserInfo(session.getSessionKey(), fullUserInfo.getRawData(), fullUserInfo.getSignature())) {
                log.error("登录失败：数据签名验证失败");
                return toResponseFail("登录失败");
            }

            // 解密用户信息
            WxMaUserInfo wxMpUser = weixinMaService.getUserService().getUserInfo(session.getSessionKey(), fullUserInfo.getEncryptedData(), fullUserInfo.getIv());
            log.info("》》》解密用户信息：" + wxMpUser.toString());

            Date nowTime = new Date();
            UserVo userVo = userService.queryByOpenId(session.getOpenid());
            if (null == userVo) {
                userVo = new UserVo();
                String name = "微信用户" + CharUtil.getRandomString(12);
                userVo.setUsername(name);
                userVo.setPassword(session.getOpenid());
                userVo.setRegisterTime(nowTime);
                userVo.setRegisterIp(this.getClientIp());
                userVo.setLastLoginIp(userVo.getRegisterIp());
                userVo.setLastLoginTime(nowTime);
                userVo.setWeixinOpenid(session.getOpenid());
                userVo.setAvatar("https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0");
                userVo.setNickname(name);
                userService.save(userVo);
            } else {
                userVo.setLastLoginIp(this.getClientIp());
                userVo.setLastLoginTime(nowTime);
                userService.update(userVo);
            }

            Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
            String token = MapUtils.getString(tokenMap, "token");

            if (null == userInfo || StringUtils.isNullOrEmpty(token)) {
                return toResponseFail("登录失败");
            }

            resultObj.put("token", token);
            resultObj.put("userInfo", userInfo);
            resultObj.put("userId", userVo.getUserId());
            return toResponseSuccess(resultObj);
        } catch (WxErrorException e) {
            log.error("登录失败：" + e.getMessage());
            return toResponseFail("登录失败");
        }
    }

    /**
     * code静默登录
     *
     * @param code code
     * @return RestResponse
     */
    @IgnoreAuth
    @GetMapping("/{code}")
    @ApiOperation(value = "微信静默登录", notes = "使用code静默登录")
    @ApiImplicitParam(required = true, paramType = "path", name = "code", value = "code", example = "oxaA11ulr9134oBL9Xscon5at_Gc", dataType = "string")
    public Object loginByCode(@PathVariable String code) {
        try {
            WxMaJscode2SessionResult session = weixinMaService.getUserService().getSessionInfo(code);

            String openid = session.getOpenid();

            Date nowTime = new Date();
            UserVo userVo = userService.queryByOpenId(openid);
            if (null == userVo) {
                userVo = new UserVo();
                String name = "微信用户" + CharUtil.getRandomString(12);
                userVo.setUsername(name);
                userVo.setPassword(openid);
                userVo.setRegisterTime(nowTime);
                userVo.setRegisterIp(this.getClientIp());
                userVo.setLastLoginIp(userVo.getRegisterIp());
                userVo.setLastLoginTime(nowTime);
                userVo.setWeixinOpenid(openid);
                userVo.setAvatar("https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0");
                userVo.setNickname(name);
                userService.save(userVo);
            } else {
                userVo.setLastLoginIp(this.getClientIp());
                userVo.setLastLoginTime(nowTime);
                userService.update(userVo);
            }

            Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
            String token = MapUtils.getString(tokenMap, "token");

            if (StringUtils.isNullOrEmpty(token)) {
                return toResponseFail("登录失败");
            }

            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("token", token);
            resultObj.put("userInfo", userVo);
            resultObj.put("userId", userVo.getUserId());
            return toResponseSuccess(resultObj);
        } catch (WxErrorException e) {
            log.error("登录失败", e);
            return toResponseFail("登录失败：" + e.getError().getErrorMsg());
        }
    }

    /**
     * 支付宝登录
     */
    @ApiOperation(value = "支付宝登录")
    @IgnoreAuth
    @PostMapping("login_by_ali")
    public Object loginByAli() {
        JSONObject jsonParam = this.getJsonRequest();
        String code = "";
        if (!StringUtils.isNullOrEmpty(jsonParam.getString("code"))) {
            code = jsonParam.getString("code");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(ResourceUtil.getConfigByName("ali.webAccessTokenhttps"), ResourceUtil.getConfigByName("ali.appId"), ResourceUtil.getConfigByName("ali.privateKey"),
                "json", "UTF-8", ResourceUtil.getConfigByName("ali.pubKey"), "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        try {
            //code 换取token
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            String accessToken = oauthTokenResponse.getAccessToken();

            //根据token获取用户头像、昵称等信息
            AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse userInfoResponse = alipayClient.execute(userInfoShareRequest, accessToken);

            Date nowTime = new Date();
            UserVo userVo = userService.queryByOpenId(userInfoResponse.getUserId());
            if (null == userVo) {
                userVo = new UserVo();
                userVo.setUsername("支付宝用户" + CharUtil.getRandomString(12));
                userVo.setPassword(userInfoResponse.getUserId());
                userVo.setRegisterTime(nowTime);
                userVo.setRegisterIp(this.getClientIp());
                userVo.setLastLoginIp(userVo.getRegisterIp());
                userVo.setLastLoginTime(nowTime);
                userVo.setWeixinOpenid(userInfoResponse.getUserId());
                userVo.setAvatar(userInfoResponse.getAvatar());
                //性别 0：未知、1：男、2：女
                //F：女性；M：男性
                userVo.setNickname(userInfoResponse.getNickName());
                userService.save(userVo);
            } else {
                userVo.setRegisterIp(this.getClientIp());
                userVo.setRegisterTime(nowTime);
                userService.update(userVo);
            }

            Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
            String token = MapUtils.getString(tokenMap, "token");

            if (StringUtils.isNullOrEmpty(token)) {
                return toResponseFail("登录失败");
            }

            Map<String, Object> resultObj = new HashMap<String, Object>();
            resultObj.put("token", token);
            resultObj.put("userInfo", userInfoResponse);
            resultObj.put("userId", userVo.getUserId());
            return toResponseSuccess(resultObj);
        } catch (AlipayApiException e) {
            return toResponseFail("登录失败");
        }
    }
}
