package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户Token
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-03-23 15:22:07
 */
@Data
public class TokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户ID
    private Long userId;
    //token
    private String token;
    //过期时间
    private Date expireTime;
    //更新时间
    private Date updateTime;
}
