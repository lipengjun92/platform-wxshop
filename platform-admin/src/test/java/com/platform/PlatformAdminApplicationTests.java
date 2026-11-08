package com.platform;

import com.platform.modules.wx.service.WxUserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requires configured MySQL and WeChat credentials; triggers an external user synchronization")
public class PlatformAdminApplicationTests {

    private final WxUserService userService;

    @Autowired
    public PlatformAdminApplicationTests(WxUserService userService) {
        this.userService = userService;
    }

    @Test
    public void syncWxUsers() {
        userService.syncWxUsers();
        System.out.println("任务已建立");
    }
}
