package com.platform.controller;

import com.platform.entity.HelpTypeEntity;
import com.platform.service.HelpTypeService;
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
@RequestMapping("helptype")
public class HelpTypeController {
    @Autowired
    private HelpTypeService helpTypeService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("helptype:list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<HelpTypeEntity> helpTypeList = helpTypeService.queryList(query);
        int total = helpTypeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(helpTypeList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("helptype:info")
    @ResponseBody
    public R info(@PathVariable("id") Integer id) {
        HelpTypeEntity helpType = helpTypeService.queryObject(id);

        return R.ok().put("helpType", helpType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("helptype:save")
    @ResponseBody
    public R save(@RequestBody HelpTypeEntity helpType) {
        helpTypeService.save(helpType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("helptype:update")
    @ResponseBody
    public R update(@RequestBody HelpTypeEntity helpType) {
        helpTypeService.update(helpType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("helptype:delete")
    @ResponseBody
    public R delete(@RequestBody Integer[]ids) {
        helpTypeService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<HelpTypeEntity> list = helpTypeService.queryList(params);

        return R.ok().put("list", list);
    }
}
