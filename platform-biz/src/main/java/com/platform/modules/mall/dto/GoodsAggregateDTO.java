/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.mall.dto;

import com.platform.modules.mall.entity.MallGoodsEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品聚合DTO
 *
 * @author pengjun
 */
@Data
public class GoodsAggregateDTO {

    private MallGoodsEntity goods;
    private List<AttributeItem> attributes = new ArrayList<>();
    private List<GalleryItem> gallery = new ArrayList<>();
    private List<SpecGroup> specs = new ArrayList<>();
    private List<SkuItem> skuList = new ArrayList<>();

    @Data
    public static class AttributeItem {
        private Integer id;
        private Integer goodsId;
        private Integer attributeId;
        private String attributeName;
        private String value;
    }

    @Data
    public static class GalleryItem {
        private Integer id;
        private Integer goodsId;
        private String imgUrl;
        private String imgDesc;
        private Integer sortOrder;
    }

    @Data
    public static class SpecGroup {
        private Integer specificationId;
        private String specificationName;
        private List<SpecValue> values = new ArrayList<>();
    }

    @Data
    public static class SpecValue {
        private String value;
        private String picUrl;
    }

    @Data
    public static class SkuItem {
        private Integer id;
        private Integer goodsId;
        private String goodsSpecificationIds;
        private String goodsSn;
        private Integer goodsNumber;
        private BigDecimal retailPrice;
        private BigDecimal marketPrice;
    }
}
