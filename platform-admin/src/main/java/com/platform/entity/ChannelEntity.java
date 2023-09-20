package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 nideshop_channel
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-22 19:19:56
 */
@Data
public class ChannelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Integer id;
    //名称
    private String name;
    //url
    private String url;
    //iconUrl
    private String iconUrl;
    //排序
    private Integer sortOrder;
}
