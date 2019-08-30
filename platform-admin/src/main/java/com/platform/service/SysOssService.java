package com.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.entity.SysOssEntity;
import com.platform.utils.PageUtilsPlus;

import java.util.Map;

/**
 * 文件上传Service
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * @date 2017-03-25 12:13:26
 */
public interface SysOssService extends IService<SysOssEntity> {

    /**
     * queryPage
     *
     * @param params
     * @return
     */
    PageUtilsPlus queryPage(Map<String, Object> params);
}
