package com.platform.modules.sys.service;

import com.platform.modules.sys.entity.SysRegionEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-11-04 11:19:31
 */
public interface SysRegionService {

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<SysRegionEntity> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);
}
