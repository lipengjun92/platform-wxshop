package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 商城_用户级别
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class UserLevelVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //名称
    private String name;
    //描述
    private String description;
}
