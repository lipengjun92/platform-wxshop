package com.platform.service;

import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.platform.utils.RRException;
import com.platform.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author pengjun
 */
@Slf4j
@Service
public class WeixinPayService extends WxPayServiceImpl {
    @PostConstruct
    public void init() {
        final WxPayConfig config = new WxPayConfig();
        config.setMchId(ResourceUtil.getConfigByName("wx.mchId"));
        config.setMchKey(ResourceUtil.getConfigByName("wx.paySignKey"));
        config.setAppId(ResourceUtil.getConfigByName("wx.appId"));
        config.setTradeType(ResourceUtil.getConfigByName("wx.tradeType"));
        config.setKeyPath(ResourceUtil.getConfigByName("wx.certName"));
        config.setSignType(WxPayConstants.SignType.MD5);
        super.setConfig(config);
    }

    /**
     * 取消订单
     *
     * @param outTradeNo  订单变化
     * @param orderMoney  支付订单金额
     * @param refundMoney 退款金额（小于支付订单金额）
     * @return WxPayRefundResult
     */
    public WxPayRefundResult refund(String outTradeNo, Double orderMoney, Double refundMoney) {
        WxPayRefundRequest request = new WxPayRefundRequest();
        request.setOutTradeNo(outTradeNo);
        request.setOutRefundNo(outTradeNo);
        request.setTotalFee(BaseWxPayRequest.yuanToFen(orderMoney.toString()));
        request.setRefundFee(BaseWxPayRequest.yuanToFen(refundMoney.toString()));
        request.setRefundDesc("用户取消订单");

        try {
            return refund(request);
        } catch (WxPayException e) {
            log.error("退款失败：" + e.getMessage());
            throw new RRException("退款失败：" + e.getMessage());
        }
    }
}
