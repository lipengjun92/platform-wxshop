package com.platform.modules.app.controller;

import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.modules.app.request.ApiCollectToggleRequest;
import com.platform.modules.mall.entity.MallCollectEntity;
import com.platform.modules.mall.service.MallCollectService;
import com.platform.modules.wx.entity.WxUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Tag(name = "会员收藏-AppCollectController")
@RestController
@RequestMapping("/app/collect")
public class AppCollectController extends AppBaseController {
    @Autowired
    private MallCollectService collectService;

    /**
     * 获取用户收藏
     */
    @Operation(summary = "获取用户收藏")
    @PostMapping("list")
    public RestResponse<List<MallCollectEntity>> list(@LoginUser WxUserEntity loginUser, Integer typeId) {

        Map<String, Object> param = new HashMap<>();
        param.put("userId", loginUser.getId());
        param.put("typeId", typeId);
        List<MallCollectEntity> collectEntities = collectService.queryList(param);

        return RestResponse.ok(collectEntities);
    }

    /**
     * 获取用户收藏
     */
    @Operation(summary = "添加取消收藏")
    @PostMapping("addordelete")
    public RestResponse<Map<String, Object>> addordelete(@LoginUser WxUserEntity loginUser, @RequestBody ApiCollectToggleRequest request) {
        Integer typeId = request.getTypeId();
        Integer valueId = request.getValueId();

        Map<String, Object> param = new HashMap<>();
        param.put("userId", loginUser.getId());
        param.put("typeId", typeId);
        param.put("valueId", valueId);
        List<MallCollectEntity> collectEntities = collectService.queryList(param);
        //
        boolean collectRes = false;
        String handleType = "add";
        if (null == collectEntities || collectEntities.isEmpty()) {
            MallCollectEntity collectEntity = new MallCollectEntity();
            collectEntity.setAddTime(System.currentTimeMillis() / 1000);
            collectEntity.setTypeId(typeId);
            collectEntity.setValueId(valueId);
            collectEntity.setIsAttention(0);
            collectEntity.setUserId(loginUser.getId());
            //添加收藏
            collectRes = collectService.save(collectEntity);
        } else {
            //取消收藏
            collectRes = collectService.delete(collectEntities.get(0).getId());
            handleType = "delete";
        }

        if (collectRes) {
            Map<String, Object> data = new HashMap<>();
            data.put("type", handleType);
            return RestResponse.ok(data);
        }
        return RestResponse.fail("操作失败");
    }
}
