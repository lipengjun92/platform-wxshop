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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色与机构对应关系实体
 * 表名 sys_role_org
 *
 * @author 李鹏军
 * @since 2019-01-21 17:20:07
 */
@Data
@TableName("SYS_ROLE_ORG")
public class SysRoleOrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 机构ID
     */
    private String orgNo;
}
