package com.platform.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 * 表名 nideshop_help_type
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-11-07 11:04:20
 */
@Data
public class HelpTypeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 问题分类
     */
    private String typeName;
    /**
     * 排序
     */
    private Integer sort;
}
