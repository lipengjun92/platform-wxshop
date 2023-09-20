package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 规格表
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:10
 */
@Data
public class SpecificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //规范名称
    private String name;
    //排序
    private Integer sortOrder;
}
