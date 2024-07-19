package com.platform;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.github.binarywang.wxpay.bean.entpay.EntPayBankRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.platform.common.utils.IpUtils;
import com.platform.modules.sys.service.MailService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * Unit test for simple App.
 */
@RequiredArgsConstructor
@SpringBootTest
public class PlatformApiApplicationTests {

    private final MailService mailService;
    private final WxPayService payService;
    private final WxMaService maService;

    @Test
    public void testHtmlMail() {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件1111111!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendMail("939961241@qq.com", "test html mail", content);
    }

    /**
     * 企业向个人付款
     *
     * @throws WxPayException
     */
    @Test
    public void testPay() throws WxPayException {
        EntPayRequest request = new EntPayRequest();
        request.setPartnerTradeNo("APP20200717171910474277266");
        request.setOpenid("ouby15OpFirvjeut_CjDhWYAcm0s");
        request.setCheckName("NO_CHECK");// 不校验真实姓名
        request.setReUserName("李鹏军");
        request.setDescription("佣金提现");
        request.setSpbillCreateIp(IpUtils.getLocalHostAdress());
        request.setAmount(30);// amount是以分为单位
        System.out.println(request);
        payService.getEntPayService().entPay(request);
    }

    /**
     * 企业向个人银行卡付款
     *
     * @throws WxPayException
     */
    @Test
    public void testPayBank() throws WxPayException {
        EntPayBankRequest request = new EntPayBankRequest();
        request.setPartnerTradeNo("APP20200717171910474277266");
        request.setEncBankNo("6214991630490547");
        request.setEncTrueName("李鹏军");
        //建设银行
        request.setBankCode("1003");
        request.setAmount(1);// amount是以分为单位
        request.setDescription("佣金提现");
        payService.getEntPayService().payBank(request);
    }

    /**
     * 申请退款
     *
     * @throws WxPayException
     */
    @Test
    public void testEntPay() throws WxPayException {
        WxPayRefundRequest request = new WxPayRefundRequest();
        request.setOutTradeNo("MA20200718115214780645086");
        request.setTotalFee(1);
        request.setRefundFee(1);
        request.setOutRefundNo("MA20200718115214780645086");
        request.setRefundDesc("测试退款");
        System.out.println(request);
        payService.refund(request);
    }

    /**
     * 生成小程序码
     * 注意事项：需要配置小程序
     *
     * @throws Exception
     */
    @Test
    public void testQrCode() throws Exception {
        byte[] bytes = maService.getQrcodeService().createWxaCodeBytes("/pages/shops/shops?shopsSn=80001", "release", 500, false, new WxMaCodeLineColor("0", "0", "0"), false);
        String file = "D:/maQrCode.jpg";
        FileUtils.writeByteArrayToFile(new File(file), bytes, false);
    }

    @Test
    public void checkMessage() throws WxErrorException {
        WxMaSecCheckService maSecCheckService = maService.getSecCheckService();
        System.out.println(maSecCheckService.checkMessage("这个没有违规"));
        //如果有违规信息直接抛出异常
        maSecCheckService.checkMessage("特3456书yuuo莞6543李zxcz蒜7782法fgnv级");
        System.out.println("程序运行不到这一步了！");
    }
}
