package com.platform.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.platform.annotation.IgnoreAuth;
import com.platform.entity.AdVo;
import com.platform.entity.BrandVo;
import com.platform.entity.CartVo;
import com.platform.entity.CategoryVo;
import com.platform.entity.ChannelVo;
import com.platform.entity.GoodsVo;
import com.platform.entity.TopicVo;
import com.platform.service.ApiAdService;
import com.platform.service.ApiBrandService;
import com.platform.service.ApiCartService;
import com.platform.service.ApiCategoryService;
import com.platform.service.ApiChannelService;
import com.platform.service.ApiGoodsService;
import com.platform.service.ApiTopicService;
import com.platform.util.ApiBaseAction;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@Api(tags = "首页接口文档")
@RestController
@RequestMapping("/api/index")
public class ApiIndexController extends ApiBaseAction {
    @Autowired
    private ApiAdService adService;
    @Autowired
    private ApiChannelService channelService;
    @Autowired
    private ApiGoodsService goodsService;
    @Autowired
    private ApiBrandService brandService;
    @Autowired
    private ApiTopicService topicService;
    @Autowired
    private ApiCategoryService categoryService;
    @Autowired
    private ApiCartService cartService;
 
    /**
     * 测试
     */
    @IgnoreAuth
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object test() {
        return toResponsMsgSuccess("请求成功yyy");
    }

    /**
     * app首页
     */
    @ApiOperation(value = "首页")
    @IgnoreAuth
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public Object index() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ad_position_id", 1);
        List<AdVo> banner = adService.queryList(param);
        resultObj.put("banner", banner);
        //
        param = new HashMap<String, Object>();
        param.put("sidx", "sort_order ");
        param.put("order", "asc ");
        List<ChannelVo> channel = channelService.queryList(param);
        resultObj.put("channel", channel);
        //
        param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("is_delete", 0);
        param.put("fields", "id, name, list_pic_url, retail_price");
        PageHelper.startPage(0, 4,false);
        List<GoodsVo> newGoods = goodsService.queryList(param);
        resultObj.put("newGoodsList", newGoods);
        //
        param = new HashMap<String, Object>();
        param.put("is_hot", "1");
        param.put("is_delete", 0);
        PageHelper.startPage(0, 3,false);
        List<GoodsVo> hotGoods = goodsService.queryHotGoodsList(param);
        resultObj.put("hotGoodsList", hotGoods);
        // 当前购物车中
        List<CartVo> cartList = new ArrayList<CartVo>();
        if (null != getUserId()) {
            //查询列表数据
            Map<String, Object> cartParam = new HashMap<String, Object>();
            cartParam.put("user_id", getUserId());
            cartList = cartService.queryList(cartParam);
        }
        if (null != cartList && cartList.size() > 0 && null != hotGoods && hotGoods.size() > 0) {
            for (GoodsVo goodsVo : hotGoods) {
                for (CartVo cartVo : cartList) {
                    if (goodsVo.getId().equals(cartVo.getGoods_id())) {
                        goodsVo.setCart_num(cartVo.getNumber());
                    }
                }
            }
        }
        //
        param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("sidx", "new_sort_order ");
        param.put("order", "asc ");
        param.put("offset", 0);
        param.put("limit", 4);
        List<BrandVo> brandList = brandService.queryList(param);
        resultObj.put("brandList", brandList);

        param = new HashMap<String, Object>();
        param.put("offset", 0);
        param.put("limit", 3);
        List<TopicVo> topicList = topicService.queryList(param);
        resultObj.put("topicList", topicList);

        param = new HashMap<String, Object>();
        param.put("parent_id", 0);
        param.put("notName", "推荐");//<>
        List<CategoryVo> categoryList = categoryService.queryList(param);
        List< Map<String, Object>> newCategoryList = new ArrayList<>();

