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

import com.alibaba.fastjson.JSONObject;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.LambdaUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.wx.form.DraftArticles;
import com.platform.modules.wx.form.DraftForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.draft.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信草稿箱接口Controller
 *
 * @author 李鹏军
 * @since 2022-08-21 22:59:47
 */
@Tag(name = "WxMpDraftController|微信草稿箱接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("manage/draft")
public class WxMpDraftController {
    private final WxMpService wxMpService;

    /**
     * 新建草稿 - 完整参数
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/draft/add?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Add_draft.html
     * </pre>
     *
     * @param addDraft 新建草稿信息
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "新建草稿", description = "新建草稿")
    @PostMapping("/addDraft")
    @RequiresPermissions("wx:draft:addDraft")
    public RestResponse<String> addDraft(@RequestBody WxMpAddDraft addDraft) throws WxErrorException {
        addDraft.getArticles().forEach(LambdaUtils.consumerWithIndex((article, index) -> {
            String str = "第" + (index + 1) + "篇文章";
            AbstractAssert.isBlank(article.getTitle(), str + "标题不能为空！");
            AbstractAssert.isBlank(article.getContent(), str + "内容不能为空！");
            AbstractAssert.isBlank(article.getThumbMediaId(), str + "封面不能为空！");
        }));
        // 新增草稿箱
        wxMpService.getDraftService().addDraft(addDraft);
        return RestResponse.ok();
    }

    /**
     * 修改草稿 - 完整参数
     * 正常情况下调用成功时，errcode将为0。错误时微信会返回错误码等信息，请根据错误码查询错误信息
     * <pre>
     * 请求地址： POST https://api.weixin.qq.com/cgi-bin/draft/update?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Update_draft.html
     * </pre>
     *
     * @param draftForm 修改草稿信息
     * @return RestResponse
     */
    @Operation(summary = "修改草稿", description = "修改草稿")
    @PostMapping("/updateDraft")
    @RequiresPermissions("wx:draft:updateDraft")
    public RestResponse<String> updateDraft(@RequestBody DraftForm draftForm) {
        String mediaId = draftForm.getMediaId();
        AbstractAssert.isBlank(mediaId, "mediaId不能为空！");

        List<DraftArticles> articles = draftForm.getNewsItem();
        articles.forEach(LambdaUtils.consumerWithIndex((article, index) -> {
            String str = "第" + (index + 1) + "篇文章";
            AbstractAssert.isNull(article.getIndex(), str + "index不能为空！");
            AbstractAssert.isBlank(article.getTitle(), str + "标题不能为空！");
            AbstractAssert.isBlank(article.getContent(), str + "内容不能为空！");
            AbstractAssert.isBlank(article.getThumbMediaId(), str + "封面不能为空！");

            // 更新参数
            WxMpUpdateDraft updateDraftInfo = new WxMpUpdateDraft();

            updateDraftInfo.setIndex(article.getIndex());
            updateDraftInfo.setMediaId(mediaId);

            WxMpDraftArticles mpDraftArticles = new WxMpDraftArticles();
            mpDraftArticles.setTitle(article.getTitle())
                    .setAuthor(article.getAuthor())
                    .setDigest(article.getDigest())
                    .setContent(article.getContent())
                    .setContentSourceUrl(article.getContentSourceUrl())
                    .setThumbMediaId(article.getThumbMediaId())
                    .setNeedOpenComment(article.getNeedOpenComment())
                    .setOnlyFansCanComment(article.getOnlyFansCanComment());
            updateDraftInfo.setArticles(mpDraftArticles);

            // 更新草稿箱
            try {
                wxMpService.getDraftService().updateDraft(updateDraftInfo);
            } catch (WxErrorException e) {
                e.printStackTrace();
                throw new BusinessException(e.getMessage());
            }
        }));
        return RestResponse.ok();
    }

    /**
     * 获取草稿信息
     *
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/draft/get?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Get_draft.html
     * </pre>
     *
     * @param mediaId 要获取的草稿的media_id
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "获取草稿信息", description = "获取草稿信息")
    @GetMapping("/getDraft/{mediaId}")
    @RequiresPermissions("wx:draft:getDraft")
    public RestResponse<WxMpDraftInfo> getDraft(@PathVariable("mediaId") String mediaId) throws WxErrorException {
        WxMpDraftInfo draftInfo = wxMpService.getDraftService().getDraft(mediaId);
        return RestResponse.ok(draftInfo);
    }

    /**
     * 删除草稿
     * 正常情况下调用成功时，errcode将为0。错误时微信会返回错误码等信息，请根据错误码查询错误信息。
     * 多次删除同一篇草稿，也返回 0.
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/draft/delete?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Delete_draft.html
     * </pre>
     *
     * @param request 要删除的草稿的media_id
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "删除草稿", description = "删除草稿")
    @SysLog("删除草稿")
    @PostMapping("/delDraft")
    @RequiresPermissions("wx:draft:delDraft")
    public RestResponse<String> delDraft(@RequestBody JSONObject request) throws WxErrorException {
        wxMpService.getDraftService().delDraft(request.getString("mediaId"));
        return RestResponse.ok();
    }

    /**
     * 获取草稿列表
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Get_draft_list.html
     * </pre>
     *
     * @param page page
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "获取草稿列表", description = "获取草稿列表")
    @GetMapping("/listDraft")
    @RequiresPermissions("wx:draft:listDraft")
    public RestResponse<WxMpDraftList> listDraft(@RequestParam(defaultValue = "1") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer limit) throws WxErrorException {
        int offset = (page - 1) * limit;

        WxMpDraftList draftList = wxMpService.getDraftService().listDraft(offset, limit, 1);
        return RestResponse.ok(draftList);
    }
}
