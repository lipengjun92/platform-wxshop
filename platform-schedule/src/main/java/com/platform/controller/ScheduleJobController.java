package com.platform.controller;

import com.platform.annotation.SysLog;
import com.platform.entity.ScheduleJobEntity;
import com.platform.service.ScheduleJobService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.R;
import com.platform.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 分页查询定时任务
     *
     * @param params 查询参数
     * @return R
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtilsPlus pageUtil = scheduleJobService.queryPage(params);
        return R.ok().put("page", pageUtil);
    }

    /**
     * 根据主键查询详情
     *
     * @param jobId 主键
     * @return R
     */
    @GetMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.getById(jobId);

        return R.ok().put("schedule", schedule);
    }

    /**
     * 新增定时任务
     *
     * @param scheduleJob scheduleJob
     * @return R
     */
    @SysLog("新增定时任务")
    @PostMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.add(scheduleJob);

        return R.ok();
    }

    /**
     * 修改定时任务
     *
     * @param scheduleJob scheduleJob
     * @return R
     */
    @SysLog("修改定时任务")
    @PostMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return R.ok();
    }

    /**
     * 删除定时任务
     *
     * @param jobIds jobIds
     * @return R
     */
    @SysLog("删除定时任务")
    @PostMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public R delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return R.ok();
    }

    /**
     * 立即执行任务
     *
     * @param jobIds jobIds
     * @return R
     */
    @SysLog("立即执行任务")
    @PostMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public R run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return R.ok();
    }

    /**
     * 暂停定时任务
     *
     * @param jobIds jobIds
     * @return R
     */
    @SysLog("暂停定时任务")
    @PostMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public R pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return R.ok();
    }

    /**
     * 恢复定时任务
     *
     * @param jobIds jobIds
     * @return R
     */
    @SysLog("恢复定时任务")
    @PostMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public R resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return R.ok();
    }

}
