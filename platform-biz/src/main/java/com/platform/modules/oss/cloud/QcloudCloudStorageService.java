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
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 腾讯云存储
 *
 * @author 李鹏军
 * @since 2019-01-17 16:21:01
 */
public class QcloudCloudStorageService extends AbstractCloudStorageService {
    private COSClient client;

    public QcloudCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {
        COSCredentials cred = new BasicCOSCredentials(config.getQcloudSecretId(), config.getQcloudSecretKey());

        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(config.getQcloudRegion());
        ClientConfig clientConfig = new ClientConfig(region);

        // 3 生成 cos 客户端。
        client = new COSClient(cred, clientConfig);
    }

    @Override
    public String upload(byte[] data, String path) {
        return this.upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        //腾讯云必需要以"/"开头
        if (!path.startsWith(Constant.SLASH)) {
            path = "/" + path;
        }

        try {
            //上传到腾讯云
            PutObjectRequest putObjectRequest = new PutObjectRequest(config.getQcloudBucketName(), path, inputStream, new ObjectMetadata());
            client.putObject(putObjectRequest);
            return config.getQcloudDomain() + path;
        } catch (Exception e) {
            throw new BusinessException("上传文件失败", e);
        }
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getQcloudPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
    }
}
