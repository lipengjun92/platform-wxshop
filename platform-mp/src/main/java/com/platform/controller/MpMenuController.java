package com.platform.controller;

import com.platform.service.MpMenuService;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 公众号菜单Controller
 *
 * @author liepngjun
 * @email 939961241@qq.com
 * @date 2018-10-30 13:01:47
 */
@Controller
@RequestMapping("/mp/menu")
public class MpMenuController {
    @Autowired
    private MpMenuService mpMenuService;

    @GetMapping("/create")
    public String menuCreate(@RequestBody WxMenu wxMenu) throws WxErrorException {
        return mpMenuService.menuCreate(wxMenu);
    }

    @GetMapping("/menuCreateSample")
    @ResponseBody
    public String menuCreateSample() throws WxErrorException {
        WxMenuButton mainBtn1 = new WxMenuButton();
        mainBtn1.setName("官网");
        mainBtn1.setType("view");
        mainBtn1.setUrl("http://fly2you.cn");

        WxMenuButton btn21 = new WxMenuButton();
        btn21.setName("便利主义超市");
        btn21.setType("miniprogram");
        btn21.setUrl("http://mp.weixin.qq.com");
        btn21.setAppId("wx2a601dffd92609df");
        btn21.setPagePath("/pages/index/index");

        WxMenuButton btn22 = new WxMenuButton();
        btn22.setName("海数据在线");
        btn22.setType("miniprogram");
        btn22.setUrl("http://mp.weixin.qq.com");
        btn22.setAppId("wx69de7e7034266a70");
        btn22.setPagePath("/pages/index/index");

        WxMenuButton mainBtn2 = new WxMenuButton();
        mainBtn2.setName("客户案例");
        mainBtn2.getSubButtons().add(btn21);
        mainBtn2.getSubButtons().add(btn22);

        WxMenuButton btn31 = new WxMenuButton();
        btn31.setName("历史消息");
        btn31.setType("view");
        btn31.setUrl("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIxMTc5MzUyMQ==#wechat_redirect");

        WxMenuButton btn32 = new WxMenuButton();
        btn32.setName("商务合作");
        btn32.setType("view");
        btn32.setUrl("https://mp.weixin.qq.com/s/srJyUVzwK6wGwGFCQFch1A");

        WxMenuButton btn33 = new WxMenuButton();
        btn33.setName("更多资讯");
        btn33.setType("view");
        btn33.setUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MzIxMTc5MzUyMQ==&hid=1&sn=f8765358ef19c3423224069d333be5bf&scene=18#wechat_redirect");

        WxMenuButton btn34 = new WxMenuButton();
        btn34.setName("曾获荣誉");
        btn34.setType("view");
        btn34.setUrl("https://mp.weixin.qq.com/s/5mbU7Kp2TavasrZhfOUc7w");

        WxMenuButton mainBtn3 = new WxMenuButton();
        mainBtn3.setName("更多");
        mainBtn3.getSubButtons().add(btn31);
        mainBtn3.getSubButtons().add(btn32);
        mainBtn3.getSubButtons().add(btn33);
        mainBtn3.getSubButtons().add(btn34);

        WxMenu menu = new WxMenu();
        menu.getButtons().add(mainBtn1);
        menu.getButtons().add(mainBtn2);
        menu.getButtons().add(mainBtn3);

        return mpMenuService.menuCreate(menu);
    }

    @GetMapping("/create/{json}")
    @ResponseBody
    public String menuCreate(@PathVariable String json) throws WxErrorException {
        return mpMenuService.menuCreate(json);
    }

    @GetMapping("/delete")
    public void menuDelete() throws WxErrorException {
        mpMenuService.menuDelete();
    }

    @GetMapping("/delete/{menuId}")
    public void menuDelete(@PathVariable String menuId) throws WxErrorException {
        mpMenuService.menuDelete(menuId);
    }

    @GetMapping("/get")
    @ResponseBody
    public WxMpMenu menuGet() throws WxErrorException {
        return mpMenuService.menuGet();
    }

    @GetMapping("/menuTryMatch/{userid}")
    @ResponseBody
    public WxMenu menuTryMatch(@PathVariable String userid) throws WxErrorException {
        return mpMenuService.menuTryMatch(userid);
    }

    @GetMapping("/getSelfMenuInfo")
    @ResponseBody
    public WxMpGetSelfMenuInfoResult getSelfMenuInfo() throws WxErrorException {
        return mpMenuService.getSelfMenuInfo();
    }
}
