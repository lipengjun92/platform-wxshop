package com.platform.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.sys.entity.SysRegionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-11-04 11:19:31
 */
@Mapper
public interface SysRegionDao extends BaseMapper<SysRegionEntity> {

    List<SysRegionEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);
}
