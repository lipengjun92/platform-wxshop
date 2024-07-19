/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务
 * testTask为spring bean的名称
 *
 * @author 李鹏军
 */
@Slf4j
@Component("testTask")
public class TestTask {

    /**
     * 过期自动取消订单
     */
    @SuppressWarnings(value = "unused")
    @Transactional(rollbackFor = Exception.class)
    public void test() {
        log.info("--------------------------开始定时任务--------------------------");

        log.info("--------------------------结束定时任务--------------------------");
    }

}
