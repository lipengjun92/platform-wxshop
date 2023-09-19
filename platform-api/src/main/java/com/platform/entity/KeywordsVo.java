package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 热闹关键词表
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:39
 */
@Data
public class KeywordsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //关键字
    private String keyword;
    //热销
    private Integer isHot;
    //默认
    private Integer isDefault;
    //显示
    private Integer isShow;
    //排序
    private Integer sortOrder;
    //关键词的跳转链接
    private String schemeUrl;
    //主键
    private Integer id;
    //类型
    private Integer type;
}
