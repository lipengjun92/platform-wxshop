package com.platform.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
@Data
public class GoodsVo implements Serializable {
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
    //市场价
    private BigDecimal marketPrice;
    //零售价格(现价)
    private BigDecimal retailPrice;
    //销售量
    private Integer sellVolume;
    //主sku　productId
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
    //购物车中商品数量
    private Integer cartNum = 0;
    // 冗余
    // 产品Id
    private Integer productId;
}
