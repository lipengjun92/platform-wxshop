/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.config.RedisTemplateUtil;
import com.platform.modules.mall.dao.*;
import com.platform.modules.mall.dto.BuyGoodsRequest;
import com.platform.modules.mall.entity.*;
import com.platform.modules.mall.service.MallOrderService;
import com.platform.modules.wx.entity.WxUserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单服务实现。
 * <p>
 * 基于 MyBatis-Plus 的 ServiceImpl 提供订单模块的 CRUD 与分页查询实现。
 * </p>
 *
 * @author 李鹏军
 * @since 2026-03-15 14:30:00
 */
@Service("mallOrderService")
public class MallOrderServiceImpl extends ServiceImpl<MallOrderDao, MallOrderEntity> implements MallOrderService {
    @Autowired
    private MallAddressDao addressDao;
    @Autowired
    private MallCartDao cartDao;
    @Autowired
    private MallProductDao productDao;
    @Autowired
    private MallOrderGoodsDao orderGoodsDao;
    @Autowired
    private MallCouponDao couponDao;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    /**
     * 查询订单列表。
     */
    @Override
    public List<MallOrderEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    /**
     * 按 QueryWrapper 查询订单列表。
     */
    @Override
    public List<MallOrderEntity> queryAllByWrapper(QueryWrapper<MallOrderEntity> queryWrapper) {
        return this.list(queryWrapper);
    }

