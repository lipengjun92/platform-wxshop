package com.platform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 实体表名 nideshop_sms_log
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-25 10:04:52
 */
@Data
public class SmsLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Long userId;
    //
    private String phone;
    //
    private Long logDate;
    // 发送模板
    private int smsCode;
    // 1成功 0失败
    private Integer sendStatus;
    //
    private String smsText;
}
