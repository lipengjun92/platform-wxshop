package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 实体
 * 表名 nideshop_comment
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-28 17:03:40
 */
@Data
public class CommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //类型
    private Integer typeId;
    //商品Id
    private Integer valueId;
    //储存为base64编码
    private String content;
    //记录时间
    private Long addTime;
    //状态
    private Integer status;
    //会员Id
    private Integer userId;

    /**
     * 翻译用字段
     */
    //会员
    private String userName;
    //商品
    private String valueName;
}
