package com.platform.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 规格表
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class SpecificationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //规范名称
    private String name;
    //排序
    private Integer sortOrder;
}
