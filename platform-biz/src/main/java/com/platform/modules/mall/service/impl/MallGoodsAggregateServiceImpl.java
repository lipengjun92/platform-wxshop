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
package com.platform.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.exception.BusinessException;
import com.platform.modules.mall.dto.GoodsAggregateDTO;
import com.platform.modules.mall.entity.*;
import com.platform.modules.mall.service.*;
import com.platform.modules.mall.service.support.SkuCartesianHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品聚合服务实现类
 *
 * @author 李鹏军
 */
@Service
@RequiredArgsConstructor
public class MallGoodsAggregateServiceImpl implements MallGoodsAggregateService {

    private final MallGoodsService mallGoodsService;
    private final MallGoodsAttributeService mallGoodsAttributeService;
    private final MallGoodsGalleryService mallGoodsGalleryService;
    private final MallGoodsSpecificationService mallGoodsSpecificationService;
    private final MallProductService mallProductService;
    private final MallAttributeService mallAttributeService;
    private final MallSpecificationService mallSpecificationService;

    @Override
    public GoodsAggregateDTO getAggregate(Integer goodsId) {
        MallGoodsEntity goods = mallGoodsService.getById(goodsId);
        if (goods == null) {
            throw new BusinessException("商品不存在");
        }

        GoodsAggregateDTO dto = new GoodsAggregateDTO();
        dto.setGoods(goods);

        List<MallGoodsAttributeEntity> attributeEntities = mallGoodsAttributeService.list(
                new QueryWrapper<MallGoodsAttributeEntity>().eq("goods_id", goodsId)
        );
        Map<Integer, String> attributeNameMap = buildAttributeNameMap(attributeEntities);
        dto.setAttributes(attributeEntities.stream().map(item -> {
            GoodsAggregateDTO.AttributeItem attr = new GoodsAggregateDTO.AttributeItem();
            attr.setId(item.getId());
            attr.setGoodsId(item.getGoodsId());
            attr.setAttributeId(item.getAttributeId());
            attr.setValue(item.getValue());
            attr.setAttributeName(attributeNameMap.get(item.getAttributeId()));
            return attr;
        }).collect(Collectors.toList()));

        List<MallGoodsGalleryEntity> galleryEntities = mallGoodsGalleryService.list(
                new QueryWrapper<MallGoodsGalleryEntity>().eq("goods_id", goodsId).orderByAsc("sort_order", "id")
        );
        dto.setGallery(galleryEntities.stream().map(item -> {
            GoodsAggregateDTO.GalleryItem gallery = new GoodsAggregateDTO.GalleryItem();
            gallery.setId(item.getId());
            gallery.setGoodsId(item.getGoodsId());
            gallery.setSortOrder(item.getSortOrder());
            gallery.setImgDesc(item.getImgDesc());
            gallery.setImgUrl(item.getImgUrl() == null ? "" : new String(item.getImgUrl(), StandardCharsets.UTF_8));
            return gallery;
        }).collect(Collectors.toList()));

        List<MallGoodsSpecificationEntity> specEntities = mallGoodsSpecificationService.list(
                new QueryWrapper<MallGoodsSpecificationEntity>().eq("goods_id", goodsId).orderByAsc("specification_id", "id")
        );
        dto.setSpecs(buildSpecGroups(specEntities));

        List<MallProductEntity> productEntities = mallProductService.list(
                new QueryWrapper<MallProductEntity>().eq("goods_id", goodsId).orderByAsc("id")
        );
        dto.setSkuList(productEntities.stream().map(item -> {
            GoodsAggregateDTO.SkuItem sku = new GoodsAggregateDTO.SkuItem();
            sku.setId(item.getId());
            sku.setGoodsId(item.getGoodsId());
            sku.setGoodsSpecificationIds(item.getGoodsSpecificationIds());
            sku.setGoodsSn(item.getGoodsSn());
            sku.setGoodsNumber(item.getGoodsNumber());
            sku.setRetailPrice(item.getRetailPrice());
            sku.setMarketPrice(item.getMarketPrice());
            return sku;
        }).collect(Collectors.toList()));

        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAggregate(GoodsAggregateDTO aggregateDTO) {
        validateAggregate(aggregateDTO, false);

        MallGoodsEntity goods = aggregateDTO.getGoods();
        goods.setId(null);
        if (goods.getAddTime() == null) {
            goods.setAddTime(new Date());
        }
        mallGoodsService.add(goods);

        saveChildren(goods.getId(), aggregateDTO);
        refreshPrimaryProduct(goods.getId());

        return goods.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateAggregate(GoodsAggregateDTO aggregateDTO) {
        validateAggregate(aggregateDTO, true);

        MallGoodsEntity goods = aggregateDTO.getGoods();
        goods.setUpdateTime(new Date());
        mallGoodsService.update(goods);

        saveChildren(goods.getId(), aggregateDTO);
        refreshPrimaryProduct(goods.getId());

        return goods.getId();
    }

    private void saveChildren(Integer goodsId, GoodsAggregateDTO aggregateDTO) {
        replaceAttributes(goodsId, aggregateDTO.getAttributes());
        replaceGallery(goodsId, aggregateDTO.getGallery());
        replaceSpecifications(goodsId, aggregateDTO.getSpecs());
        replaceSku(goodsId, aggregateDTO.getSkuList(), aggregateDTO.getGoods());
    }

    private void replaceAttributes(Integer goodsId, List<GoodsAggregateDTO.AttributeItem> items) {
        mallGoodsAttributeService.remove(new QueryWrapper<MallGoodsAttributeEntity>().eq("goods_id", goodsId));
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        List<MallGoodsAttributeEntity> entities = items.stream()
                .filter(item -> item.getAttributeId() != null && StringUtils.hasText(item.getValue()))
                .map(item -> {
                    MallGoodsAttributeEntity entity = new MallGoodsAttributeEntity();
                    entity.setGoodsId(goodsId);
                    entity.setAttributeId(item.getAttributeId());
                    entity.setValue(item.getValue());
                    return entity;
                })
                .collect(Collectors.toList());
        if (!entities.isEmpty()) {
            mallGoodsAttributeService.saveBatch(entities);
        }
    }

    private void replaceGallery(Integer goodsId, List<GoodsAggregateDTO.GalleryItem> items) {
        mallGoodsGalleryService.remove(new QueryWrapper<MallGoodsGalleryEntity>().eq("goods_id", goodsId));
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        int fallbackSort = 1;
        List<MallGoodsGalleryEntity> entities = new ArrayList<>();
        for (GoodsAggregateDTO.GalleryItem item : items) {
            if (!StringUtils.hasText(item.getImgUrl())) {
                continue;
            }
            MallGoodsGalleryEntity entity = new MallGoodsGalleryEntity();
            entity.setGoodsId(goodsId);
            entity.setImgUrl(item.getImgUrl().getBytes(StandardCharsets.UTF_8));
            entity.setImgDesc(item.getImgDesc());
            entity.setSortOrder(item.getSortOrder() == null ? fallbackSort : item.getSortOrder());
            fallbackSort++;
            entities.add(entity);
        }
        if (!entities.isEmpty()) {
            mallGoodsGalleryService.saveBatch(entities);
        }
    }

    private void replaceSpecifications(Integer goodsId, List<GoodsAggregateDTO.SpecGroup> groups) {
        mallGoodsSpecificationService.remove(new QueryWrapper<MallGoodsSpecificationEntity>().eq("goods_id", goodsId));
        if (CollectionUtils.isEmpty(groups)) {
            return;
        }
        List<MallGoodsSpecificationEntity> entities = new ArrayList<>();
        for (GoodsAggregateDTO.SpecGroup group : groups) {
            if (group.getSpecificationId() == null || CollectionUtils.isEmpty(group.getValues())) {
                continue;
            }
            for (GoodsAggregateDTO.SpecValue value : group.getValues()) {
                if (!StringUtils.hasText(value.getValue())) {
                    continue;
                }
                MallGoodsSpecificationEntity entity = new MallGoodsSpecificationEntity();
                entity.setGoodsId(goodsId);
                entity.setSpecificationId(group.getSpecificationId());
                entity.setValue(value.getValue());
                entity.setPicUrl(value.getPicUrl());
                entities.add(entity);
            }
        }
        if (!entities.isEmpty()) {
            mallGoodsSpecificationService.saveBatch(entities);
        }
    }

    private void replaceSku(Integer goodsId, List<GoodsAggregateDTO.SkuItem> items, MallGoodsEntity goods) {
        mallProductService.remove(new QueryWrapper<MallProductEntity>().eq("goods_id", goodsId));

        List<MallProductEntity> entities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(items)) {
            for (GoodsAggregateDTO.SkuItem item : items) {
                MallProductEntity entity = new MallProductEntity();
                entity.setGoodsId(goodsId);
                entity.setGoodsSpecificationIds(item.getGoodsSpecificationIds());
                entity.setGoodsSn(StringUtils.hasText(item.getGoodsSn()) ? item.getGoodsSn() : goods.getGoodsSn());
                entity.setGoodsNumber(item.getGoodsNumber() == null ? goods.getGoodsNumber() : item.getGoodsNumber());
                entity.setRetailPrice(item.getRetailPrice() == null ? nvl(goods.getRetailPrice()) : item.getRetailPrice());
                entity.setMarketPrice(item.getMarketPrice() == null ? nvl(goods.getMarketPrice()) : item.getMarketPrice());
                entities.add(entity);
            }
        }

        if (entities.isEmpty()) {
            MallProductEntity defaultSku = new MallProductEntity();
            defaultSku.setGoodsId(goodsId);
            defaultSku.setGoodsSpecificationIds("");
            defaultSku.setGoodsSn(goods.getGoodsSn());
            defaultSku.setGoodsNumber(goods.getGoodsNumber());
            defaultSku.setRetailPrice(nvl(goods.getRetailPrice()));
            defaultSku.setMarketPrice(nvl(goods.getMarketPrice()));
            entities.add(defaultSku);
        }

        mallProductService.saveBatch(entities);
    }

    private void refreshPrimaryProduct(Integer goodsId) {
        MallProductEntity firstSku = mallProductService.getOne(
                new QueryWrapper<MallProductEntity>().eq("goods_id", goodsId).orderByAsc("id").last("LIMIT 1")
        );
        if (firstSku == null) {
            return;
        }
        MallGoodsEntity goods = new MallGoodsEntity();
        goods.setId(goodsId);
        goods.setPrimaryProductId(firstSku.getId());
        mallGoodsService.updateById(goods);
    }

    private void validateAggregate(GoodsAggregateDTO aggregateDTO, boolean checkId) {
        if (aggregateDTO == null || aggregateDTO.getGoods() == null) {
            throw new BusinessException("商品数据不能为空");
        }
        MallGoodsEntity goods = aggregateDTO.getGoods();

        if (checkId && goods.getId() == null) {
            throw new BusinessException("商品ID不能为空");
        }
        if (!StringUtils.hasText(goods.getName())) {
            throw new BusinessException("商品名称不能为空");
        }
        if (goods.getCategoryId() == null) {
            throw new BusinessException("商品分类不能为空");
        }
        if (goods.getRetailPrice() == null) {
            throw new BusinessException("商品零售价不能为空");
        }

        List<GoodsAggregateDTO.SpecGroup> specs = aggregateDTO.getSpecs();
        List<GoodsAggregateDTO.SkuItem> skuList = aggregateDTO.getSkuList();

        if (!CollectionUtils.isEmpty(specs)) {
            if (CollectionUtils.isEmpty(skuList)) {
                throw new BusinessException("存在规格时，SKU列表不能为空");
            }
            long expected = SkuCartesianHelper.countCartesian(specs.stream()
                    .map(item -> item.getValues() == null ? Collections.emptyList() : item.getValues())
                    .collect(Collectors.toList()));
            if (expected > 0 && expected != skuList.size()) {
                throw new BusinessException("SKU数量与规格组合数量不一致");
            }
        }

        if (!CollectionUtils.isEmpty(skuList)) {
            Set<String> keys = new HashSet<>();
            for (GoodsAggregateDTO.SkuItem sku : skuList) {
                if (!StringUtils.hasText(sku.getGoodsSpecificationIds())) {
                    continue;
                }
                if (!keys.add(sku.getGoodsSpecificationIds())) {
                    throw new BusinessException("SKU规格组合重复: " + sku.getGoodsSpecificationIds());
                }
            }
        }

        if (!CollectionUtils.isEmpty(aggregateDTO.getGallery())) {
            for (GoodsAggregateDTO.GalleryItem item : aggregateDTO.getGallery()) {
                if (!StringUtils.hasText(item.getImgUrl())) {
                    throw new BusinessException("商品相册图片不能为空");
                }
            }
        }
    }

    private List<GoodsAggregateDTO.SpecGroup> buildSpecGroups(List<MallGoodsSpecificationEntity> specEntities) {
        if (CollectionUtils.isEmpty(specEntities)) {
            return Collections.emptyList();
        }
        Set<Integer> specIds = specEntities.stream().map(MallGoodsSpecificationEntity::getSpecificationId).collect(Collectors.toSet());
        Map<Integer, String> specNameMap = mallSpecificationService.listByIds(specIds).stream()
                .collect(Collectors.toMap(MallSpecificationEntity::getId, MallSpecificationEntity::getName, (a, b) -> a));

        Map<Integer, GoodsAggregateDTO.SpecGroup> groupMap = new LinkedHashMap<>();
        for (MallGoodsSpecificationEntity specEntity : specEntities) {
            GoodsAggregateDTO.SpecGroup group = groupMap.computeIfAbsent(specEntity.getSpecificationId(), key -> {
                GoodsAggregateDTO.SpecGroup g = new GoodsAggregateDTO.SpecGroup();
                g.setSpecificationId(key);
                g.setSpecificationName(specNameMap.get(key));
                return g;
            });
            GoodsAggregateDTO.SpecValue value = new GoodsAggregateDTO.SpecValue();
            value.setValue(specEntity.getValue());
            value.setPicUrl(specEntity.getPicUrl());
            group.getValues().add(value);
        }
        return new ArrayList<>(groupMap.values());
    }

    private Map<Integer, String> buildAttributeNameMap(List<MallGoodsAttributeEntity> attributeEntities) {
        if (CollectionUtils.isEmpty(attributeEntities)) {
            return Collections.emptyMap();
        }
        Set<Integer> ids = attributeEntities.stream()
                .map(MallGoodsAttributeEntity::getAttributeId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return mallAttributeService.listByIds(ids).stream()
                .collect(Collectors.toMap(MallAttributeEntity::getId, MallAttributeEntity::getName, (a, b) -> a));
    }

    private BigDecimal nvl(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
