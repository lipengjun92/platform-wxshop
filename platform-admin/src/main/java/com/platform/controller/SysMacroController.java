package com.platform.controller;

import com.platform.entity.SysMacroEntity;
import com.platform.service.SysMacroService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通用字典表Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-22 11:48:16
 */
@RestController
@RequestMapping("sys/macro")
public class SysMacroController {
    @Autowired
    private SysMacroService sysMacroService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:macro:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<SysMacroEntity> sysMacroList = sysMacroService.queryList(query);
        int total = sysMacroService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysMacroList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{macroId}")
    @RequiresPermissions("sys:macro:info")
    public R info(@PathVariable("macroId") Long macroId) {
        SysMacroEntity sysMacro = sysMacroService.queryObject(macroId);

        return R.ok().put("macro", sysMacro);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:macro:save")
    public R save(@RequestBody SysMacroEntity sysMacro) {
        sysMacroService.save(sysMacro);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:macro:update")
    public R update(@RequestBody SysMacroEntity sysMacro) {
        sysMacroService.update(sysMacro);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:macro:delete")
    public R delete(@RequestBody Long[] macroIds) {
        sysMacroService.deleteBatch(macroIds);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<SysMacroEntity> list = sysMacroService.queryList(params);

        return R.ok().put("list", list);
    }

    /**
     * 查询数据字典
     *
     * @param value
     * @return
     */
    @RequestMapping("/queryMacrosByValue")
    public R queryMacrosByValue(@RequestParam String value) {

        List<SysMacroEntity> list = sysMacroService.queryMacrosByValue(value);

        return R.ok().put("list", list);
    }
}
