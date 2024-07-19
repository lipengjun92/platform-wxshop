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
package com.platform.common.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 返回数据
 *
 * @author 李鹏军
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "响应信息")
public class RestResponse<V> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    @Schema(description = "是否成功", example = "true")
    private boolean success = true;

    /**
     * 状态码
     */
    @Schema(description = "状态码", example = "0")
    private int code = 0;

    /**
     * 状态信息
     */
    @Schema(description = "状态信息", example = "操作成功")
    private String msg = "操作成功";

    /**
     * 返回数据
     */
    @Schema(description = "返回数据")
    private V data;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳", example = "1691453288")
    private long timestamp = DateUtils.currentSeconds();

    /**
     * 成功状态码
     */
    private static final int SUCCESS_CODE = 0;
    /**
     * 失败状态码
     */
    private static final int FAIL_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public RestResponse(boolean success, int code, String msg, V data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <V> RestResponse<V> ok() {
        return new RestResponse<>(true, SUCCESS_CODE, "操作成功", null);
    }

    public static <V> RestResponse<V> ok(V data) {
        return new RestResponse<>(true, SUCCESS_CODE, "操作成功", data);
    }

    public static <V> RestResponse<V> ok(String msg) {
        return new RestResponse<>(true, SUCCESS_CODE, msg, null);
    }

    public static <V> RestResponse<V> ok(String msg, V data) {
        return new RestResponse<>(true, SUCCESS_CODE, msg, data);
    }

    public static <V> RestResponse<V> fail() {
        return new RestResponse<>(false, FAIL_CODE, "操作失败", null);
    }

    public static <V> RestResponse<V> fail(String msg) {
        return new RestResponse<>(false, FAIL_CODE, msg, null);
    }

    public static <V> RestResponse<V> fail(V data) {
        return new RestResponse<>(false, FAIL_CODE, "操作失败", data);
    }

    public static <V> RestResponse<V> fail(String msg, V data) {
        return new RestResponse<>(false, FAIL_CODE, msg, data);
    }

    public static <V> RestResponse<V> fail(int code, String msg) {
        return new RestResponse<>(false, code, msg, null);
    }
}