        for (CategoryVo categoryItem : categoryList) {
            param.remove("fields");
            param.put("parent_id", categoryItem.getId());
            List<CategoryVo> categoryEntityList = categoryService.queryList(param);
            List<Integer> childCategoryIds = new ArrayList<>();
            for (CategoryVo categoryEntity : categoryEntityList) {
                childCategoryIds.add(categoryEntity.getId());
            }
            //
            param = new HashMap<String, Object>();
            param.put("categoryIds", childCategoryIds);
            param.put("fields", "id as id, name as name, list_pic_url as list_pic_url, retail_price as retail_price");
            PageHelper.startPage(0, 7,false);
            List<GoodsVo> categoryGoods = goodsService.queryList(param);
            Map<String, Object> newCategory = new HashMap<String, Object>();
            newCategory.put("id", categoryItem.getId());
            newCategory.put("name", categoryItem.getName());
            newCategory.put("goodsList", categoryGoods);
            newCategoryList.add(newCategory);
        }
        resultObj.put("categoryList", newCategoryList);
        return toResponsSuccess(resultObj);
    }
    

    /**
     * app首页
     */
    @ApiOperation(value = "新商品信息")
    @IgnoreAuth
    @RequestMapping(value = "newGoods", method = RequestMethod.GET)
    public Object newGoods() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("is_delete", 0);
        param.put("fields", "id, name, list_pic_url, retail_price");
        PageHelper.startPage(0, 4,false);
        List<GoodsVo> newGoods = goodsService.queryList(param);
        resultObj.put("newGoodsList", newGoods);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "新热门商品信息")
    @IgnoreAuth
    @RequestMapping(value = "hotGoods", method = RequestMethod.GET)
    public Object hotGoods() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("is_hot", "1");
        param.put("is_delete", 0);
        PageHelper.startPage(0, 3,false);
        List<GoodsVo> hotGoods = goodsService.queryHotGoodsList(param);
        resultObj.put("hotGoodsList", hotGoods);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "topic")
    @IgnoreAuth
    @RequestMapping(value = "topic", method = RequestMethod.GET)
    public Object topic() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
    	  param.put("offset", 0);
          param.put("limit", 3);
          List<TopicVo> topicList = topicService.queryList(param);
          resultObj.put("topicList", topicList);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "brand")
    @IgnoreAuth
    @RequestMapping(value = "brand", method = RequestMethod.GET)
    public Object brand() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
    	 param.put("is_new", 1);
         param.put("sidx", "new_sort_order ");
         param.put("order", "asc ");
         param.put("offset", 0);
         param.put("limit", 4);
         List<BrandVo> brandList = brandService.queryList(param);
         resultObj.put("brandList", brandList);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "category")
    @IgnoreAuth
    @RequestMapping(value = "category", method = RequestMethod.GET)
    public Object category() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
    	 param = new HashMap<String, Object>();
         param.put("parent_id", 0);
         param.put("notName", "推荐");//<>
         List<CategoryVo> categoryList = categoryService.queryList(param);
         List< Map<String, Object>> newCategoryList = new ArrayList<>();

         for (CategoryVo categoryItem : categoryList) {
             param.remove("fields");
             param.put("parent_id", categoryItem.getId());
             List<CategoryVo> categoryEntityList = categoryService.queryList(param);
             List<Integer> childCategoryIds = new ArrayList<>();
             for (CategoryVo categoryEntity : categoryEntityList) {
                 childCategoryIds.add(categoryEntity.getId());
             }
             //
             param = new HashMap<String, Object>();
             param.put("categoryIds", childCategoryIds);
             param.put("fields", "id as id, name as name, list_pic_url as list_pic_url, retail_price as retail_price");
             PageHelper.startPage(0, 7,false);
             List<GoodsVo> categoryGoods = goodsService.queryList(param);
             Map<String, Object> newCategory = new HashMap<String, Object>();
             newCategory.put("id", categoryItem.getId());
             newCategory.put("name", categoryItem.getName());
             newCategory.put("goodsList", categoryGoods);
             newCategoryList.add(newCategory);
         }
         resultObj.put("categoryList", newCategoryList);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "banner")
    @IgnoreAuth
    @RequestMapping(value = "banner", method = RequestMethod.GET)
    public Object banner() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
    	   param.put("ad_position_id", 1);
           List<AdVo> banner = adService.queryList(param);
           resultObj.put("banner", banner);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "channel")
    @IgnoreAuth
    @RequestMapping(value = "channel", method = RequestMethod.GET)
    public Object channel() {
    	Map<String, Object> resultObj = new HashMap<String, Object>();
    	//
    	Map<String, Object> param = new HashMap<String, Object>();
    	 param = new HashMap<String, Object>();
         param.put("sidx", "sort_order ");
         param.put("order", "asc ");
         List<ChannelVo> channel = channelService.queryList(param);
         resultObj.put("channel", channel);
    	//
    	
    	return toResponsSuccess(resultObj);
    }
    
    /*IndexUrlNewGoods: NewApiRootUrl + 'index/newGoods', //
    IndexUrlHotGoods: NewApiRootUrl + 'index/hotGoods', //首页数据接口
    IndexUrlTopic: NewApiRootUrl + 'index/topic', //首页数据接口
    IndexUrlBrand: NewApiRootUrl + 'index/brand', //首页数据接口IndexUrlChannel
    IndexUrlCategory: NewApiRootUrl + 'index/category', //首页数据接口IndexUrlChannel
    IndexUrlBanner: NewApiRootUrl + 'index/banner', //首页数据接口IndexUrlChannel
    IndexUrlChannel: NewApiRootUrl + 'index/channel', //首页数据接口IndexUrlChannel
    */
}