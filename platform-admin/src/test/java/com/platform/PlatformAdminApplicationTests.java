package com.platform;

import com.platform.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor
@SpringBootTest
public class PlatformAdminApplicationTests {

    private final WxUserService userService;


    @Test
    public void syncWxUsers() {
        userService.syncWxUsers();
        System.out.println("任务已建立");
    }
}
