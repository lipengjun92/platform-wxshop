package com.platform.modules.mall.service;

import com.platform.modules.mall.dto.GoodsAggregateDTO;

/**
 * 商品聚合服务
 *
 * @author pengjun
 */
public interface MallGoodsAggregateService {

    GoodsAggregateDTO getAggregate(Integer goodsId);

    Integer saveAggregate(GoodsAggregateDTO aggregateDTO);

    Integer updateAggregate(GoodsAggregateDTO aggregateDTO);
}
