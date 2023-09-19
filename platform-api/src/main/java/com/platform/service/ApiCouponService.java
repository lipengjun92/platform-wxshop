package com.platform.service;

import com.platform.dao.ApiCouponMapper;
import com.platform.entity.CouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ApiCouponService {
    @Autowired
    private ApiCouponMapper apiCouponMapper;

    public CouponVo queryObject(Integer couponId) {
        return apiCouponMapper.queryObject(couponId);
    }

    public List<CouponVo> queryList(Map<String, Object> map) {
        return apiCouponMapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return apiCouponMapper.queryTotal(map);
    }


    public void save(CouponVo userVo) {
        apiCouponMapper.save(userVo);
    }

    public void update(CouponVo user) {
        apiCouponMapper.update(user);
    }

    public void delete(Long userId) {
        apiCouponMapper.delete(userId);
    }

    public void deleteBatch(Long[] userIds) {
        apiCouponMapper.deleteBatch(userIds);
    }

    public List<CouponVo> queryUserCoupons(Map<String, Object> map) {
        // 检查优惠券是否过期
        List<CouponVo> couponVos = apiCouponMapper.queryUserCoupons(map);
        for (CouponVo couponVo : couponVos) {
            if (couponVo.getCouponStatus()==1) {
                // 检查是否过期
                if(couponVo.getUseEndDate().before(new Date())) {
                    couponVo.setCouponStatus(3);
                    apiCouponMapper.updateUserCoupon(couponVo);
                }
            }
            if (couponVo.getCouponStatus()==3) {
                // 检查是否不过期
                if(couponVo.getUseEndDate().after(new Date())) {
                    couponVo.setCouponStatus(1);
                    apiCouponMapper.updateUserCoupon(couponVo);
                }
            }
        }

        return couponVos;
    }

    public CouponVo queryMaxUserEnableCoupon(Map<String, Object> map) {
        return apiCouponMapper.queryMaxUserEnableCoupon(map);
    }

    public List<CouponVo> queryUserCouponList(Map<String, Object> map) {
        return apiCouponMapper.queryUserCouponList(map);
    }
}
