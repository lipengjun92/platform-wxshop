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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 组织机构实体
 * 表名 sys_org
 *
 * @author 李鹏军
 * @since 2019-01-21 11:29:22
 */
@Data
@TableName("SYS_ORG")
public class SysOrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 机构编码
     */
    @TableId
    private String orgNo;
    /**
     * 机构名称
     */
    @NotBlank(message = "机构名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String orgName;
    /**
     * 上级机构ID，一级机构为0
     */
    @NotBlank(message = "上级机构不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String parentNo;
    /**
     * 级别
     */
    @NotNull(message = "机构级别不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer orgType;
    /**
     * 状态  0：无效   1：有效
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建者ID
     */
    private String createUserId;
    /**
     * 创建人所属部门
     */
    private String createUserOrgNo;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String parentName;
}
