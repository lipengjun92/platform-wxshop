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
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author 李鹏军
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {
    /**
     * 获取图片验证码
     *
     * @param uuid uuid
     * @return BufferedImage
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     *
     * @param uuid uuid
     * @param code code
     * @return 校验结果
     */
    boolean validate(String uuid, String code);
}
