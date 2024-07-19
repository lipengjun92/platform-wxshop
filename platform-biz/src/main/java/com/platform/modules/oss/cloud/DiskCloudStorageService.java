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
package com.platform.modules.oss.cloud;

import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import org.apache.commons.io.IOUtils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务器存储
 *
 * @author 李鹏军
 * @since 2019-01-17 16:21:01
 */
public class DiskCloudStorageService extends AbstractCloudStorageService {

    public DiskCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {

    }

    @Override
    public String upload(byte[] data, String path) {
        if (data.length < Constant.THREE || "".equals(path)) {
            throw new BusinessException("上传文件为空");
        }
        //本地存储必需要以"/"开头
        if (!path.startsWith(Constant.SLASH)) {
            path = "/" + path;
        }
        try {
            String fileName = config.getDiskPath() + path;

            String dateDir = path.split("/")[1];

            //文件夹
            File dirFile = new File(config.getDiskPath() + "/" + dateDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //打开输入流
            FileImageOutputStream imageOutput = new FileImageOutputStream(file);
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("上传文件失败", e);
        }

        return config.getProxyServer() + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new BusinessException("上传文件失败", e);
        }
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath("", suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath("", suffix));
    }
}
