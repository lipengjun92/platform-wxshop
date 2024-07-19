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
package com.platform.modules.sys.controller;

import com.aizuda.monitor.DiskInfo;
import com.aizuda.monitor.OshiMonitor;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.platform.common.utils.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器监控
 *
 * @author 李鹏军
 */
@Tag(name = "SysMonitorController|服务器监控")
@RestController
@RequestMapping("/sys/monitor")
public class SysMonitorController {
    /**
     * 注入监控模块 Oshi 调用类
     */
    @Resource
    private OshiMonitor oshiMonitor;

    @Operation(summary = "服务器监控", description = "服务器监控")
    @PostMapping("/server")
    @RequiresPermissions("sys:monitor:server")
    public RestResponse<Map<String, Object>> monitor() {
        Map<String, Object> server = new HashMap<>(5);
        // 系统信息
        server.put("sysInfo", oshiMonitor.getSysInfo());
        // CPU 信息
        server.put("cupInfo", oshiMonitor.getCpuInfo());
        // 内存信息
        server.put("memoryInfo", oshiMonitor.getMemoryInfo());
        // Jvm 虚拟机信息
        server.put("jvmInfo", oshiMonitor.getJvmInfo());
        // 磁盘信息
        List<DiskInfo> diskInfos = oshiMonitor.getDiskInfos();
        server.put("diskInfos", diskInfos);
        // 中央处理器
        server.put("centralProcessor", oshiMonitor.getCentralProcessor().getProcessorIdentifier());
        if (CollectionUtils.isNotEmpty(diskInfos)) {
            long usableSpace = 0;
            long totalSpace = 0;
            for (DiskInfo diskInfo : diskInfos) {
                usableSpace += diskInfo.getUsableSpace();
                totalSpace += diskInfo.getTotalSpace();
            }
            double usedSize = (totalSpace - usableSpace);
            // 统计所有磁盘的使用率
            server.put("diskUsePercent", oshiMonitor.formatDouble(usedSize / totalSpace * 100));
        }

        // 系统前 10 个进程
        List<OSProcess> processList = oshiMonitor.getOperatingSystem().getProcesses(null,
                OperatingSystem.ProcessSorting.CPU_DESC, 10);
        List<Map<String, Object>> processMapList = new ArrayList<>();
        for (OSProcess process : processList) {
            Map<String, Object> processMap = new HashMap<>(5);
            processMap.put("pid", process.getProcessID());
            processMap.put("cpu", oshiMonitor.formatDouble(process.getProcessCpuLoadCumulative()));
            processMap.put("name", process.getName());
            processMap.put("state", process.getState().name());
            processMap.put("threadCount", process.getThreadCount());
            processMap.put("virtualSize", process.getVirtualSize());
            processMap.put("residentSetSize", process.getResidentSetSize());
            processMap.put("kernelTime", process.getKernelTime());
            processMap.put("userTime", process.getUserTime());
            processMap.put("startTime", process.getStartTime());
            processMap.put("upTime", process.getUpTime());
            processMap.put("bytesRead", process.getBytesRead());
            processMap.put("bytesWritten", process.getBytesWritten());
            processMap.put("openFiles", process.getOpenFiles());
            processMapList.add(processMap);
        }
        server.put("processList", processMapList);
        return RestResponse.ok(server);
    }
}
