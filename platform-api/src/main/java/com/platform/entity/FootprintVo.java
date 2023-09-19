package com.platform.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.utils.JsonDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class FootprintVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //会员Id
    private Long userId;
    //商品id
    private Integer goodsId;
    //记录时间
    @JsonSerialize(using = JsonDateSerializer.class)
    private Long addTime;
    //推荐人
    private Long referrer;

    // 商品冗余字段
    private String name;
    private String listPicUrl;
    private String goodsBrief;
    //
    private BigDecimal retailPrice;
    // 会员
    private String nickname;
    private String avatar;
}
