package com.platform.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.*;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author 李鹏军
 */
@Service
public class AliPayService {
    @Value("${ali.pay.appId}")
    private String appId;
    @Value("${ali.pay.rsaPublicKey}")
    private String rsaPublicKey;
    @Value("${ali.pay.merchantPrivateKey}")
    private String merchantPrivateKey;
    @Value("${ali.pay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${ali.pay.signType}")
    private String signType;
    @Value("${ali.pay.gatewayHost}")
    private String gatewayHost;
    @Value("${ali.pay.protocol}")
    private String protocol;
    @Value("${ali.pay.baseNotifyUrl}")
    private String baseNotifyUrl;
    @Value("${ali.pay.encryptKey}")
    private String encryptKey;

    @PostConstruct
    public void loadConfigStorages() {
        Config config = new Config();
        // 网关协议
        config.protocol = protocol;
        // 网关地址
        config.gatewayHost = gatewayHost;
        // 密钥加密方式
        config.signType = signType;
        // 应用识别码APPID
        config.appId = appId;
        // 应用私钥
        config.merchantPrivateKey = merchantPrivateKey;
        // 支付宝公钥
        config.alipayPublicKey = alipayPublicKey;
        // AES密钥
        config.encryptKey = encryptKey;
        // 异步通知回调地址
        config.notifyUrl = baseNotifyUrl + "/app/pay/aliNotify";
        // Factory全局只需配置一次
        Factory.setOptions(config);
    }

    /**
     * 对应 alipay.trade.app.pay 接口
     * 构造交易数据供商户app到支付宝下单
     */
    public AlipayTradeAppPayResponse createAppTradeForm(String subject, String tradeNo, String totalAmount, String timeoutExpress) throws Exception {
        return Factory.Payment.App()
                //单独设置超时时间 默认30分钟
                .optional("timeout_express", timeoutExpress)
                // 设置异步通知地址，也可以在全局设置，已经在全局设置
                // 此处设置的异步通知地址的优先级高于全局Config中配置的异步通知地址，并且此处设置将在本调用中覆盖Config中的全局配置
//                .asyncNotify(baseNotifyUrl + "/app/pay/aliNotify")
                .pay(subject, tradeNo, totalAmount);
    }

    /**
     * 对应alipay.trade.page.pay 接口
     * 构造交易数据供pc端到支付宝下单
     */
    public AlipayTradePagePayResponse createWebTradeForm(String subject, String tradeNo, String totalAmount, String returnUrl, String timeoutExpress) throws Exception {
        return Factory.Payment.Page()
                //单独设置超时时间 默认30分钟
                .optional("timeout_express", timeoutExpress)
                .pay(subject, tradeNo, totalAmount, returnUrl);
    }

    /**
     * alipay.trade.wap.pay
     * 构造交易数据供wap端到支付宝下单
     */
    public AlipayTradeWapPayResponse createWapTradeForm(String subject, String tradeNo, String totalAmount, String returnUrl, String timeoutExpress) throws Exception {
        return Factory.Payment.Wap()
                //单独设置超时时间 默认30分钟
                .optional("timeout_express", timeoutExpress)
                .pay(subject, tradeNo, totalAmount, returnUrl, returnUrl);
    }

    /**
     * alipay.trade.create
     * 构造交易数据供小程序端到支付宝下单
     */
    public AlipayTradeCreateResponse create(String subject, String tradeNo, String totalAmount, String buyerId, String timeoutExpress) throws Exception {
        return Factory.Payment.Common()
                //单独设置超时时间 默认30分钟
                .optional("timeout_express", timeoutExpress)
                .create(subject, tradeNo, totalAmount, buyerId);
    }

    /**
     * 对应alipay.trade.query(统一收单线下交易查询)
     */
    public AlipayTradeQueryResponse queryTrade(String tradeNo) throws Exception {
        return Factory.Payment.Common().query(tradeNo);
    }

    /**
     * alipay.trade.cancel
     */
    public AlipayTradeCancelResponse cancelTrade(String tradeNo) throws Exception {
        return Factory.Payment.Common().cancel(tradeNo);
    }

    /**
     * alipay.trade.close(统一收单交易关闭接口)
     */
    public AlipayTradeCloseResponse closeTrade(String tradeNo) throws Exception {
        return Factory.Payment.Common().close(tradeNo);
    }

    /**
     * alipay.trade.refund(统一收单交易退款接口)
     */
    public AlipayTradeRefundResponse refundTrade(String tradeNo, String refundAmount, String outRequestNo) throws Exception {
        return Factory.Payment.Common()
                // 退款请求号。标识一次退款请求，需要保证在交易号下唯一，如需部分退款，则此参数必传。
                .optional("out_request_no", outRequestNo)
                .refund(tradeNo, refundAmount);
    }

    /**
     * alipay.trade.fastpay.refund.query(统一收单交易退款查询)
     */
    public AlipayTradeFastpayRefundQueryResponse refundQuery(String tradeNo, String outRequestNo) throws Exception {
        return Factory.Payment.Common().queryRefund(tradeNo, outRequestNo);
    }
}
