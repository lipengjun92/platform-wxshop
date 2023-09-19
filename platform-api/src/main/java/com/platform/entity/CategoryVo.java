package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:39
 */
@Data
public class CategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //分类名称
    private String name;
    //关键字
    private String keywords;
    //描述
    private String frontDesc;
    //父节点
    private Integer parentId;
    //排序
    private Integer sortOrder;
    //首页展示
    private Integer showIndex;
    //显示
    private Integer isShow;
    //banner图片
    private String bannerUrl;
    //icon链接
    private String iconUrl;
    //图片
    private String imgUrl;
    //手机banner
    private String wapBannerUrl;
    //级别
    private String level;
    //类型
    private Integer type;
    //
    private String frontName;

    private Boolean checked;

    private List<CategoryVo> subCategoryList;
}
