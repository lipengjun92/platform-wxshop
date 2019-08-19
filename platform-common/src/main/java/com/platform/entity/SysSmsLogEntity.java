package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 发送短信日志
 * 表名 sys_sms_log
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-12-16 23:38:05
 */
@Data
public class SysSmsLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 操作人
     */
    private Long userId;
    /**
     * 必填参数。手机号码。多个以英文逗号隔开
     */
    private String mobile;
    /**
     * 发送时间
     */
    private Date stime;
    /**
     * 模板ID
     */
    private int templateId;
    /**
     * 验证码
     */
    private String code;
    /**
     * 必填参数。用户签名
     */
    private String sign;
    /**
     * 1成功 0失败
     */
    private Integer sendStatus;
    /**
     * 发送编号
     */
    private String sendId;
    /**
     * 成功提交数
     */
    private Integer successNum;
    /**
     * 返回消息
     */
    private String returnMsg;

    //翻译
    /**
     * 操作人
     */
    private String userName;
}
