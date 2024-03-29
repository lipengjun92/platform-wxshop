package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.LoginUser;
import com.platform.cache.J2CacheUtils;
import com.platform.entity.*;
import com.platform.service.*;
import com.platform.util.ApiBaseAction;
import com.platform.utils.CharUtil;
import com.platform.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * API优惠券管理
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2017-03-23 15:31
 */
@Api(tags = "优惠券-ApiCouponController")
@RestController
@RequestMapping("/api/coupon")
public class ApiCouponController extends ApiBaseAction {
    @Autowired
    private ApiUserService apiUserService;
    @Autowired
    private ApiCouponService apiCouponService;
    @Autowired
    private ApiUserCouponService apiUserCouponService;
    @Autowired
    private ApiProductService apiProductService;
    @Autowired
    private ApiCartService apiCartService;

    /**
     * 获取优惠券列表
     */
    @ApiOperation(value = "获取优惠券列表")
    @PostMapping("/list")
    public Object list(@LoginUser UserVo loginUser) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", loginUser.getUserId());
        List<CouponVo> couponVos = apiCouponService.queryUserCoupons(param);
        return toResponseSuccess(couponVos);
    }

    /**
     * 根据商品获取可用优惠券列表
     */
    @ApiOperation(value = "根据商品获取可用优惠券列表")
    @PostMapping("/listByGoods")
    public Object listByGoods(@RequestParam(defaultValue = "cart") String type, @LoginUser UserVo loginUser) {
        BigDecimal goodsTotalPrice = new BigDecimal("0.00");
        if ("cart".equals(type)) {
            Map<String, Object> param = new HashMap<>();
            param.put("userId", loginUser.getUserId());
            List<CartVo> cartList = apiCartService.queryList(param);
            //获取购物车统计信息
            for (CartVo cartItem : cartList) {
                if (null != cartItem.getChecked() && 1 == cartItem.getChecked()) {
                    goodsTotalPrice = goodsTotalPrice.add(cartItem.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
                }
            }
        } else { // 是直接购买的
            BuyGoodsVo goodsVo = (BuyGoodsVo) J2CacheUtils.get(J2CacheUtils.SHOP_CACHE_NAME, "goods" + loginUser.getUserId() + "");
            ProductVo productInfo = apiProductService.queryObject(goodsVo.getProductId());
            //商品总价
            goodsTotalPrice = productInfo.getRetailPrice().multiply(new BigDecimal(goodsVo.getNumber()));
        }

        // 获取可用优惠券
        Map<String, Object> param = new HashMap<>();
        param.put("userId", loginUser.getUserId());
        param.put("couponStatus", 1);
        List<CouponVo> couponVos = apiCouponService.queryUserCoupons(param);
        List<CouponVo> useCoupons = new ArrayList<>();
        List<CouponVo> notUseCoupons = new ArrayList<>();
        for (CouponVo couponVo : couponVos) {
            if (goodsTotalPrice.compareTo(couponVo.getMinGoodsAmount()) >= 0) { // 可用优惠券
                couponVo.setEnabled(1);
                useCoupons.add(couponVo);
            } else {
                couponVo.setEnabled(0);
                notUseCoupons.add(couponVo);
            }
        }
        useCoupons.addAll(notUseCoupons);
        return toResponseSuccess(useCoupons);
    }

    /**
     * 兑换优惠券
     */
    @ApiOperation(value = "领券优惠券")
    @PostMapping("exchange")
    public Object exchange(@LoginUser UserVo loginUser) {
        JSONObject jsonParam = getJsonRequest();
        String couponNumber = jsonParam.getString("couponNumber");
        if (StringUtils.isNullOrEmpty(couponNumber)) {
            return toResponseFail("当前优惠码无效");
        }
        //
        Map<String, Object> param = new HashMap<>();
        param.put("couponNumber", couponNumber);
        List<UserCouponVo> couponVos = apiUserCouponService.queryList(param);
        UserCouponVo userCouponVo = null;
        if (null == couponVos || couponVos.size() == 0) {
            return toResponseFail("当前优惠码无效");
        }
        userCouponVo = couponVos.get(0);
        if (null != userCouponVo.getUserId() && !userCouponVo.getUserId().equals(0L)) {
            return toResponseFail("当前优惠码已经兑换");
        }
        CouponVo couponVo = apiCouponService.queryObject(userCouponVo.getCouponId());
        if (null == couponVo || null == couponVo.getUseEndDate() || couponVo.getUseEndDate().before(new Date())) {
            return toResponseFail("当前优惠码已经过期");
        }
        userCouponVo.setUserId(loginUser.getUserId());
        userCouponVo.setAddTime(new Date());
        apiUserCouponService.update(userCouponVo);
        return toResponseSuccess(userCouponVo);
    }

    /**
     * 　　填写手机号码，领券
     */
    @ApiOperation(value = "领券优惠券")
    @PostMapping("newuser")
    public Object newuser(@LoginUser UserVo loginUser) {
        JSONObject jsonParam = getJsonRequest();
        //
        String phone = jsonParam.getString("phone");
        Integer smscode = jsonParam.getInteger("smscode");
        // 校验短信码
        SmsLogVo smsLogVo = apiUserService.querySmsCodeByUserId(loginUser.getUserId());
        if (null != smsLogVo && smsLogVo.getSmsCode() != smscode) {
            return toResponseFail("短信校验失败");
        }
        // 更新手机号码
        if (!StringUtils.isNullOrEmpty(phone)) {
            if (phone.equals(loginUser.getMobile())) {
                loginUser.setMobile(phone);
                apiUserService.update(loginUser);
            }
        }
        // 判断是否是新用户
        if (!StringUtils.isNullOrEmpty(loginUser.getMobile())) {
            return toResponseFail("当前优惠券只能新用户领取");
        }
        // 是否领取过了
        Map<String, Object> params = new HashMap<>();
        params.put("userId", loginUser.getUserId());
        params.put("sendType", 4);
        List<CouponVo> couponVos = apiCouponService.queryUserCoupons(params);
        if (null != couponVos && couponVos.size() > 0) {
            return toResponseFail("已经领取过，不能重复领取");
        }
        // 领取
        Map<String, Object> couponParam = new HashMap<>();
        couponParam.put("sendType", 4);
        CouponVo newCouponConfig = apiCouponService.queryMaxUserEnableCoupon(couponParam);
        if (null != newCouponConfig) {
            UserCouponVo userCouponVo = new UserCouponVo();
            userCouponVo.setAddTime(new Date());
            userCouponVo.setCouponId(newCouponConfig.getId());
            userCouponVo.setCouponNumber(CharUtil.getRandomString(12));
            userCouponVo.setUserId(loginUser.getUserId());
            apiUserCouponService.save(userCouponVo);
            return toResponseSuccess(userCouponVo);
        } else {
            return toResponseFail("领取失败");
        }
    }

    /**
     * 　　转发领取红包
     */
    @ApiOperation(value = "转发领取红包")
    @PostMapping("transActivit")
    public Object transActivit(@LoginUser UserVo loginUser, String sourceKey, Long referrer) {
        JSONObject jsonParam = getJsonRequest();
        // 是否领取过了
        Map<String, Object> params = new HashMap<>();
        params.put("userId", loginUser.getUserId());
        params.put("sendType", 2);
        params.put("sourceKey", sourceKey);
        List<CouponVo> couponVos = apiCouponService.queryUserCoupons(params);
        if (null != couponVos && couponVos.size() > 0) {
            return toResponsObject(2, "已经领取过", couponVos);
        }
        // 领取
        Map<String, Object> couponParam = new HashMap<>();
        couponParam.put("sendType", 2);
        CouponVo newCouponConfig = apiCouponService.queryMaxUserEnableCoupon(couponParam);
        if (null != newCouponConfig) {
            UserCouponVo userCouponVo = new UserCouponVo();
            userCouponVo.setAddTime(new Date());
            userCouponVo.setCouponId(newCouponConfig.getId());
            userCouponVo.setCouponNumber(CharUtil.getRandomString(12));
            userCouponVo.setUserId(loginUser.getUserId());
            userCouponVo.setSourceKey(sourceKey);
            userCouponVo.setReferrer(referrer);
            apiUserCouponService.save(userCouponVo);
            //
            List<UserCouponVo> userCouponVos = new ArrayList<>();
            userCouponVos.add(userCouponVo);
            //
            params = new HashMap<>();
            params.put("userId", loginUser.getUserId());
            params.put("sendType", 2);
            params.put("sourceKey", sourceKey);
            couponVos = apiCouponService.queryUserCoupons(params);
            return toResponseSuccess(couponVos);
        } else {
            return toResponseFail("领取失败");
        }
    }
}
