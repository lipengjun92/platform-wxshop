package com.platform.entity;

import lombok.Data;

/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
@Data
public class RegionVo {

    //主键
    private Integer id;
    //父节点
    private Integer parentId;
    //区域名称
    private String name;
    //类型 0国家 1省份 2地市 3区县
    private Integer type;
    //区域代理Id
    private Integer agencyId;

    public RegionVo(SysRegionEntity regionEntity) {
        id = regionEntity.getId();
        parentId = regionEntity.getParentId();
        name = regionEntity.getName();
        type = regionEntity.getType();
        agencyId = regionEntity.getAgencyId();
    }
}
