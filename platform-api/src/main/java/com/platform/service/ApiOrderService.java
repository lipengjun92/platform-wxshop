package com.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.platform.dao.*;
import com.platform.entity.*;
import com.platform.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ApiOrderService {
    @Autowired
    private ApiOrderMapper orderDao;
    @Autowired
    private ApiAddressMapper apiAddressMapper;
    @Autowired
    private ApiCartMapper apiCartMapper;
    @Autowired
    private ApiCouponMapper apiCouponMapper;
    @Autowired
    private ApiOrderMapper apiOrderMapper;
    @Autowired
    private ApiOrderGoodsMapper apiOrderGoodsMapper;
    @Autowired
    private ApiUserCouponMapper apiUserCouponMapper;


    public OrderVo queryObject(Integer id) {
        return orderDao.queryObject(id);
    }


    public List<OrderVo> queryList(Map<String, Object> map) {
        return orderDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return orderDao.queryTotal(map);
    }


    public void save(OrderVo order) {
        orderDao.save(order);
    }


    public void update(OrderVo order) {
        orderDao.update(order);
    }


    public void delete(Integer id) {
        orderDao.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        orderDao.deleteBatch(ids);
    }


    @Transactional
    public Map submit(JSONObject jsonParam, UserVo loginUser) {
        Map resultObj = new HashMap();

        Integer couponId = jsonParam.getInteger("couponId");
        String couponNumber = jsonParam.getString("couponNumber");
        BigDecimal fullCutCouponDec = jsonParam.getBigDecimal("fullCutCouponDec");
        if(fullCutCouponDec == null ){
            fullCutCouponDec = BigDecimal.valueOf(0);
        }
        String postscript = jsonParam.getString("postscript");
//        AddressVo addressVo = jsonParam.getObject("checkedAddress",AddressVo.class);
        AddressVo addressVo = apiAddressMapper.queryObject(jsonParam.getInteger("addressId"));

        Integer freightPrice = 10;
        //获取要购买的商品
        Map param = new HashMap();
        param.put("user_id", loginUser.getUserId());
        param.put("session_id", 1);
        param.put("checked", 1);
        List<CartVo> checkedGoodsList = apiCartMapper.queryList(param);
        if (null == checkedGoodsList) {
            resultObj.put("errno", 400);
            resultObj.put("errmsg", "请选择商品");
            return resultObj;
        }
        //统计商品总价
        BigDecimal goodsTotalPrice = new BigDecimal(0.00);
        for (CartVo cartItem : checkedGoodsList) {
            goodsTotalPrice = goodsTotalPrice.add(cartItem.getRetail_price().multiply(new BigDecimal(cartItem.getNumber())));
        }

        //获取订单使用的优惠券
        BigDecimal couponPrice = new BigDecimal(0.00);
        if (null != couponId && 0 != couponId) {
            CouponVo couponVo = apiCouponMapper.queryObject(couponId);
            if (null != couponVo && null != couponVo.getType_money()) {
                couponPrice = couponVo.getType_money();
            }
        }
        // 获取优惠信息提示
        Map couponParam = new HashMap();
        couponParam.put("enabled", true);
        Integer[] send_types = new Integer[]{7};
        couponParam.put("send_types", send_types);
        List<CouponVo> couponVos = apiCouponMapper.queryList(couponParam);
        if (null != couponVos && couponVos.size() > 0) {
            for (CouponVo couponVo : couponVos) {
                // 是否免运费
                if (couponVo.getSend_type() == 7 && couponVo.getMin_amount().compareTo(goodsTotalPrice) <= 0) {
                    freightPrice = 0;
                }
            }
        }
        //订单价格计算
        BigDecimal orderTotalPrice = goodsTotalPrice.add(new BigDecimal(freightPrice)); //订单的总价

        BigDecimal actualPrice = orderTotalPrice.subtract(fullCutCouponDec).subtract(couponPrice);  //减去其它支付的金额后，要实际支付的金额

        Long currentTime = System.currentTimeMillis() / 1000;

        //
        OrderVo orderInfo = new OrderVo();
        orderInfo.setOrder_sn(CommonUtil.generateOrderNumber());
        orderInfo.setUser_id(loginUser.getUserId());
        //收货地址和运费
        orderInfo.setConsignee(addressVo.getUserName());
        orderInfo.setMobile(addressVo.getTelNumber());
        orderInfo.setCountry(addressVo.getNationalCode());
        orderInfo.setProvince(addressVo.getProvinceName());
        orderInfo.setCity(addressVo.getCityName());
        orderInfo.setDistrict(addressVo.getCountyName());
        orderInfo.setAddress(addressVo.getDetailInfo());
        //
        orderInfo.setFreight_price(freightPrice);
        //留言
        orderInfo.setPostscript(postscript);
        //使用的优惠券
        orderInfo.setFull_cut_price(fullCutCouponDec);
        orderInfo.setCoupon_id(couponId);
        orderInfo.setCoupon_price(couponPrice);
        orderInfo.setAdd_time(new Date());
        orderInfo.setGoods_price(goodsTotalPrice);
        orderInfo.setOrder_price(orderTotalPrice);
        orderInfo.setActual_price(actualPrice);
        // 待付款
        orderInfo.setOrder_status(0);
        orderInfo.setShipping_status(0);
        orderInfo.setPay_status(0);
        orderInfo.setShipping_id(0);
        orderInfo.setShipping_fee(new BigDecimal(0));
        orderInfo.setIntegral(0);
        orderInfo.setIntegral_money(new BigDecimal(0));

        //开启事务，插入订单信息和订单商品
        apiOrderMapper.save(orderInfo);
        if (null == orderInfo.getId()) {
            resultObj.put("errno", 1);
            resultObj.put("errmsg", "订单提交失败");
            return resultObj;
        }
        //统计商品总价
        List<OrderGoodsVo> orderGoodsData = new ArrayList();
        for (CartVo goodsItem : checkedGoodsList) {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            orderGoodsVo.setOrder_id(orderInfo.getId());
            orderGoodsVo.setGoods_id(goodsItem.getGoods_id());
            orderGoodsVo.setGoods_sn(goodsItem.getGoods_sn());
            orderGoodsVo.setProduct_id(goodsItem.getProduct_id());
            orderGoodsVo.setGoods_name(goodsItem.getGoods_name());
            orderGoodsVo.setList_pic_url(goodsItem.getList_pic_url());
            orderGoodsVo.setMarket_price(goodsItem.getMarket_price());
            orderGoodsVo.setRetail_price(goodsItem.getRetail_price());
            orderGoodsVo.setNumber(goodsItem.getNumber());
            orderGoodsVo.setGoods_specifition_name_value(goodsItem.getGoods_specifition_name_value());
            orderGoodsVo.setGoods_specifition_ids(goodsItem.getGoods_specifition_ids());
            orderGoodsData.add(orderGoodsVo);
            apiOrderGoodsMapper.save(orderGoodsVo);
        }

        //清空已购买的商品
        apiCartMapper.deleteByCart(loginUser.getUserId(), 1, 1);
        resultObj.put("errno", 0);
        resultObj.put("errmsg", "订单提交成功");
        //
        Map orderInfoMap = new HashMap();
        orderInfoMap.put("orderInfo", orderInfo);
        //
        resultObj.put("data", orderInfoMap);
        // 优惠券标记已用
        if (!StringUtils.isEmpty(couponNumber)) {
            UserCouponVo userCouponVo = apiUserCouponMapper.queryByCouponNumber(couponNumber);
            if (null != userCouponVo && null == userCouponVo.getOrder_id()) {
                userCouponVo.setUsed_time(new Date());
                userCouponVo.setOrder_id(orderInfo.getId());
                apiUserCouponMapper.update(userCouponVo);
            }
        }
        return resultObj;
    }

}
