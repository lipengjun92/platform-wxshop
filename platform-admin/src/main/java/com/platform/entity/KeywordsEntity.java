package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 热闹关键词表实体
 * 表名 nideshop_keywords
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-25 21:23:41
 */
@Data
public class KeywordsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private String keyword;
    //
    private Integer isHot;
    //
    private Integer isDefault;
    //
    private Integer isShow;
    //
    private Integer sortOrder;
    //关键词的跳转链接
    private String schemeUrl;
    //
    private Integer type;
}
