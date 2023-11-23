package com.platform.oss;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import com.platform.utils.RRException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Minio存储
 *
 * @author 李鹏军
 * @email 939961241@qq.com
 * @date 2022-03-06 16:21:01
 */
public class HuaWeiCloudStorageService extends AbstractCloudStorageService {

    private ObsClient obsClient;

    public HuaWeiCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        //初始化
        init();

    }

    private void init() {
        obsClient = new ObsClient(config.getHuaweiAccessKey(), config.getHuaweiSecretKey(), config.getHuaweiEndPoint());
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return upload(file.getBytes(), getPath("") + "." + prefix);
    }

    @Override
    public String upload(byte[] data, String path) {

        try {

            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            PutObjectResult result = obsClient.putObject(config.getHuaweiBucketName(), path, inputStream);

            return "https://" + config.getHuaweiBucketName() + "." + config.getHuaweiEndPoint() + "/" + result.getObjectKey();
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
