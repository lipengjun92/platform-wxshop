package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:39
 */
@Data
public class CollectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //用户Id
    private Long userId;
    //产品Id
    private Integer valueId;
    //添加时间
    private Long addTime;
    //是否是关注
    private Integer isAttention;
    //
    private Integer typeId;
    //
    private String name;
    private String listPicUrl;
    private String goodsBrief;
    private String retailPrice;
}
