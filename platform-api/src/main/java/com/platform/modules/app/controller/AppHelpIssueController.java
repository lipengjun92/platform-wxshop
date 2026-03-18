package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallHelpIssueEntity;
import com.platform.modules.mall.entity.MallHelpTypeEntity;
import com.platform.modules.mall.service.MallHelpIssueService;
import com.platform.modules.mall.service.MallHelpTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-11-07 11:04:20
 */
@Tag(name = "常见问题|AppHelpIssueController")
@RestController
@RequestMapping("/app/helpissue")
public class AppHelpIssueController extends AppBaseController {
    @Autowired
    private MallHelpIssueService helpIssueService;
    @Autowired
    private MallHelpTypeService helpTypeService;

    /**
     * 查看帮助类型列表
     */
    @Operation(summary = "查看帮助类型列表")
    @PostMapping("/typeList")
    @IgnoreAuth
    public RestResponse<Object> typeList() {

        List<MallHelpTypeEntity> list = helpTypeService.queryList(new HashMap<>());

        return RestResponse.ok(list);
    }

    /**
     * 查看问题列表
     */
    @Operation(summary = "查看问题列表")
    @PostMapping("/issueList")
    @IgnoreAuth
    public RestResponse<Object> issueList(Long typeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("typeId", typeId);
        List<MallHelpIssueEntity> helpIssueList = helpIssueService.queryList(params);

        return RestResponse.ok(helpIssueList);
    }
}
