package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.*;
import com.platform.modules.mall.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 *
 * 描述: AppIndexController <br>
 */
@Tag(name = "首页接口-AppIndexController")
@RestController
@RequestMapping("/app/index")
public class AppIndexController extends AppBaseController {
    @Autowired
    private MallAdService adService;
    @Autowired
    private MallChannelService channelService;
    @Autowired
    private MallGoodsService goodsService;
    @Autowired
    private MallBrandService brandService;
    @Autowired
    private MallTopicService topicService;
    @Autowired
    private MallCategoryService categoryService;
    @Autowired
    private MallCartService cartService;

    /**
     * app首页
     */
    @Operation(summary = "首页")
    @IgnoreAuth
    @PostMapping("index")
    public RestResponse<Object> index() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("adPositionId", 1);
        List<MallAdEntity> banner = adService.queryList(param);
        resultObj.put("banner", banner);
        //
        param = new HashMap<String, Object>();
        param.put("sidx", "sort_order ");
        param.put("order", "asc ");
        List<MallChannelEntity> channel = channelService.queryList(param);
        resultObj.put("channel", channel);
        //
        QueryWrapper<MallGoodsEntity> newGoodsWrapper = new QueryWrapper<>();
        newGoodsWrapper.select("id", "name", "list_pic_url", "retail_price").eq("IS_NEW", 1).eq("IS_DELETE", 0).orderByDesc("ID");
        Page<MallGoodsEntity> newGoodsPage = goodsService.page(new Page<>(1, 4), newGoodsWrapper);
        List<MallGoodsEntity> newGoods = newGoodsPage.getRecords();
        resultObj.put("newGoodsList", newGoods);
        //
        QueryWrapper<MallGoodsEntity> hotGoodsWrapper = new QueryWrapper<>();
        hotGoodsWrapper.eq("IS_HOT", 1).eq("IS_DELETE", 0).orderByDesc("ID");
        Page<MallGoodsEntity> hotGoodsPage = goodsService.page(new Page<>(1, 4), hotGoodsWrapper);
        List<MallGoodsEntity> hotGoods = hotGoodsPage.getRecords();
        resultObj.put("hotGoodsList", hotGoods);
        // 当前购物车中
        List<MallCartEntity> cartList = new ArrayList<MallCartEntity>();
        if (null != getUserId()) {
            //查询列表数据
            Map<String, Object> cartParam = new HashMap<String, Object>();
            cartParam.put("userId", getUserId());
            cartList = cartService.queryList(cartParam);
        }
        if (null != cartList && cartList.size() > 0 && null != hotGoods && hotGoods.size() > 0) {
            for (MallGoodsEntity goodsVo : hotGoods) {
                for (MallCartEntity cartVo : cartList) {
                    if (goodsVo.getId().equals(cartVo.getGoodsId())) {
                        goodsVo.setCartNum(cartVo.getNumber());
                    }
                }
            }
        }
        //
        param = new HashMap<String, Object>();
        param.put("isNew", 1);
        param.put("sidx", "new_sort_order ");
        param.put("order", "asc ");
        param.put("offset", 0);
        param.put("limit", 4);
        Query<MallBrandEntity> query = new Query<>(param);
        List<MallBrandEntity> brandList = brandService.queryList(query);
        resultObj.put("brandList", brandList);

        param = new HashMap<String, Object>();
        param.put("offset", 0);
        param.put("limit", 4);
        query = new Query<>(param);
        List<MallTopicEntity> topicList = topicService.queryList(query);
        resultObj.put("topicList", topicList);

        param = new HashMap<String, Object>();
        param.put("parentId", 0);
        param.put("notName", "推荐");
        query = new Query<>(param);
        List<MallCategoryEntity> categoryList = categoryService.queryList(query);
        List<Map<String, Object>> newCategoryList = new ArrayList<>();

