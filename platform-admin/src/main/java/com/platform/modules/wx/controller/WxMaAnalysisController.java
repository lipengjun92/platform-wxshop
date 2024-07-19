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
package com.platform.modules.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaAnalysisService;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaSummaryTrend;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaVisitDistribution;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaVisitPage;
import cn.binarywang.wx.miniapp.bean.analysis.WxMaVisitTrend;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序数据分析相关接口Controller
 *
 * @author 李鹏军
 * @since 2020-04-21 14:41:47
 */
@Tag(name = "WxMaAnalysisController|小程序数据分析")
@RequiredArgsConstructor
@RestController
@RequestMapping("manage/maanalysis")
public class WxMaAnalysisController extends AbstractController {
    private final WxMaAnalysisService maAnalysisService;

    /**
     * 查询概况趋势
     * 温馨提示：小程序接口目前只能查询一天的数据，即 beginDate 和 endDate 一样
     *
     * @param params 开始日期,结束日期，限定查询1天数据，end_date允许设置的最大值为昨日
     * @return 概况趋势
     */
    @Operation(summary = "查询概况趋势", description = "查询概况趋势")
    @GetMapping("/getDailySummaryTrend")
    public RestResponse<List<WxMaSummaryTrend>> getDailySummaryTrend(@RequestParam Map<String, String> params) {
        String strDate = params.get("date");
        Date date = DateUtils.strToDate(strDate);
        if (null == date) {
            //参数为空默认查昨天
            date = DateUtils.addDateDays(new Date(), -1);
        }
        List<WxMaSummaryTrend> list;
        try {
            list = maAnalysisService.getDailySummaryTrend(date, date);
        } catch (WxErrorException e) {
            return RestResponse.fail("查询概况趋势失败，" + e.getMessage());
        }
        return RestResponse.ok(list);
    }

    /**
     * 获取日访问趋势
     * 温馨提示：小程序接口目前只能查询一天的数据，即 beginDate 和 endDate 一样
     *
     * @param params 开始日期,结束日期，限定查询1天数据，end_date允许设置的最大值为昨日
     * @return 日访问趋势
     */
    @Operation(summary = "获取日访问趋势", description = "获取日访问趋势")
    @GetMapping("/getDailyVisitTrend")
    public RestResponse<List<WxMaVisitTrend>> getDailyVisitTrend(@RequestParam Map<String, String> params) {
        String strDate = params.get("date");
        Date date = DateUtils.strToDate(strDate);
        if (null == date) {
            //参数为空默认查昨天
            date = DateUtils.addDateDays(new Date(), -1);
        }
        List<WxMaVisitTrend> list;
        try {
            list = maAnalysisService.getDailyVisitTrend(date, date);
        } catch (WxErrorException e) {
            return RestResponse.fail("查询概况趋势失败，" + e.getMessage());
        }
        return RestResponse.ok(list);
    }

    /**
     * 获取周访问趋势
     * 限定查询一个自然周的数据，时间必须按照自然周的方式输入： 如：20170306(周一), 20170312(周日)
     *
     * @param params 开始日期，为周一日期,结束日期，为周日日期，限定查询一周数据
     * @return 周访问趋势（每项数据都是一个自然周汇总）
     */
    @Operation(summary = "获取周访问趋势", description = "获取周访问趋势")
    @GetMapping("/getWeeklyVisitTrend")
    public RestResponse<List<WxMaVisitTrend>> getWeeklyVisitTrend(@RequestParam Map<String, String> params) {
        String strDate = params.get("date");
        Date date = DateUtils.strToDate(strDate);
        Date[] dates;
        if (null == date) {
            //参数为空默认查上周
            dates = DateUtils.getWeekStartAndEnd(-1);
        } else {
            dates = DateUtils.getWeekStartAndEndByDate(date);
        }
        Date date1 = dates[0];
        Date date2 = dates[1];
        List<WxMaVisitTrend> list;
        try {
            list = maAnalysisService.getWeeklyVisitTrend(date1, date2);
        } catch (WxErrorException e) {
            return RestResponse.fail("查询概况趋势失败，" + e.getMessage());
        }
        return RestResponse.ok(list);
    }

    /**
     * 获取月访问趋势
     * 限定查询一个自然月的数据，时间必须按照自然月的方式输入： 如：20170201(月初), 20170228(月末)
     *
     * @param params 开始日期，为自然月第一天，结束日期，为自然月最后一天，限定查询一个月数据
     * @return 月访问趋势（每项数据都是一个自然月汇总）
     */
    @Operation(summary = "获取月访问趋势", description = "获取月访问趋势")
    @GetMapping("/getMonthlyVisitTrend")
    public RestResponse<List<WxMaVisitTrend>> getMonthlyVisitTrend(@RequestParam Map<String, String> params) {
        String strDate = params.get("date");
        Date date = DateUtils.strToDate(strDate);
        Date[] dates;
        if (null == date) {
            //参数为空默认查上月
            dates = DateUtils.getMonthStartAndEnd(-1);
        } else {
            dates = DateUtils.getMonthStartAndEnd(date);
        }
        Date date1 = dates[0];
        Date date2 = dates[1];
        List<WxMaVisitTrend> list;
        try {
            list = maAnalysisService.getMonthlyVisitTrend(date1, date2);
        } catch (WxErrorException e) {
            return RestResponse.fail("查询概况趋势失败，" + e.getMessage());
        }
        return RestResponse.ok(list);
    }

    /**
     * 获取访问分布
     * （此接口目前只能查询一天的数据，即 beginDate 和 endDate 一样）
     *
     * @param params 开始日期，为周一日期，结束日期，限定查询1天数据，end_date允许设置的最大值为昨日
     * @return 访问分布
     */
    @Operation(summary = "获取访问分布", description = "获取访问分布")
    @GetMapping("/getVisitDistribution")
    public RestResponse<WxMaVisitDistribution> getVisitDistribution(@RequestParam Map<String, String> params) {
        String strDate = params.get("date");
        Date date = DateUtils.strToDate(strDate);
        if (null == date) {
            //参数为空默认查昨天
            date = DateUtils.addDateDays(new Date(), -1);
        }
        try {
            WxMaVisitDistribution data = maAnalysisService.getVisitDistribution(date, date);
            return RestResponse.ok(data);
        } catch (WxErrorException e) {
            return RestResponse.fail("查询概况趋势失败，" + e.getMessage());
        }
    }

    /**
     * 获取访问页面数据
     * 温馨提示：此接口目前只能查询一天的数据，即 beginDate 和 endDate 一样
     *
     * @param params 开始日期，为周一日期，结束日期，限定查询1天数据，end_date允许设置的最大值为昨日
     * @return 访问页面数据
     */
    @Operation(summary = "获取访问页面数据", description = "获取访问页面数据")
    @GetMapping("/getVisitPage")
    public RestResponse<Map<String, Object>> getVisitPage(@RequestParam Map<String, String> params) {
        String strDate = params.get("date");
        Date date = DateUtils.strToDate(strDate);
        if (null == date) {
            //参数为空默认查昨天
            date = DateUtils.addDateDays(new Date(), -1);
        }
        try {
            List<WxMaVisitPage> list = maAnalysisService.getVisitPage(date, date);
            Map<String, Object> map = new HashMap<>(4);
            map.put("list", list);
            map.put("date", DateUtils.format(date));
            return RestResponse.ok(map);
        } catch (WxErrorException e) {
            return RestResponse.fail("查询概况趋势失败，" + e.getMessage());
        }
    }
}
