package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.entity.TopicVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiTopicService;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiPageUtils;
import com.platform.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * 描述: ApiIndexController <br>
 */
@Api(tags = "专题管理|ApiTopicController")
@RestController
@RequestMapping("/api/topic")
public class ApiTopicController extends ApiBaseAction {
    @Autowired
    private ApiTopicService topicService;

    /**
     */
    @ApiOperation(value = "专题列表")
    @IgnoreAuth
    @PostMapping("list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", page);
        param.put("limit", size);
        param.put("sidx", "id");
        param.put("order", "desc");
        param.put("fields", "id, title, price_info, scene_pic_url,subtitle");
        //查询列表数据
        Query query = new Query(param);
        List<TopicVo> topicEntities = topicService.queryList(query);
        int total = topicService.queryTotal(query);
        ApiPageUtils pageUtil = new ApiPageUtils(topicEntities, total, query.getLimit(), query.getPage());
        return toResponseSuccess(pageUtil);
    }

    /**
     */
    @ApiOperation(value = "专题详情")
    @IgnoreAuth
    @PostMapping("detail")
    public Object detail(@LoginUser UserVo loginUser, Integer id) {
        TopicVo topicEntity = topicService.queryObject(id);
        return toResponseSuccess(topicEntity);
    }

    /**
     */
    @ApiOperation(value = "关联专题")
    @IgnoreAuth
    @PostMapping("related")
    public Object related(@LoginUser UserVo loginUser, Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("limit", 4);
        List<TopicVo> topicEntities = topicService.queryList(param);
        return toResponseSuccess(topicEntities);
    }
}
