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
package com.platform.modules.mall.service.support;

import java.util.List;

/**
 * 笛卡尔积计算工具类
 *
 * @author pengjun
 */
public final class SkuCartesianHelper {

    private SkuCartesianHelper() {
    }

    public static long countCartesian(List<? extends List<?>> dimensions) {
        if (dimensions == null || dimensions.isEmpty()) {
            return 0L;
        }
        long count = 1L;
        for (List<?> dimension : dimensions) {
            if (dimension == null || dimension.isEmpty()) {
                return 0L;
            }
            count = count * dimension.size();
        }
        return count;
    }
}
