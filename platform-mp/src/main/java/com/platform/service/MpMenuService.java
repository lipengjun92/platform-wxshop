package com.platform.service;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpMenuServiceImpl;
import org.springframework.stereotype.Service;

@Service("mpMenuService")
public class MpMenuService extends WxMpMenuServiceImpl {

    public MpMenuService(WxMpService wxMpService) {
        super(wxMpService);
    }
}
