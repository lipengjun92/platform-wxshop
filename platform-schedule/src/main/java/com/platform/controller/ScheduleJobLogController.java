package com.platform.controller;

import com.platform.entity.ScheduleJobLogEntity;
import com.platform.service.ScheduleJobLogService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 分页查询定时任务日志
     *
     * @param params 查询参数
     * @return R
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtilsPlus page = scheduleJobLogService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param logId logId
     * @return R
     */
    @GetMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);

        return R.ok().put("log", log);
    }
}
