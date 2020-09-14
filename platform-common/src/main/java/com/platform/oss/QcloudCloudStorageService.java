package com.platform.oss;

import com.platform.utils.RRException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 腾讯云存储
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-03-26 20:51
 */
public class QcloudCloudStorageService extends CloudStorageService {
    private COSClient client;

    public QcloudCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        // 初始化
        init();
    }

    private void init() {
        COSCredentials credentials = new BasicCOSCredentials(config.getQcloudSecretId(), config.getQcloudSecretKey());
        // 初始化客户端配置 设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        ClientConfig clientConfig = new ClientConfig(new Region(config.getQcloudRegion()));
        client = new COSClient(credentials, clientConfig);

    }

    @Override
    public String upload(MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();

        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return upload(file.getInputStream(), getPath(config.getQcloudPrefix()) + "." + prefix);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        // 腾讯云必需要以"/"开头
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        try {
            //上传到腾讯云
            PutObjectRequest putObjectRequest = new PutObjectRequest(config.getQcloudBucketName(), path, inputStream, new ObjectMetadata());
            client.putObject(putObjectRequest);
            return config.getQcloudDomain() + path;
        } catch (Exception e) {
            throw new RRException("上传文件失败", e);
        }
    }

    @Override
    public String upload(byte[] data, String path) {
        // 这个方法在腾讯新版sdk中已经弃用
        return null;
    }

    public void delete(String path) {
        client.deleteObject(config.getQcloudBucketName(), path);
    }
}