        for (MallCategoryEntity categoryItem : categoryList) {
            param.remove("fields");
            param.put("parentId", categoryItem.getId());
            List<MallCategoryEntity> categoryEntityList = categoryService.queryList(query);
            List<Integer> childCategoryIds = new ArrayList<>();
            for (MallCategoryEntity categoryEntity : categoryEntityList) {
                childCategoryIds.add(categoryEntity.getId());
            }
            //
            param = new HashMap<String, Object>();
            QueryWrapper<MallGoodsEntity> categoryGoodsWrapper = new QueryWrapper<>();
            categoryGoodsWrapper.in(!childCategoryIds.isEmpty(), "CATEGORY_ID", childCategoryIds)
                    .select("id, name, list_pic_url, retail_price")
                    .eq("IS_DELETE", 0)
                    .orderByDesc("ID");
            Page<MallGoodsEntity> categoryGoodsPage = goodsService.page(new Page<>(1, 7), categoryGoodsWrapper);
            List<MallGoodsEntity> categoryGoods = categoryGoodsPage.getRecords();
            Map<String, Object> newCategory = new HashMap<String, Object>();
            newCategory.put("id", categoryItem.getId());
            newCategory.put("name", categoryItem.getName());
            newCategory.put("goodsList", categoryGoods);
            newCategoryList.add(newCategory);
        }
        resultObj.put("categoryList", newCategoryList);
        return RestResponse.ok(resultObj);
    }


    /**
     * app首页
     */
    @Operation(summary = "新商品信息")
    @IgnoreAuth
    @PostMapping("newGoods")
    public RestResponse<Object> newGoods() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("isNew", 1);
        param.put("isDelete", 0);
        QueryWrapper<MallGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_NEW", 1).eq("IS_DELETE", 0).orderByDesc("ID");
        List<MallGoodsEntity> newGoods = goodsService.page(new Page<>(1, 4), queryWrapper).getRecords();
        resultObj.put("newGoodsList", newGoods);
        //

        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "新热门商品信息")
    @IgnoreAuth
    @PostMapping("hotGoods")
    public RestResponse<Object> hotGoods() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("isHot", "1");
        param.put("isDelete", 0);
        QueryWrapper<MallGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_HOT", 1).eq("IS_DELETE", 0).orderByDesc("ID");
        List<MallGoodsEntity> hotGoods = goodsService.page(new Page<>(1, 4), queryWrapper).getRecords();
        resultObj.put("hotGoodsList", hotGoods);
        //

        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "专题")
    @IgnoreAuth
    @PostMapping("topic")
    public RestResponse<Object> topic() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("offset", 0);
        param.put("limit", 4);
        List<MallTopicEntity> topicList = topicService.queryList(param);
        resultObj.put("topicList", topicList);
        //

        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "品牌制造商")
    @IgnoreAuth
    @PostMapping("brand")
    public RestResponse<Object> brand() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("isNew", 1);
        param.put("sidx", "new_sort_order ");
        param.put("order", "asc ");
        param.put("offset", 0);
        param.put("limit", 4);
        List<MallBrandEntity> brandList = brandService.queryList(param);
        resultObj.put("brandList", brandList);
        //

        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "商品分类及分类下的商品")
    @IgnoreAuth
    @PostMapping("category")
    public RestResponse<Object> category() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param = new HashMap<String, Object>();
        param.put("parentId", "0");
        param.put("notName", "推荐");
        List<MallCategoryEntity> categoryList = categoryService.queryList(param);
        List<Map<String, Object>> newCategoryList = new ArrayList<>();

        for (MallCategoryEntity categoryItem : categoryList) {
            param.remove("fields");
            param.put("parentId", categoryItem.getId());
            List<MallCategoryEntity> categoryEntityList = categoryService.queryList(param);
            List<Integer> childCategoryIds = null;
            if (categoryEntityList != null && !categoryEntityList.isEmpty()) {
                childCategoryIds = new ArrayList<>();
                for (MallCategoryEntity categoryEntity : categoryEntityList) {
                    childCategoryIds.add(categoryEntity.getId());
                }
            }
            //
            param = new HashMap<String, Object>();
            QueryWrapper<MallGoodsEntity> categoryGoodsWrapper = new QueryWrapper<>();
            categoryGoodsWrapper.in(childCategoryIds != null && !childCategoryIds.isEmpty(), "CATEGORY_ID", childCategoryIds)
                    .eq("IS_DELETE", 0)
                    .select("id, name, list_pic_url, retail_price")
                    .orderByDesc("ID");
            List<MallGoodsEntity> categoryGoods = goodsService.page(new Page<>(1, 7), categoryGoodsWrapper).getRecords();
            Map<String, Object> newCategory = new HashMap<String, Object>();
            newCategory.put("id", categoryItem.getId());
            newCategory.put("name", categoryItem.getName());
            newCategory.put("goodsList", categoryGoods);
            newCategoryList.add(newCategory);
        }
        resultObj.put("categoryList", newCategoryList);
        //

        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "轮播图")
    @IgnoreAuth
    @PostMapping("banner")
    public RestResponse<Object> banner() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("adPositionId", 1);
        List<MallAdEntity> banner = adService.queryList(param);
        resultObj.put("banner", banner);
        //

        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "快捷类目")
    @IgnoreAuth
    @PostMapping("channel")
    public RestResponse<Object> channel() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param = new HashMap<String, Object>();
        param.put("sidx", "sort_order ");
        param.put("order", "asc ");
        List<MallChannelEntity> channel = channelService.queryList(param);
        resultObj.put("channel", channel);
        //

        return RestResponse.ok(resultObj);
    }
}
