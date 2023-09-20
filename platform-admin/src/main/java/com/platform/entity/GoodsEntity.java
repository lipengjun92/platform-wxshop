package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-13 10:41:08
 */
@Data
public class GoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品类型Id
    private Integer categoryId;
    //商品序列号
    private String goodsSn;
    //名称
    private String name;
    //品牌Id
    private Integer brandId;
    //商品序列号
    private Integer goodsNumber;
    //关键字
    private String keywords;
    //简明介绍
    private String goodsBrief;
    //商品描述
    private String goodsDesc;
    //上架
    private Integer isOnSale;
    //添加时间
    private Date addTime;
    //修改时间
    private Date updateTime;
    //排序
    private Integer sortOrder;
    //删除状态
    private Integer isDelete;
    //属性类别
    private Integer attributeCategory;
    //专柜价格
    private BigDecimal counterPrice;
    //附加价格
    private BigDecimal extraPrice;
    //是否新商品
    private Integer isNew;
    //商品单位
    private String goodsUnit;
    //商品主图
    private String primaryPicUrl;
    //商品列表图
    private String listPicUrl;
    //零售价格
    private BigDecimal retailPrice;
    //销售量
    private Integer sellVolume;
    //主sku　product_id
    private Integer primaryProductId;
    //单位价格，单价
    private BigDecimal unitPrice;
    //推广描述
    private String promotionDesc;
    //推广标签
    private String promotionTag;
    //APP专享价
    private BigDecimal appExclusivePrice;
    //是否是APP专属
    private Integer isAppExclusive;
    //限购
    private Integer isLimited;
    //热销
    private Integer isHot;
    //市场价
    private BigDecimal marketPrice;
    /**
     * 用户ID
     */
    private Long createUserId;
    /**
     * 用户ID
     */
    private Long createUserDeptId;
    /**
     * 用户ID
     */
    private Long updateUserId;

    List<GoodsAttributeEntity> attributeEntityList = new ArrayList<>();

    List<GoodsGalleryEntity> goodsImgList = new ArrayList<>();
    /**
     * 翻译用字段
     */
    //商品类型
    private String categoryName;
    //属性类别
    private String attributeCategoryName;
    //品牌
    private String brandName;
}
