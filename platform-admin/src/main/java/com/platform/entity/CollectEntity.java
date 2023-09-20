package com.platform.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.utils.JsonTimeSerializer;
import lombok.Data;

import java.io.Serializable;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:06
 */
@Data
public class CollectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //用户Id
    private Long userId;
    private String userName;
    //产品Id
    private Integer valueId;
    private String valueName;
    //添加时间
    private Long addTime;
    //是否提醒
    private Integer isAttention;
    //
    private Integer typeId;
}
