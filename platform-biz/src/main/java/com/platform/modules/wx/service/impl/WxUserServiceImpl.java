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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wx.TaskExcutor;
import com.platform.modules.wx.dao.WxUserDao;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 李鹏军
 */
@Service
@RequiredArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<WxUserDao, WxUserEntity> implements WxUserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WxUserDao userMapper;
    private final WxMpService wxMpService;

    private volatile static boolean syncWxUserTaskRunning = false;

    @Override
    public Page<WxUserEntity> queryPage(Map<String, Object> params) {
        String openid = (String) params.get("openid");
        String nickname = (String) params.get("nickname");
        String city = (String) params.get("city");
        String qrSceneStr = (String) params.get("qrSceneStr");
        String subscribeScene = (String) params.get("subscribeScene");
        String tagid = (String) params.get("tagid");
        Page<WxUserEntity> page = new Query<WxUserEntity>(params).getPage();
        return baseMapper.selectPage(page,
                new QueryWrapper<WxUserEntity>()
                        .eq(StringUtils.hasText(openid), "OPENID", openid)
                        .like(StringUtils.hasText(nickname), "NICKNAME", nickname)
                        .like(StringUtils.hasText(tagid), "TAGID_LIST", tagid)
                        .eq(StringUtils.hasText(city), "CITY", city)
                        .eq(StringUtils.hasText(qrSceneStr), "QR_SCENE_STR", qrSceneStr)
                        .eq(StringUtils.hasText(subscribeScene), "SUBSCRIBE_SCENE", subscribeScene).orderByDesc("SUBSCRIBE_TIME")
        );
    }

    /**
     * 根据openid更新用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public WxUserEntity refreshUserInfo(String openid) {
        try {
            // 获取微信用户基本信息
            logger.info("更新用户信息，openid={}", openid);
            WxMpUser userWxInfo = wxMpService.getUserService().userInfo(openid, null);
            if (userWxInfo == null) {
                logger.error("获取不到用户信息，无法更新,openid:{}", openid);
                return null;
            }
            WxUserEntity user = new WxUserEntity(userWxInfo);
            user.setLastLoginTime(new Date());

            // 用户先使用小程序登录时，使用openId会查不到，导致同一用户产生两条相同uniodId的数据
            // 所以这里先根据unionId更新，更新返回false则新增
            boolean flag = this.update(user, new UpdateWrapper<WxUserEntity>().eq("UNION_ID", userWxInfo.getUnionId()));
            if (!flag) {
                user.setRegisterTime(new Date());
                this.saveOrUpdate(user);
            }
            return user;
        } catch (Exception e) {
            logger.error("更新用户信息失败,openid:{}", openid);
        }
        return null;
    }

    /**
     * 异步批量同步用户信息
     *
     * @param openidList
     */
    @Override
    @Async
    public void refreshUserInfoAsync(String[] openidList) {
        logger.info("批量更新用户信息：任务开始");
        for (String openid : openidList) {
            TaskExcutor.submit(() -> this.refreshUserInfo(openid));
        }
        logger.info("批量更新用户信息：任务全部添加到线程池");
    }

    /**
     * 数据存在时更新，否则新增
     *
     * @param user
     */
    @Override
    public void updateOrInsert(WxUserEntity user) {
        int updateCount = userMapper.updateById(user);
        if (updateCount < 1) {
            userMapper.insert(user);
        }
    }

    @Override
    public void unsubscribe(String openid) {
        userMapper.unsubscribe(openid);
    }

    /**
     * 同步用户列表,公众号一次拉取调用最多拉取10000个关注者的OpenID，可以通过传入nextOpenid参数多次拉取
     * 项目正式上线时初始化拉取公众号关注用户，运营之后禁止调用
     */
    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void syncWxUsers() {
        //同步较慢，防止个多线程重复执行同步任务
        Assert.isTrue(!syncWxUserTaskRunning, "后台有同步任务正在进行中，请稍后重试");
        syncWxUserTaskRunning = true;
        logger.info("同步公众号粉丝列表：任务开始");
        boolean hasMore = true;
        String nextOpenid = null;
        WxMpUserService wxMpUserService = wxMpService.getUserService();
        try {
            int page = 1;
            while (hasMore) {
                //拉取openid列表，每次最多1万个
                WxMpUserList wxMpUserList = wxMpUserService.userList(nextOpenid);
                logger.info("拉取openid列表：第{}页，数量：{}", page++, wxMpUserList.getCount());
                List<String> openids = wxMpUserList.getOpenids();
                syncWxUsers(openids);
                nextOpenid = wxMpUserList.getNextOpenid();
                hasMore = StringUtils.hasText(nextOpenid) && wxMpUserList.getCount() >= 10000;
            }
        } catch (WxErrorException e) {
            logger.error("同步公众号粉丝出错:", e);
        } finally {
            syncWxUserTaskRunning = false;
        }
        logger.info("同步公众号粉丝列表：完成");
    }

    /**
     * 通过传入的openid列表，同步用户列表
     *
     * @param openids
     */
    private void syncWxUsers(List<String> openids) {
        if (openids.size() < 1) {
            return;
        }
        //截取首个openid的一部分做批次号（打印日志时使用，无实际意义）
        final String batch = openids.get(0).substring(20);
        WxMpUserService wxMpUserService = wxMpService.getUserService();
        int start = 0, batchSize = openids.size(), end = Math.min(100, batchSize);
        logger.info("开始处理批次：{}，批次数量：{}", batch, batchSize);
        //分批处理,每次最多拉取100个用户信息
        while (start < end && end <= batchSize) {
            final int finalStart = start, finalEnd = end;
            final List<String> subOpenids = openids.subList(finalStart, finalEnd);
            //使用线程池同步数据，否则大量粉丝数据需同步时会很慢
            TaskExcutor.submit(() -> {
                logger.info("同步批次:【{}--{}-{}】，数量：{}", batch, finalStart, finalEnd, subOpenids.size());
                List<WxMpUser> wxMpUsers = null;//批量获取用户信息，每次最多100个
                try {
                    wxMpUsers = wxMpUserService.userInfoList(subOpenids);
                } catch (WxErrorException e) {
                    logger.error("同步出错，批次：【{}--{}-{}】，错误信息：{}", batch, finalStart, finalEnd, e);
                }
                if (wxMpUsers != null && !wxMpUsers.isEmpty()) {
                    List<WxUserEntity> wxUsers = wxMpUsers.parallelStream().map(WxUserEntity::new).collect(Collectors.toList());
                    this.saveOrUpdateBatch(wxUsers);
                }
            });
            start = end;
            end = Math.min(end + 100, openids.size());
        }
        logger.info("批次：{}处理完成", batch);
    }
}
