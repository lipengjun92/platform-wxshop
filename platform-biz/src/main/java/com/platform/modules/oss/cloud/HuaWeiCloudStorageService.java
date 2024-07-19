package com.platform.modules.oss.cloud;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import org.apache.commons.io.IOUtils;

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
    public String upload(byte[] data, String path) {
        if (data.length < Constant.THREE || Constant.BLANK.equals(path)) {
            throw new BusinessException("上传文件为空");
        }

        try {

            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            PutObjectResult result = obsClient.putObject(config.getHuaweiBucketName(), path, inputStream);

            return "https://" + config.getHuaweiBucketName() + "." + config.getHuaweiEndPoint() + "/" + result.getObjectKey();
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
        return upload(data, getPath(config.getHuaweiPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getHuaweiPrefix(), suffix));
    }
}
