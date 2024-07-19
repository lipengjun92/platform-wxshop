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
package com.platform.modules.gen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 名称：ResultMap <br>
 * 描述：查询表信息返回的BaseResultMap<br>
 *
 * @author lipengjun
 * @since 2019-01-24 14:20
 */
@Data
public class ResultMapEntity {
    /**
     * 表名称
     */
    @TableId
    private String tableName;
    /**
     * engine
     */
    private String engine;
    /**
     * 表注释
     */
    private String tableComment;
    /**
     * 创建时间
     */
    private Date createTime;
}
