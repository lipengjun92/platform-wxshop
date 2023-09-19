package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 实体
 * 表名 nideshop_goods_issue
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-23 14:12:34
 */
@Data
public class GoodsIssueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //问题
    private String question;
    //回答
    private String answer;
}
