package com.platform.controller;

import com.platform.entity.HelpIssueEntity;
import com.platform.service.HelpIssueService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-11-07 10:09:54
 */
@Controller
@RequestMapping("helpissue")
public class HelpIssueController {
    @Autowired
    private HelpIssueService helpIssueService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("helpissue:list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<HelpIssueEntity> helpIssueList = helpIssueService.queryList(query);
        int total = helpIssueService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(helpIssueList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("helpissue:info")
    @ResponseBody
    public R info(@PathVariable("id") Integer id) {
        HelpIssueEntity helpIssue = helpIssueService.queryObject(id);

        return R.ok().put("helpIssue", helpIssue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("helpissue:save")
    @ResponseBody
    public R save(@RequestBody HelpIssueEntity helpIssue) {
        helpIssueService.save(helpIssue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("helpissue:update")
    @ResponseBody
    public R update(@RequestBody HelpIssueEntity helpIssue) {
        helpIssueService.update(helpIssue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("helpissue:delete")
    @ResponseBody
    public R delete(@RequestBody Integer[] ids) {
        helpIssueService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<HelpIssueEntity> list = helpIssueService.queryList(params);

        return R.ok().put("list", list);
    }
}
