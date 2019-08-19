package com.platform.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 名称：SmsConfig <br>
 * 描述：短信配置信息<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
@Data
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：腾讯云
     */
    @Range(min = 1, max = 3, message = "类型错误")
    private Integer type;

    /**
     * appid
     */
    private int appid;

    /**
     * key
     */
    private String appkey;

    /**
     * 签名
     */
    private String sign;
}
