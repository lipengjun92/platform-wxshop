package com.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 名称：TestController <br>
 * 描述：测试<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("/toTest")
    public String tojsp() {
        return "test";
    }
}