    /**
     * 分页查询订单，并按 ID 倒序。
     */
    @Override
    public Page<MallOrderEntity> queryPage(Map<String, Object> params) {
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MallOrderEntity> page = new Query<MallOrderEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMallOrderPage(page, params));
    }

    /**
     * 新增订单。
     */
    @Override
    public boolean add(MallOrderEntity mallOrder) {
        return this.save(mallOrder);
    }

    /**
     * 修改订单。
     */
    @Override
    public boolean update(MallOrderEntity mallOrder) {
        return this.updateById(mallOrder);
    }

    /**
     * 删除订单。
     */
    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    /**
     * 批量删除订单。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional
    public Map<String, Object> submit(JSONObject jsonParam, WxUserEntity loginUser) {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        Integer couponId = jsonParam.getInteger("couponId");
        String type = jsonParam.getString("type");
        String postscript = jsonParam.getString("postscript");
        MallAddressEntity addressVo = addressDao.selectById(jsonParam.getInteger("addressId"));

        Integer freightPrice = 0;

        // * 获取要购买的商品
        List<MallCartEntity> checkedGoodsList = new ArrayList<>();
        BigDecimal goodsTotalPrice;
        if ("cart".equals(type)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("USER_ID", loginUser.getId());
            param.put("SESSION_ID", 1);
            param.put("CHECKED", 1);
            checkedGoodsList = cartDao.selectByMap(param);
            if (null == checkedGoodsList) {
                resultObj.put("code", 400);
                resultObj.put("msg", "请选择商品");
                return resultObj;
            }
            //统计商品总价
            goodsTotalPrice = new BigDecimal(0.00);
            for (MallCartEntity cartItem : checkedGoodsList) {
                goodsTotalPrice = goodsTotalPrice.add(cartItem.getRetailPrice().multiply(new BigDecimal(cartItem.getNumber())));
            }
        } else {
            BuyGoodsRequest goodsVo = (BuyGoodsRequest) redisTemplateUtil.get(Constant.MTM_CACHE + "goods" + loginUser.getId());
            MallProductEntity productInfo = productDao.selectById(goodsVo.getProductId());
            //计算订单的费用
            //商品总价
            goodsTotalPrice = productInfo.getRetailPrice().multiply(new BigDecimal(goodsVo.getNumber()));

            MallCartEntity MallCartEntity = new MallCartEntity();
            BeanUtils.copyProperties(productInfo, MallCartEntity);
            MallCartEntity.setNumber(goodsVo.getNumber());
            MallCartEntity.setProductId(goodsVo.getProductId());
            checkedGoodsList.add(MallCartEntity);
        }


        //获取订单使用的优惠券
        BigDecimal couponPrice = new BigDecimal("0.00");
        MallCouponEntity couponVo = null;
        if (couponId != null && couponId != 0) {
            couponVo = couponDao.getUserCoupon(couponId, loginUser.getId());
            if (couponVo != null && couponVo.getCouponStatus() == 1) {
                couponPrice = couponVo.getTypeMoney();
            }
        }

        //订单价格计算
        BigDecimal orderTotalPrice = goodsTotalPrice.add(new BigDecimal(freightPrice));

        BigDecimal actualPrice = orderTotalPrice.subtract(couponPrice);

        //
        MallOrderEntity orderInfo = new MallOrderEntity();
        orderInfo.setOrderSn(StringUtils.generateOrderNumber());
        orderInfo.setUserId(loginUser.getId());
        //收货地址和运费
        orderInfo.setConsignee(addressVo.getUserName());
        orderInfo.setMobile(addressVo.getTelNumber());
        orderInfo.setCountry(addressVo.getNationalCode());
        orderInfo.setProvince(addressVo.getProvinceName());
        orderInfo.setCity(addressVo.getCityName());
        orderInfo.setDistrict(addressVo.getCountyName());
        orderInfo.setAddress(addressVo.getDetailInfo());
        //
        orderInfo.setFreightPrice(freightPrice);
        //留言
        orderInfo.setPostscript(postscript);
        //使用的优惠券
        orderInfo.setCouponId(couponId);
        orderInfo.setCouponPrice(couponPrice);
        orderInfo.setAddTime(new Date());
        orderInfo.setGoodsPrice(goodsTotalPrice);
        orderInfo.setOrderPrice(orderTotalPrice);
        orderInfo.setActualPrice(actualPrice);
        // 待付款
        orderInfo.setOrderStatus(0);
        orderInfo.setShippingStatus(0);
        orderInfo.setPayStatus(0);
        orderInfo.setShippingId(0);
        orderInfo.setShippingFee(new BigDecimal(0));
        orderInfo.setIntegral(0);
        orderInfo.setIntegralMoney(new BigDecimal(0));
        if ("cart".equals(type)) {
            orderInfo.setOrderType("1");
        } else {
            orderInfo.setOrderType("4");
        }

        //开启事务，插入订单信息和订单商品
        baseMapper.insert(orderInfo);
        if (null == orderInfo.getId()) {
            resultObj.put("code", 1);
            resultObj.put("msg", "订单提交失败");
            return resultObj;
        }
        //统计商品总价
        List<MallOrderGoodsEntity> orderGoodsData = new ArrayList<MallOrderGoodsEntity>();
        for (MallCartEntity goodsItem : checkedGoodsList) {
            MallOrderGoodsEntity orderGoodsVo = new MallOrderGoodsEntity();
            orderGoodsVo.setOrderId(orderInfo.getId());
            orderGoodsVo.setGoodsId(goodsItem.getGoodsId());
            orderGoodsVo.setGoodsSn(goodsItem.getGoodsSn());
            orderGoodsVo.setProductId(goodsItem.getProductId());
            orderGoodsVo.setGoodsName(goodsItem.getGoodsName());
            orderGoodsVo.setListPicUrl(goodsItem.getListPicUrl());
            orderGoodsVo.setMarketPrice(goodsItem.getMarketPrice());
            orderGoodsVo.setRetailPrice(goodsItem.getRetailPrice());
            orderGoodsVo.setNumber(goodsItem.getNumber());
            orderGoodsVo.setGoodsSpecifitionNameValue(goodsItem.getGoodsSpecifitionNameValue());
            orderGoodsVo.setGoodsSpecifitionIds(goodsItem.getGoodsSpecifitionIds());
            orderGoodsData.add(orderGoodsVo);
            orderGoodsDao.insert(orderGoodsVo);
        }

        //清空已购买的商品
        cartDao.deleteByCart(loginUser.getId(), 1, 1);
        resultObj.put("code", 0);
        resultObj.put("msg", "订单提交成功");
        //
        Map<String, MallOrderEntity> orderInfoMap = new HashMap<>();
        orderInfoMap.put("orderInfo", orderInfo);
        //
        resultObj.put("data", orderInfoMap);
        // 优惠券标记已用
        if (couponVo != null && couponVo.getCouponStatus() == 1) {
            couponVo.setCouponStatus(2);
            couponDao.updateUserCoupon(couponVo);
        }

        return resultObj;
    }

    @Override
    public List<MallOrderEntity> queryList(Map<String, Object> params) {
        return baseMapper.queryList(params);
    }
}
