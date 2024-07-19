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
package com.platform.modules.wx.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.handler.JsonArrayTypeHandler;
import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信粉丝
 *
 * @author 李鹏军
 */
@Data
@TableName(value = "WX_USER", autoResultMap = true)
public class WxUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 微信公众号openId
     */
    @TableId
    private String id;
    /**
     * 微信公众号openId
     */
    private String openid;
    /**
     * 小程序openId
     */
    private String maOpenid;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 微信昵称
     */
    private String nickname;
    /**
     * 性别(0-未知、1-男、2-女)
     */
    private int sex;
    /**
     * 城市
     */
    private String city;
    /**
     * 省份
     */
    private String province;
    /**
     * 用户头像
     */
    private String headImgUrl;
    /**
     * 订阅时间
     */
    @JSONField(name = "subscribe_time")
    private Date subscribeTime;
    /**
     * 是否关注
     */
    private boolean subscribe;
    /**
     * 用户唯一标识
     */
    private String unionId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 标签ID列表
     */
    @TableField(typeHandler = JsonArrayTypeHandler.class)
    private JSONArray tagidList;
    /**
     * 关注场景
     */
    private String subscribeScene;
    /**
     * 扫码场景值
     */
    private String qrSceneStr;
    /**
     * 总积分
     */
    private Integer allIntegral;
    /**
     * 剩余积分
     */
    private Integer integral;
    /**
     * 会员等级ID
     */
    private String userLevelId;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 注册ip
     */
    private String registerIp;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    public WxUserEntity() {
    }

    public WxUserEntity(String openid) {
        this.openid = openid;
    }

    public WxUserEntity(WxMpUser wxMpUser) {
        this.openid = wxMpUser.getOpenId();
        this.subscribe = wxMpUser.getSubscribe();
        if (wxMpUser.getSubscribe()) {
            this.subscribeTime = new Date(wxMpUser.getSubscribeTime() * 1000);
            this.unionId = wxMpUser.getUnionId();
            if (StringUtils.hasLength(wxMpUser.getRemark())) {
                this.remark = wxMpUser.getRemark();
            }
            this.tagidList = JSONArray.parseArray(JSONObject.toJSONString(wxMpUser.getTagIds()));
            this.subscribeScene = wxMpUser.getSubscribeScene();
            String qrScene = wxMpUser.getQrScene();
            this.qrSceneStr = !StringUtils.hasText(qrScene) ? wxMpUser.getQrSceneStr() : qrScene;
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
