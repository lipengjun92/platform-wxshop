package com.platform.modules.oss.cloud;

import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Minio存储
 *
 * @author 李鹏军
 * @email 939961241@qq.com
 * @date 2022-03-06 16:21:01
 */
public class MinioCloudStorageService extends AbstractCloudStorageService {

    private MinioClient minioClient;

    public MinioCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        //初始化
        init();

    }

    private void init() {
        minioClient = MinioClient.builder()
                .endpoint(config.getMinioUrl())
                .credentials(config.getMinioAccessKey(), config.getMinioSecretKey())
                .build();
    }

    @Override
    public String upload(byte[] data, String path) {
        if (data.length < Constant.THREE || Constant.BLANK.equals(path)) {
            throw new BusinessException("上传文件为空");
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(config.getMinioBucketName())
                    .object(path)
                    .stream(inputStream, data.length, -1)
                    .contentType("image/jpg")
                    .build());
            inputStream.close();
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(config.getMinioBucketName())
                    .object(path)
                    .expiry(7, TimeUnit.DAYS).build());
            return url.substring(0, url.lastIndexOf("?"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("上传文件失败", e);
        }
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
        return upload(data, getPath(Constant.BLANK, suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(Constant.BLANK, suffix));
    }
}
