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
import com.platform.common.utils.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishInfo;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * 微信发布能力接口Controller.
 *
 * @author 李鹏军
 * @since 2022-08-22 19:20:47
 */
@Tag(name = "WxMpFreePublishController|微信发布能力接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("manage/freepublish")
public class WxMpFreePublishController {
    private final WxMpService wxMpService;

    /**
     * 删除发布
     * 发布成功之后，随时可以通过该接口删除。此操作不可逆，请谨慎操作。
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/freepublish/delete?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Publish/Delete_posts.html
     * </pre>
     *
     * @param request articleId 成功发布时返回的 article_id
     *                index 要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "删除发布", description = "删除发布")
    @SysLog("删除发布")
    @PostMapping("/deletePush")
    @RequiresPermissions("wx:freepublish:deletePush")
    public RestResponse<String> delDraft(@RequestBody JSONObject request) throws WxErrorException {
        Integer index = request.getInteger("index");
        String articleId = request.getString("articleId");
        if (null == index) {
            wxMpService.getFreePublishService().deletePushAllArticle(articleId);
        } else {
            wxMpService.getFreePublishService().deletePush(articleId, index);
        }
        return RestResponse.ok();
    }

    /**
     * 获取成功发布列表
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Publish/Get_publication_records.html
     * </pre>
     *
     * @param page  page
     * @param limit limit
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "获取成功发布列表", description = "获取成功发布列表")
    @GetMapping("/getPublicationRecords")
    @RequiresPermissions("wx:freepublish:getPublicationRecords")
    public RestResponse<WxMpFreePublishList> listDraft(@RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer limit) throws WxErrorException {
        int offset = (page - 1) * limit;

        WxMpFreePublishList freePublishList = wxMpService.getFreePublishService().getPublicationRecords(offset, limit, 1);
        return RestResponse.ok(freePublishList);
    }

    /**
     * 通过 article_id 获取已发布文章
     * 开发者可以通过 article_id 获取已发布的图文信息。
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/freepublish/getarticle?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Publish/Get_article_from_id.html
     * </pre>
     *
     * @param articleId 要获取的草稿的article_id
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "获取已发布文章", description = "通过 article_id 获取已发布文章")
    @GetMapping("/getArticleFromId")
    @RequiresPermissions("wx:freepublish:getArticleFromId")
    public RestResponse<WxMpFreePublishInfo> getArticleFromId(@RequestParam String articleId) throws WxErrorException {
        WxMpFreePublishInfo freePublishInfo = wxMpService.getFreePublishService().getArticleFromId(articleId);
        return RestResponse.ok(freePublishInfo);
    }

    /**
     * 发布接口 - 只有默认必填参数
     * 开发者需要先将图文素材以草稿的形式保存（见“草稿箱/新建草稿”，如需从已保存的草稿中选择，见“草稿箱/获取草稿列表”），选择要发布的草稿 media_id 进行发布
     * <pre>
     * 请求地址：POST https://api.weixin.qq.com/cgi-bin/freepublish/submit?access_token=ACCESS_TOKEN
     * 文档地址：https://developers.weixin.qq.com/doc/offiaccount/Publish/Publish.html
     * </pre>
     *
     * @param mediaId 要发布的草稿的media_id
     * @return RestResponse
     * @throws WxErrorException .
     */
    @Operation(summary = "发布接口", description = "发布接口")
    @GetMapping("/submit/{mediaId}")
    @RequiresPermissions("wx:freepublish:submit")
    public RestResponse<String> submit(@PathVariable("mediaId") String mediaId) throws WxErrorException {
        String publishId = wxMpService.getFreePublishService().submit(mediaId);
        return RestResponse.ok("操作成功", publishId);
    }
}
