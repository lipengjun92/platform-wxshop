package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.entity.HelpIssueVo;
import com.platform.entity.HelpTypeVo;
import com.platform.service.ApiHelpIssueService;
import com.platform.service.ApiHelpTypeService;
import com.platform.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2018-11-07 11:04:20
 */
@Api(tags = "常见问题|ApiHelpIssueController")
@RestController
@RequestMapping("api/helpissue")
public class ApiHelpIssueController extends ApiBaseAction {
    @Autowired
    private ApiHelpIssueService helpIssueService;
    @Autowired
    private ApiHelpTypeService helpTypeService;

    /**
     * 查看帮助类型列表
     */
    @ApiOperation(value = "查看帮助类型列表")
    @PostMapping("/typeList")
    @IgnoreAuth
    public Object typeList() {

        List<HelpTypeVo> list = helpTypeService.queryList(new HashMap());

        return toResponseSuccess(list);
    }

    /**
     * 查看问题列表
     */
    @ApiOperation(value = "查看问题列表")
    @PostMapping("/issueList")
    @IgnoreAuth
    public Object issueList(Long typeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("typeId", typeId);
        List<HelpIssueVo> helpIssueList = helpIssueService.queryList(params);

        return toResponseSuccess(helpIssueList);
    }
}
