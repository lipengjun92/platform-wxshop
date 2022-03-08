package com.platform.oss;

import com.platform.utils.RRException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 服务器存储
 *
 * @author 沈明开
 * @email 1193136099@qq.com
 * @date 2022-03-06 16:21:01
 */
public class MinioCloudStorageService extends CloudStorageService {

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
    public String upload(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return upload(file.getBytes(), getPath("") + "." + prefix);
    }

    @Override
    public String upload(byte[] data, String path) {
        if (data.length < 3 || path.equals(""))
            throw new RRException("上传文件为空");

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
            throw new RRException("上传文件失败", e);
        }
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new RRException("上传文件失败", e);
        }
    }
}
