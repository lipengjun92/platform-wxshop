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
package com.platform.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wx.dao.MsgReplyRuleDao;
import com.platform.modules.wx.entity.MsgReplyRuleEntity;
import com.platform.modules.wx.service.MsgReplyRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpCurrentAutoReplyInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
@Slf4j
@RequiredArgsConstructor
@Service("msgReplyRuleService")
public class MsgReplyRuleServiceImpl extends ServiceImpl<MsgReplyRuleDao, MsgReplyRuleEntity> implements MsgReplyRuleService {

    private final WxMpService wxMpService;

    @Override
    public Page<MsgReplyRuleEntity> queryPage(Map<String, Object> params) {
        String matchValue = (String) params.get("matchValue");

        Page<MsgReplyRuleEntity> page = new Query<MsgReplyRuleEntity>(params).getPage();
        return baseMapper.selectPage(page,
                new QueryWrapper<MsgReplyRuleEntity>()
                        .like(StringUtils.hasText(matchValue), "MATCH_VALUE", matchValue)
                        .orderByDesc("UPDATE_TIME")
        );
    }

    /**
     * 保存自动回复规则
     *
     * @param msgReplyRule
     */

    @Override
    public boolean save(MsgReplyRuleEntity msgReplyRule) {
        if (StringUtils.hasText(msgReplyRule.getRuleId())) {
            baseMapper.updateById(msgReplyRule);
        } else {
            baseMapper.insert(msgReplyRule);
        }
        return false;
    }

    /**
     * 获取所有的回复规则
     *
     * @return
     */
    @Override
    public List<MsgReplyRuleEntity> getRules() {
        return baseMapper.selectList(new QueryWrapper<MsgReplyRuleEntity>().orderByDesc("RULE_ID"));
    }

    /**
     * 获取当前时段内所有有效的回复规则
     *
     * @return
     */
    @Override
    public List<MsgReplyRuleEntity> getValidRules() {
        return baseMapper.selectList(
                new QueryWrapper<MsgReplyRuleEntity>()
                        .eq("STATUS", 1)
                        .isNotNull("MATCH_VALUE")
                        .ne("MATCH_VALUE", "")
                        .orderByDesc("PRIORITY"));
    }

    /**
     * 筛选符合条件的回复规则
     *
     * @param exactMatch 是否精确匹配
     * @param keywords   关键词
     * @return 规则列表
     */
    @Override
    public List<MsgReplyRuleEntity> getMatchedRules(boolean exactMatch, String keywords) {
        LocalTime now = LocalTime.now();
        return this.getValidRules().stream()
                // 检测是否在有效时段，effectTimeStart为null则一直有效
                .filter(rule -> null == rule.getEffectTimeStart() || rule.getEffectTimeStart().toLocalTime().isBefore(now))
                // 检测是否在有效时段，effectTimeEnd为null则一直有效
                .filter(rule -> null == rule.getEffectTimeEnd() || rule.getEffectTimeEnd().toLocalTime().isAfter(now))
                //检测是否符合匹配规则
                .filter(rule -> isMatch(exactMatch || rule.isExactMatch(), rule.getMatchValue().split(","), keywords))
                .collect(Collectors.toList());
    }

    /**
     * 同步公众号回复规则
     * <p>
     * 注意
     * 1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自动回复配置。
     * 2、本接口仅能获取公众号在公众平台官网的自动回复功能中设置的自动回复规则，若公众号自行开发实现自动回复，或通过第三方平台开发者来实现，则无法获取。
     * 3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
     * 4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
     * 5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpCurrentAutoReplyInfo syncReplyRyle() {
        WxMpCurrentAutoReplyInfo currentAutoReplyInfo = null;
        try {
            currentAutoReplyInfo = wxMpService.getCurrentAutoReplyInfo();
            // 关注后自动回复的信息
            WxMpCurrentAutoReplyInfo.AutoReplyInfo addFriendAutoReplyInfo = currentAutoReplyInfo.getAddFriendAutoReplyInfo();
            // 收到消息自动回复的信息
            WxMpCurrentAutoReplyInfo.AutoReplyInfo messageDefaultAutoReplyInfo = currentAutoReplyInfo.getMessageDefaultAutoReplyInfo();
            // 关键词自动回复的信息
            WxMpCurrentAutoReplyInfo.KeywordAutoReplyInfo keywordAutoReplyInfo = currentAutoReplyInfo.getKeywordAutoReplyInfo();
            // todo 和开启服务器配置冲突，这里就不入库了（查询的是微信公众平台的配置，而在本系统内添加的规则无法同步到微信公众平台）
            log.info(currentAutoReplyInfo.toString());
        } catch (WxErrorException e) {
            log.error("同步公众号回复规则出错:", e);
        }
        log.info("同步公众号回复规则：完成");
        return currentAutoReplyInfo;
    }

    /**
     * 检测文字是否匹配规则
     * 精确匹配时，需关键词与规则词语一致
     * 非精确匹配时，检测文字需包含任意一个规则词语
     *
     * @param exactMatch 是否精确匹配
     * @param ruleWords  规则列表
     * @param checkWords 需检测的文字
     * @return
     */
    public static boolean isMatch(boolean exactMatch, String[] ruleWords, String checkWords) {
        if (!StringUtils.hasText(checkWords)) {
            return false;
        }
        for (String words : ruleWords) {
            if (exactMatch && words.equals(checkWords)) {
                //精确匹配，需关键词与规则词语一致
                return true;
            }
            if (!exactMatch && checkWords.contains(words)) {
                //模糊匹配
                return true;
            }
        }
        return false;
    }
}
