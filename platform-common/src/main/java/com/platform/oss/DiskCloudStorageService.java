package com.platform.oss;

import com.platform.utils.RRException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务器存储
 *
 * @author 李鹏军
 * @email 939961241@qq.com
 * @date 2019-01-17 16:21:01
 */
public class DiskCloudStorageService extends CloudStorageService {

    public DiskCloudStorageService(CloudStorageConfig config) {
        this.config = config;
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

        //本地存储必需要以"/"开头
        if (!path.startsWith("/")) {
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
            FileImageOutputStream imageOutput = new FileImageOutputStream(file);//打开输入流
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("上传文件失败", e);
        }

        return config.getProxyServer() + path;
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
