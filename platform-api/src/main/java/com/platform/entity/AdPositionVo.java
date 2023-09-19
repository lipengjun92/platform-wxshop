package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class AdPositionVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //位置名称
    private String name;
    //宽度
    private Integer width;
    //高度
    private Integer height;
    //描述
    private String desc;
}
