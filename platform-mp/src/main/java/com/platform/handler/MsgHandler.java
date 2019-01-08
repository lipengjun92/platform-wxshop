package com.platform.handler;

import com.platform.builder.TextBuilder;
import com.platform.service.WeixinService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MsgHandler extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgHandler.class);

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

        WeixinService weixinService = (WeixinService) wxMpService;

        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.hasKefuOnline()) {
            return WxMpXmlOutMessage
                    .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
        }

        switch (wxMessage.getContent()) {
            case "签到表单":
                return sendInSignMessage(wxMessage, context, wxMpService, sessionManager);
            case "发送弹幕":
                return handleDanmu(wxMessage, context, wxMpService, sessionManager);
            case "年会节目单":
                return handleContent(wxMessage, context, wxMpService, sessionManager);
            case "投票":
                return handleVote(wxMessage, context, wxMpService, sessionManager);
            default:
                String content = "回复信息内容";
                return new TextBuilder().build(content, wxMessage, weixinService);
        }
    }

    private WxMpXmlOutNewsMessage sendInSignMessage(WxMpXmlMessage wxMessage,
                                                    Map<String, Object> context, WxMpService wxMpService,
                                                    WxSessionManager sessionManager) {

        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription("签到表单");
        item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz_jpg/rFTQWsGze4G89XqNehSdSBGt1ic6ricfgBvaxnXGhsicia3xhIaKJB0hWMIqDXqLXC3OmBKnwfMiaXaBbrECialjOJiaQ/0?wx_fmt=jpeg");
        item.setTitle("签到");
        item.setUrl("https://fly2you.cn");

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .addArticle(item)
                .build();
        return m;
    }

    private WxMpXmlOutMessage handleDanmu(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                          WxMpService wxMpService, WxSessionManager sessionManager) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription("发送弹幕");
        item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz_jpg/rFTQWsGze4G89XqNehSdSBGt1ic6ricfgBfr8ThJnpIIibwpPhGjGrKpraiaNULFLfv238cC3sIxgCYZza6TYLKicBg/0?wx_fmt=jpeg");
        item.setTitle("评论上墙");
        item.setUrl("https://fly2you.cn");

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .addArticle(item)
                .build();
        return m;
    }

    private WxMpXmlOutMessage handleContent(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                            WxMpService wxMpService, WxSessionManager sessionManager) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription("年会节目单");
        item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/bVoOkrvEGHqgetjIc7VcFoCWgLCNaTOnZaXvR9J04EgxMfbm3WM9OreMfTcMcKN8UFkWtDwUbiatU7Qtxsutglg/0?wx_fmt=png");
        item.setTitle("节目单");
        item.setUrl("https://fly2you.cn");

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .addArticle(item)
                .build();
        return m;
    }

    private WxMpXmlOutMessage handleVote(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                         WxMpService wxMpService, WxSessionManager sessionManager) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription("来投票吧");
        item.setPicUrl("https://mmbiz.qlogo.cn/mmbiz_jpg/rFTQWsGze4EdewBW92AAD6Ap8ydAQrgBnndVMdAIXB4CmGiaGiassibiaKhWID6icmdMg3kvWSejFd5omyUdjcvb0GA/0?wx_fmt=jpeg");
        item.setTitle("投票");
        item.setUrl("https://fly2you.cn");

        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .addArticle(item)
                .build();
        return m;
    }
}