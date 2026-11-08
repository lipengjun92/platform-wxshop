package com.platform;

import com.platform.modules.wx.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfSystemProperty(named = "runWxSyncIntegrationTest", matches = "true")
public class PlatformAdminApplicationTests {

    @Autowired
    private WxUserService userService;


    @Test
    public void syncWxUsers() {
        userService.syncWxUsers();
        System.out.println("任务已建立");
    }
}
