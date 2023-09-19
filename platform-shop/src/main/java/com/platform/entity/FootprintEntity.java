package com.platform.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.utils.JsonTimeSerializer;
import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:08
 */
@Data
public class FootprintEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //会员Id
    private Long userId;
    private String userName;
    //商品id
    private Integer goodsId;
    private String goodsName;
    //记录时间
    @JsonSerialize(using = JsonTimeSerializer.class)
    private Long addTime;
}
