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
package com.platform.modules.wx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wx.entity.MsgReplyRuleEntity;
import me.chanjar.weixin.mp.bean.result.WxMpCurrentAutoReplyInfo;

import java.util.List;
import java.util.Map;

/**
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
public interface MsgReplyRuleService extends IService<MsgReplyRuleEntity> {
    /**
     * 分页查询用户数据
     *
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    Page<MsgReplyRuleEntity> queryPage(Map<String, Object> params);

    /**
     * 保存自动回复规则
     *
     * @param msgReplyRule msgReplyRule
     * @return boolean
     */
    @Override
    boolean save(MsgReplyRuleEntity msgReplyRule);

    /**
     * 获取所有的回复规则
     *
     * @return
     */
    List<MsgReplyRuleEntity> getRules();

    /**
     * 获取当前时段内所有有效的回复规则
     *
     * @return 有效的规则列表
     */
    List<MsgReplyRuleEntity> getValidRules();

    /**
     * 筛选符合条件的回复规则
     *
     * @param exactMatch 是否精确匹配
     * @param keywords   关键词
     * @return 规则列表
     */
    List<MsgReplyRuleEntity> getMatchedRules(boolean exactMatch, String keywords);

    /**
     * 同步公众号回复规则
     * <p>
     * 注意
     * 1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自动回复配置。
     * 2、本接口仅能获取公众号在公众平台官网的自动回复功能中设置的自动回复规则，若公众号自行开发实现自动回复，或通过第三方平台开发者来实现，则无法获取。
     * 3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
     * 4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
     * 5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
     *
     * @return WxMpCurrentAutoReplyInfo
     */
    WxMpCurrentAutoReplyInfo syncReplyRyle();
}
