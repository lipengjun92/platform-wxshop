package com.platform.controller;

import com.platform.entity.Button;
import com.platform.entity.ComplexButton;
import com.platform.entity.Menu;
import com.platform.entity.ViewButton;
import com.platform.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公众号菜单Controller
 *
 * @author liepngjun
 * @email 939961241@qq.com
 * @date 2018-10-30 13:01:47
 */
@RestController
@RequestMapping("/mp/menu")
public class MpMenuController {

    // 菜单创建（POST）
    public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 菜单查询（GET）
    public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    // 菜单删除（GET）
    public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    @RequestMapping("/getMenu")
    public R getMenu() {

        ViewButton mainBtn1 = new ViewButton();
        mainBtn1.setName("官网");
        mainBtn1.setType("view");
        mainBtn1.setUrl("http://fly2you.cn");

        ViewButton viewButton1 = new ViewButton();
        viewButton1.setName("便利主义超市");
        viewButton1.setType("miniprogram");
        viewButton1.setUrl("http://mp.weixin.qq.com");
        viewButton1.setAppId("");
        viewButton1.setPagePath("/pages/index/index");

        ViewButton viewButton2 = new ViewButton();
        viewButton2.setName("海数据在线");
        viewButton2.setType("miniprogram");
        viewButton2.setUrl("http://mp.weixin.qq.com");
        viewButton2.setAppId("");
        viewButton2.setPagePath("/pages/index/index");

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("客户案例");
        mainBtn2.setSub_button(new Button[]{viewButton1, viewButton2});

        ViewButton btn30 = new ViewButton();
        btn30.setName("历史消息");
        btn30.setType("view");
        btn30.setUrl("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIxMTc5MzUyMQ==#wechat_redirect");

        ViewButton btn31 = new ViewButton();
        btn31.setName("商务合作");
        btn31.setType("view");
        btn31.setUrl("https://mp.weixin.qq.com/s/srJyUVzwK6wGwGFCQFch1A");

        ViewButton btn32 = new ViewButton();
        btn32.setName("更多资讯");
        btn32.setType("view");
        btn32.setUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MzIxMTc5MzUyMQ==&hid=1&sn=f8765358ef19c3423224069d333be5bf&scene=18#wechat_redirect");

        ViewButton btn33 = new ViewButton();
        btn33.setName("曾获荣誉");
        btn33.setType("view");
        btn33.setUrl("https://mp.weixin.qq.com/s/5mbU7Kp2TavasrZhfOUc7w");

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多");
        mainBtn3.setSub_button(new Button[]{btn30, btn31, btn32, btn33});

        Menu menu = new Menu();
        menu.setButton(new Button[]{mainBtn1, mainBtn2, mainBtn3});
        return R.ok().put("menu", menu);
    }
}
