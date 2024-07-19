/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典实体
 * 表名 sys_dict
 *
 * @author 李鹏军
 * @since 2019-01-15 11:42:20
 */
@Data
@TableName("SYS_DICT")
public class SysDictEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    /**
     * 所属分组ID
     */
    @NotBlank(message = "所属分组不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String groupId;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String value;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private String code;
}
