package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallTopicEntity;
import com.platform.modules.mall.service.MallTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * <p>
 * 描述: AppController <br>
 */
@Tag(name = "专题管理|AppTopicController")
@RestController
@RequestMapping("/app/topic")
public class AppTopicController extends AppBaseController {
    @Autowired
    private MallTopicService topicService;

    /**
     *
     */
    @Operation(summary = "专题列表")
    @IgnoreAuth
    @PostMapping("list")
    public RestResponse<Object> list(@RequestParam(value = "page") Integer page,
                                     @RequestParam(value = "size") Integer size) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", page);
        param.put("limit", size);
        param.put("sidx", "id");
        param.put("order", "desc");
        param.put("fields", "id, title, price_info, scene_pic_url,subtitle");
        Query<MallTopicEntity> query = new Query<>(param);
        //查询列表数据
        Page<MallTopicEntity> result = topicService.queryPage(query);

        return RestResponse.ok(result);
    }

    /**
     *
     */
    @Operation(summary = "专题详情")
    @IgnoreAuth
    @PostMapping("detail")
    public RestResponse<Object> detail(Integer id) {
        MallTopicEntity topicEntity = topicService.getById(id);
        return RestResponse.ok(topicEntity);
    }

    /**
     *
     */
    @Operation(summary = "关联专题")
    @IgnoreAuth
    @PostMapping("related")
    public RestResponse<Object> related() {
        Map<String, Object> param = new HashMap<>();
        param.put("limit", 4);
        List<MallTopicEntity> topicEntities = topicService.queryList(param);
        return RestResponse.ok(topicEntities);
    }
}
