package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-11-04 11:19:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SYS_REGION")
public class SysRegionEntity extends Tree<SysRegionEntity> {

    //主键
    @TableId
    private Integer id;
    //父节点
    private Integer parentId;
    //区域名称
    private String name;
    //类型 0国家 1省份 2地市 3区县
    private Integer type;
    //区域代理Id
    private Integer agencyId;

    /**
     * 翻译用字段
     */
    //父级名称
    @TableField(exist = false)
    private String parentName;
}
