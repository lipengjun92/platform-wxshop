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
package com.platform.modules.wx.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 李鹏军
 * @date 2021-05-06 18:30:15
 */
public interface WxAssetsService {
    /**
     * 获取素材总数
     *
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialCountResult materialCount() throws WxErrorException;

    /**
     * 获取图文素材详情
     *
     * @param mediaId
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialNews materialNewsInfo(String mediaId) throws WxErrorException;

    /**
     * 根据类别分页获取非图文素材列表
     *
     * @param type
     * @param page
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int page) throws WxErrorException;

    /**
     * 分页获取图文素材列表
     *
     * @param page
     * @return
     * @throws WxErrorException
     */
    WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int page) throws WxErrorException;

    /**
     * 添加多媒体永久素材
     *
     * @param mediaType mediaType
     * @param fileName  fileName
     * @param file      file
     * @return WxMpMaterialUploadResult
     * @throws WxErrorException
     * @throws IOException
     */
    WxMpMaterialUploadResult materialFileUpload(String mediaType, String fileName, MultipartFile file) throws WxErrorException, IOException;

    /**
     * 删除素材
     *
     * @param mediaId mediaId
     * @return boolean
     * @throws WxErrorException
     */
    boolean materialDelete(String mediaId) throws WxErrorException;
}
