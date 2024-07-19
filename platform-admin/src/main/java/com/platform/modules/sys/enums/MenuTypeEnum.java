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
package com.platform.modules.sys.enums;

/**
 * 名称：MenuTypeEnum <br>
 * 描述：菜单类型<br>
 *
 * @author 李鹏军
 * @version 1.0
 * @since 1.0.0
 */
public enum MenuTypeEnum {
    /**
     * 目录
     */
    CATALOG(0, "目录"),
    /**
     * 菜单
     */
    MENU(1, "菜单"),
    /**
     * 按钮
     */
    BUTTON(2, "按钮");

    private Integer value;
    private String text;

    MenuTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return text;
    }
}
