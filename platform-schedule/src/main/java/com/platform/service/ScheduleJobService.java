package com.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.ScheduleJobEntity;
import com.platform.utils.PageUtilsPlus;

import java.util.Map;

/**
 * 定时任务
 *
 * @author 李鹏军
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

    /**
     * 获取分页数据
     *
     * @param params 查询参数
     * @return Page
     */
    PageUtilsPlus queryPage(Map<String, Object> params);

    /**
     * 新增定时任务
     *
     * @param scheduleJob scheduleJob
     */
    void add(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     *
     * @param scheduleJob scheduleJob
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     *
     * @param jobIds jobIds
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     *
     * @param jobIds jobIds
     * @param status status
     */
    void updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     *
     * @param jobIds jobIds
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     *
     * @param jobIds jobIds
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     *
     * @param jobIds jobIds
     */
    void resume(Long[] jobIds);
}
