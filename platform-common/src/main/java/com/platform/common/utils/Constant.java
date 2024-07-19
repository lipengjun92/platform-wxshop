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
package com.platform.common.utils;

/**
 * 常量
 *
 * @author 李鹏军
 */
public class Constant {
    public static final String USER_KEY = "LOGIN_USER_KEY";
    public static final String MA_APPID = "MA_APPID";
    public static final String OK = "OK";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    /**
     * 超级管理员ID
     */
    public static final String SUPER_ADMIN = "1";
    /**
     * 超级管理员所属机构
     */
    public static final String SUPER_ADMIN_ORG = "01";

    public static final String DEFAULT_PW = "888888";

    /**
     * 6小时后过期
     */
    public static final int EXPIRE = 3600 * 6;

    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * 短信配置KEY
     */
    public final static String SMS_CONFIG_KEY = "SMS_CONFIG_KEY";
    /**
     * 短信
     */
    public final static String PRE_SMS = "PRE_SMS:";

    /**
     * Cacheable缓存
     */
    public static final String CACHEABLE = "Cacheable";

    /**
     * 系统缓存前缀
     */
    public static final String SYS_CACHE = "SYS_CACHE:";

    /**
     * 业务系统缓存前缀
     */
    public static final String MTM_CACHE = "MTM_CACHE:";

    public static final String STR_ZERO = "0";
    public static final String STR_ONE = "1";
    public static final String STR_TWO = "2";
    public static final String STR_THREE = "3";
    public static final String STR_FOUR = "4";
    public static final String AES = "aes";

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    /**
     * 未关注
     */
    public static final int UNSUBSCRIBE = 0;
    /**
     * 关注
     */
    public static final int SUBSCRIBE = 1;
    /**
     * 空字符串
     */
    public static final String BLANK = "";
    /**
     * 斜杠
     */
    public static final String SLASH = "/";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 句号
     */
    public static final String DOT = ".";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 下划线
     */
    public static final String UNDERSCORE = "_";
    /**
     * 换行符
     */
    public static final String LINE_BREAK = "\\r\\n";

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public static final String BPMN20 = ".bpmn20.xml";

    public static final String IMAGE = "image";

    public static final String XML = "xml";
    public static final String PNG = "png";
    public static final String BAR = "bar";
    public static final String ZIP = "zip";
    public static final String BPMN = "bpmn";

    /**
     * 系统邮件签名
     */
    public static final String SIGNATURE_STR = "<br><font color='red'>-------------------------------------------------------------------<br>以上内容为邮件系统自动发送，请勿直接回复。</font>";

    /**
     * 系统自动邮件
     */
    public static final int SYS_SEND = 0;
    /**
     * 操作人主动邮件
     */
    public static final int USER_SEND = 1;
    public static final String NULL = "null";

    public static final String WX_MA_CONFIG = "WX_MA_CONFIG";
    public static final String WX_MP_CONFIG = "WX_MP_CONFIG";

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * 服务器存储
         */
        DISCK(4),
        /**
         * MINIO
         */
        MINIO(5),
        /**
         * 华为云
         */
        HUAWEI(6);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum SmsType {
        /**
         * 腾讯云
         */
        TX(1),
        /**
         * 阿里云
         */
        ALI(2);

        private final Integer value;

        SmsType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
