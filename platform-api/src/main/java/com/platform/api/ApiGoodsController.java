package com.platform.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.entity.*;
import com.platform.service.*;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiPageUtils;
import com.platform.utils.Base64Util;
import com.platform.utils.CharUtil;
import com.platform.utils.DateUtils;
import com.platform.utils.Query;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * 描述: ApiIndexController <br>
 */
@Api(tags = "商品信息-ApiGoodsController")
@RestController
@RequestMapping("/api/goods")
public class ApiGoodsController extends ApiBaseAction {
    @Autowired
    private ApiGoodsService goodsService;
    @Autowired
    private ApiGoodsSpecificationService goodsSpecificationService;
    @Autowired
    private ApiProductService productService;
    @Autowired
    private ApiGoodsGalleryService goodsGalleryService;
    @Autowired
    private ApiGoodsIssueService goodsIssueService;
    @Autowired
    private ApiAttributeService attributeService;
    @Autowired
    private ApiBrandService brandService;
    @Autowired
    private ApiCommentService commentService;
    @Autowired
    private ApiUserService userService;
    @Autowired
    private ApiCommentPictureService commentPictureService;
    @Autowired
    private ApiCollectService collectService;
    @Autowired
    private ApiFootprintService footprintService;
    @Autowired
    private ApiCategoryService categoryService;
    @Autowired
    private ApiSearchHistoryService searchHistoryService;
    @Autowired
    private ApiRelatedGoodsService relatedGoodsService;
    @Autowired
    private ApiCouponService apiCouponService;
    @Autowired
    private ApiUserCouponService apiUserCouponService;
    @Autowired
    private ApiCartService cartService;

    /**
     */
    @ApiOperation(value = "商品首页")
    @IgnoreAuth
    @PostMapping(value = "index")
    public Object index() {
        //
        Map<String, Object> param = new HashMap<>();
        param.put("isDelete", 0);
        param.put("isOnSale", 1);
        List<GoodsVo> goodsList = goodsService.queryList(param);
        //
        return toResponseSuccess(goodsList);
    }

    /**
     * 获取商品规格信息，用于购物车编辑时选择规格
     */
    @ApiOperation(value = " 获取商品规格信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "商品id", paramType = "path", required = true)})
    @IgnoreAuth
    @PostMapping(value = "sku")
    public Object sku(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", id);
        List<GoodsSpecificationVo> goodsSpecificationEntityList = goodsSpecificationService.queryList(param);
        //
        List<ProductVo> productEntityList = productService.queryList(param);
        //
        resultObj.put("specificationList", goodsSpecificationEntityList);
        resultObj.put("productList", productEntityList);
        return toResponseSuccess(resultObj);
    }

    /**
     * 商品详情页数据
     */
    @ApiOperation(value = " 商品详情页数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "商品id", paramType = "path", required = true),
            @ApiImplicitParam(name = "referrer", value = "商品referrer", paramType = "path", required = false)})
    @PostMapping(value = "detail")
    public Object detail(Integer id, Long referrer) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        Long userId = getUserId();
        GoodsVo info = goodsService.queryObject(id);
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", id);
        //
        Map<String, Object> specificationParam = new HashMap<>();
        specificationParam.put("fields", "gs.*, s.name");
        specificationParam.put("goodsId", id);
        specificationParam.put("specification", true);
        specificationParam.put("sidx", "s.sort_order");
        specificationParam.put("order", "asc");
        List<GoodsSpecificationVo> goodsSpecificationEntityList = goodsSpecificationService.queryList(specificationParam);

        List<Map> specificationList = new ArrayList<>();
        //按规格名称分组
        for (int i = 0; i < goodsSpecificationEntityList.size(); i++) {
            GoodsSpecificationVo specItem = goodsSpecificationEntityList.get(i);
            //
            List<GoodsSpecificationVo> tempList = null;
            for (int j = 0; j < specificationList.size(); j++) {
                if (specificationList.get(j).get("specificationId").equals(specItem.getSpecificationId())) {
                    tempList = (List<GoodsSpecificationVo>) specificationList.get(j).get("valueList");
                    break;
                }
            }
            //
            if (null == tempList) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("specificationId", specItem.getSpecificationId());
                temp.put("name", specItem.getName());
                tempList = new ArrayList<>();
                tempList.add(specItem);
                temp.put("valueList", tempList);
                specificationList.add(temp);
            } else {
                for (int j = 0; j < specificationList.size(); j++) {
                    if (specificationList.get(j).get("specificationId").equals(specItem.getSpecificationId())) {
                        tempList = (List<GoodsSpecificationVo>) specificationList.get(j).get("valueList");
                        tempList.add(specItem);
                        break;
                    }
                }
            }
        }
        //
        List<ProductVo> productEntityList = productService.queryList(param);
        //
        List<GoodsGalleryVo> gallery = goodsGalleryService.queryList(param);
        Map<String, Object> ngaParam = new HashMap<>();
        ngaParam.put("fields", "nga.value, na.name");
        ngaParam.put("sidx", "nga.id");
        ngaParam.put("order", "asc");
        ngaParam.put("goodsId", id);
        List<AttributeVo> attribute = attributeService.queryList(ngaParam);
        //
        Map<String, Object> issueParam = new HashMap<>();
//        issueParam.put("goodsId", id);
        List<GoodsIssueVo> issue = goodsIssueService.queryList(issueParam);
        //
        BrandVo brand = brandService.queryObject(info.getBrandId());
        //
        param.put("valueId", id);
        param.put("typeId", 0);
        Integer commentCount = commentService.queryTotal(param);
        List<CommentVo> hotComment = commentService.queryList(param);
        Map<String, Object> commentInfo = new HashMap<>();
        if (null != hotComment && hotComment.size() > 0) {
            UserVo commentUser = userService.queryObject(hotComment.get(0).getUserId());
            commentInfo.put("content", Base64Util.decode(hotComment.get(0).getContent()));
            commentInfo.put("addTime", DateUtils.timeToStr(hotComment.get(0).getAddTime(), DateUtils.DATE_PATTERN));
            commentInfo.put("nickname", commentUser.getNickname());
            commentInfo.put("avatar", commentUser.getAvatar());
            Map<String, Object> paramPicture = new HashMap<>();
            paramPicture.put("commentId", hotComment.get(0).getId());
            List<CommentPictureVo> commentPictureEntities = commentPictureService.queryList(paramPicture);
            commentInfo.put("picList", commentPictureEntities);
        }
        Map<String, Object> comment = new HashMap<>();
        comment.put("count", commentCount);
        comment.put("data", commentInfo);
        //当前用户是否收藏
        Map<String, Object> collectParam = new HashMap<>();
        collectParam.put("userId", getUserId());
        collectParam.put("valueId", id);
        collectParam.put("typeId", 0);
        Integer userHasCollect = collectService.queryTotal(collectParam);
        if (userHasCollect > 0) {
            userHasCollect = 1;
        }
        //记录用户的足迹
        FootprintVo footprintEntity = new FootprintVo();
        footprintEntity.setAddTime(System.currentTimeMillis() / 1000);
        footprintEntity.setGoodsBrief(info.getGoodsBrief());
        footprintEntity.setListPicUrl(info.getListPicUrl());
        footprintEntity.setGoodsId(info.getId());
        footprintEntity.setName(info.getName());
        footprintEntity.setRetailPrice(info.getRetailPrice());
        footprintEntity.setUserId(userId);
        if (null != referrer) {
            footprintEntity.setReferrer(referrer);
        } else {
            footprintEntity.setReferrer(0L);
        }
        footprintService.save(footprintEntity);
        //
        resultObj.put("info", info);
        resultObj.put("gallery", gallery);
        resultObj.put("attribute", attribute);
        resultObj.put("userHasCollect", userHasCollect);
        resultObj.put("issue", issue);
        resultObj.put("comment", comment);
        resultObj.put("brand", brand);
        resultObj.put("specificationList", specificationList);
        resultObj.put("productList", productEntityList);
        // 记录推荐人是否可以领取红包，用户登录时校验
        try {
            // 是否已经有可用的转发红包
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("sendType", 2);
            params.put("unUsed", true);
            List<CouponVo> enabledCouponVos = apiCouponService.queryUserCoupons(params);
            if ((null == enabledCouponVos || enabledCouponVos.size() == 0)
                    && null != referrer && null != userId) {
                // 获取优惠信息提示
                Map<String, Object> couponParam = new HashMap<>();
                couponParam.put("enabled", true);
                Integer[] sendTypes = new Integer[]{2};
                couponParam.put("sendTypes", sendTypes);
                List<CouponVo> couponVos = apiCouponService.queryList(couponParam);
                if (null != couponVos && couponVos.size() > 0) {
                    CouponVo couponVo = couponVos.get(0);
                    Map<String, Object> footprintParam = new HashMap<>();
                    footprintParam.put("goodsId", id);
                    footprintParam.put("referrer", referrer);
                    Integer footprintNum = footprintService.queryTotal(footprintParam);
                    if (null != footprintNum && null != couponVo.getMinTransmitNum()
                            && footprintNum > couponVo.getMinTransmitNum()) {
                        UserCouponVo userCouponVo = new UserCouponVo();
                        userCouponVo.setAddTime(new Date());
                        userCouponVo.setCouponId(couponVo.getId());
                        userCouponVo.setCouponNumber(CharUtil.getRandomString(12));
                        userCouponVo.setUserId(getUserId());
                        apiUserCouponService.save(userCouponVo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toResponseSuccess(resultObj);
    }

    /**
     * 　获取分类下的商品
     */
    @ApiOperation(value = " 获取分类下的商品")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "分类id", paramType = "path", required = true)})
    @IgnoreAuth
    @PostMapping(value = "category")
    public Object category(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        CategoryVo currentCategory = categoryService.queryObject(id);
        //
        CategoryVo parentCategory = categoryService.queryObject(currentCategory.getParentId());
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", currentCategory.getParentId());
        List<CategoryVo> brotherCategory = categoryService.queryList(params);
        //
        resultObj.put("currentCategory", currentCategory);
        resultObj.put("parentCategory", parentCategory);
        resultObj.put("brotherCategory", brotherCategory);
        return toResponseSuccess(resultObj);
    }

    /**
     * 　　获取商品列表
     */
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "分类id", paramType = "path", required = true),
            @ApiImplicitParam(name = "brandId", value = "品牌Id", paramType = "path", required = true),
            @ApiImplicitParam(name = "isNew", value = "新商品", paramType = "path", required = true),
            @ApiImplicitParam(name = "isHot", value = "热卖商品", paramType = "path", required = true)})
    @IgnoreAuth
    @PostMapping(value = "list")
    public Object list(@LoginUser UserVo loginUser, Integer categoryId,
                       Integer brandId, String keyword, Integer isNew, Integer isHot,
                       @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size,
                       String sort, String order) {
        Map<String, Object> params = new HashMap<>();
        params.put("isDelete", 0);
        params.put("isOnSale", 1);
        params.put("brandId", brandId);
        params.put("keyword", keyword);
        params.put("isNew", isNew);
        params.put("isHot", isHot);
        params.put("page", page);
        params.put("limit", size);
        params.put("order", sort);
        params.put("sidx", order);
        //
        if (null != sort && "price".equals(sort)) {
            params.put("sidx", "retail_price");
            params.put("order", order);
        } else {
            params.put("sidx", "id");
            params.put("order", "desc");
        }
        //添加到搜索历史
        if (!StringUtils.isNullOrEmpty(keyword)) {
            SearchHistoryVo searchHistoryVo = new SearchHistoryVo();
            searchHistoryVo.setAddTime(System.currentTimeMillis() / 1000);
            searchHistoryVo.setKeyword(keyword);
            searchHistoryVo.setUserId(null != loginUser ? loginUser.getUserId().toString() : "");
            searchHistoryVo.setFrom("");
            searchHistoryService.save(searchHistoryVo);

        }
        //筛选的分类
        List<CategoryVo> filterCategory = new ArrayList<>();
        CategoryVo rootCategory = new CategoryVo();
        rootCategory.setId(0);
        rootCategory.setName("全部");
        rootCategory.setChecked(false);
        filterCategory.add(rootCategory);
        //
        params.put("fields", "category_id");
        List<GoodsVo> categoryEntityList = goodsService.queryList(params);
        params.remove("fields");
        if (null != categoryEntityList && categoryEntityList.size() > 0) {
            List<Integer> categoryIds = new ArrayList<>();
            for (GoodsVo goodsVo : categoryEntityList) {
                categoryIds.add(goodsVo.getCategoryId());
            }
            //查找二级分类的parentId
            Map<String, Object> categoryParam = new HashMap<>();
            categoryParam.put("ids", categoryIds);
            categoryParam.put("fields", "parent_id");
            List<CategoryVo> parentCategoryList = categoryService.queryList(categoryParam);
            //
            List<Integer> parentIds = new ArrayList<>();
            for (CategoryVo categoryEntity : parentCategoryList) {
                parentIds.add(categoryEntity.getParentId());
            }
            //一级分类
            categoryParam = new HashMap<>();
            categoryParam.put("fields", "id,name");
            categoryParam.put("order", "asc");
            categoryParam.put("sidx", "sort_order");
            categoryParam.put("ids", parentIds);
            List<CategoryVo> parentCategory = categoryService.queryList(categoryParam);
            if (null != parentCategory) {
                filterCategory.addAll(parentCategory);
            }
        }
        //加入分类条件
        if (null != categoryId && categoryId > 0) {
            List<Integer> categoryIds = new ArrayList<>();
            Map<String, Object> categoryParam = new HashMap<>();
            categoryParam.put("parentId", categoryId);
            categoryParam.put("fields", "id");
            List<CategoryVo> childIds = categoryService.queryList(categoryParam);
            for (CategoryVo categoryEntity : childIds) {
                categoryIds.add(categoryEntity.getId());
            }
            categoryIds.add(categoryId);
            params.put("categoryIds", categoryIds);
        }
        //查询列表数据
        params.put("fields", "id, name, list_pic_url, market_price, retail_price, goods_brief");
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<GoodsVo> goodsList = goodsService.queryList(query);
        ApiPageUtils goodsData = new ApiPageUtils(new PageInfo(goodsList));
        //搜索到的商品
        for (CategoryVo categoryEntity : filterCategory) {
        	if (null != categoryId && (categoryEntity.getId() == 0 || categoryId.equals(categoryEntity.getId()))
        	   ||null == categoryId&&null == categoryEntity.getId()) {
                categoryEntity.setChecked(true);
            } else {
                categoryEntity.setChecked(false);
            }
        }
        goodsData.setFilterCategory(filterCategory);
        goodsData.setGoodsList(goodsList);
        return toResponseSuccess(goodsData);
    }

    /**
     * 　　商品列表筛选的分类列表
     */
    @ApiOperation(value = "商品列表筛选的分类列表")
    @IgnoreAuth
    @PostMapping(value = "filter")
    public Object filter(Integer categoryId,
                         String keyword, Integer isNew, Integer isHot) {
        Map<String, Object> params = new HashMap<>();
        params.put("isDelete", 0);
        params.put("isOnSale", 1);
        params.put("categoryId", categoryId);
        params.put("keyword", keyword);
        params.put("isNew", isNew);
        params.put("isHot", isHot);
        if (null != categoryId) {
            Map<String, Object> categoryParams = new HashMap<>();
            categoryParams.put("categoryId", categoryId);
            List<CategoryVo> categoryEntityList = categoryService.queryList(categoryParams);
            List<Integer> categoryIds = new ArrayList<>();
            for (CategoryVo categoryEntity : categoryEntityList) {
                categoryIds.add(categoryEntity.getId());
            }
            params.put("categoryId", categoryIds);
        }
        //筛选的分类
        List<CategoryVo> filterCategory = new ArrayList<>();
        CategoryVo rootCategory = new CategoryVo();
        rootCategory.setId(0);
        rootCategory.setName("全部");
        // 二级分类id
        List<GoodsVo> goodsEntityList = goodsService.queryList(params);
        if (null != goodsEntityList && goodsEntityList.size() > 0) {
            List<Integer> categoryIds = new ArrayList<>();
            for (GoodsVo goodsEntity : goodsEntityList) {
                categoryIds.add(goodsEntity.getCategoryId());
            }
            //查找二级分类的parentId
            Map<String, Object> categoryParam = new HashMap<>();
            categoryParam.put("categoryIds", categoryIds);
            List<CategoryVo> parentCategoryList = categoryService.queryList(categoryParam);
            //
            List<Integer> parentIds = new ArrayList<>();
            for (CategoryVo categoryEntity : parentCategoryList) {
                parentIds.add(categoryEntity.getId());
            }
            //一级分类
            categoryParam.put("categoryIds", parentIds);
            List<CategoryVo> parentCategory = categoryService.queryList(categoryParam);
            if (null != parentCategory) {
                filterCategory.addAll(parentCategory);
            }
        }
        return toResponseSuccess(filterCategory);
    }

    /**
     * 　　新品首发
     */
    @ApiOperation(value = "新品首发")
    @IgnoreAuth
    @PostMapping(value = "new")
    public Object newAction() {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> bannerInfo = new HashMap<>();
        bannerInfo.put("url", "");
        bannerInfo.put("name", "坚持初心，为你寻觅世间好物");
        bannerInfo.put("imgUrl", "https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/1504208321fef4.png");
        resultObj.put("bannerInfo", bannerInfo);
        return toResponseSuccess(resultObj);
    }

    /**
     * 　　人气推荐
     */
    @ApiOperation(value = "人气推荐")
    @IgnoreAuth
    @PostMapping(value = "hot")
    public Object hot() {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> bannerInfo = new HashMap<>();
        bannerInfo.put("url", "");
        bannerInfo.put("name", "大家都在买的严选好物");
        bannerInfo.put("imgUrl", "https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/1504208321fef4.png");
        resultObj.put("bannerInfo", bannerInfo);
        return toResponseSuccess(resultObj);
    }

    /**
     * 　　商品详情页的大家都在看的商品
     */
    @ApiOperation(value = "商品详情页")
    @IgnoreAuth
    @PostMapping(value = "related")
    public Object related(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", id);
        param.put("fields", "related_goods_id");
        List<RelatedGoodsVo> relatedGoodsEntityList = relatedGoodsService.queryList(param);

        List<Integer> relatedGoodsIds = new ArrayList<>();
        for (RelatedGoodsVo relatedGoodsEntity : relatedGoodsEntityList) {
            relatedGoodsIds.add(relatedGoodsEntity.getRelatedGoodsId());
        }

        List<GoodsVo> relatedGoods = new ArrayList<GoodsVo>();

        if (null == relatedGoodsIds || relatedGoods.size() < 1) {
            //查找同分类下的商品
            GoodsVo goodsCategory = goodsService.queryObject(id);
            Map<String, Object> paramRelated = new HashMap<>();
            paramRelated.put("fields", "id, name, list_pic_url, retail_price");
            paramRelated.put("categoryId", goodsCategory.getCategoryId());
            relatedGoods = goodsService.queryList(paramRelated);
        } else {
            Map<String, Object> paramRelated = new HashMap<>();
            paramRelated.put("goodsIds", relatedGoodsIds);
            paramRelated.put("fields", "id, name, list_pic_url, retail_price");
            relatedGoods = goodsService.queryList(paramRelated);
        }
        resultObj.put("goodsList", relatedGoods);
        return toResponseSuccess(resultObj);
    }

    /**
     * 　　在售的商品总数
     */
    @ApiOperation(value = "在售的商品总数")
    @IgnoreAuth
    @PostMapping(value = "count")
    public Object count() {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("isDelete", 0);
        param.put("isOnSale", 1);
        Integer goodsCount = goodsService.queryTotal(param);
        resultObj.put("goodsCount", goodsCount);
        return toResponseSuccess(resultObj);
    }

    /**
     * 　　获取商品列表
     */
    @ApiOperation(value = "获取商品列表")
    @IgnoreAuth
    @PostMapping(value = "productlist")
    public Object productlist(Integer categoryId,
                              Integer isNew, Integer discount,
                              @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size,
                              String sort, String order) {
        Map<String, Object> params = new HashMap<>();
        params.put("isNew", isNew);
        params.put("page", page);
        params.put("limit", size);
        params.put("order", sort);
        params.put("sidx", order);
        //
        if ("price".equals(sort)) {
            params.put("sidx", "retail_price");
            params.put("order", order);
        } else if ("sell".equals(sort)) {
            params.put("sidx", "orderNum");
            params.put("order", order);
        } else {
            params.put("sidx", "id");
            params.put("order", "desc");
        }
        // 0不限 1特价 2团购
        if (null != discount && discount == 1) {
            params.put("isHot", 1);
        } else if (null != discount && discount == 2) {
            params.put("is_group", true);
        }
        //加入分类条件
        if (null != categoryId && categoryId > 0) {
            List<Integer> categoryIds = new ArrayList<>();
            Map<String, Object> categoryParam = new HashMap<>();
            categoryParam.put("parentId", categoryId);
            categoryParam.put("fields", "id");
            List<CategoryVo> childIds = categoryService.queryList(categoryParam);
            for (CategoryVo categoryEntity : childIds) {
                categoryIds.add(categoryEntity.getId());
            }
            categoryIds.add(categoryId);
            params.put("categoryIds", categoryIds);
        }
        //查询列表数据
        Query query = new Query(params);
        List<GoodsVo> goodsList = goodsService.queryCatalogProductList(query);
        int total = goodsService.queryTotal(query);

        // 当前购物车中
        List<CartVo> cartList = new ArrayList<>();
        if (null != getUserId()) {
            //查询列表数据
            Map<String, Object> cartParam = new HashMap<>();
            cartParam.put("userId", getUserId());
            cartList = cartService.queryList(cartParam);
        }
        if (null != cartList && cartList.size() > 0 && null != goodsList && goodsList.size() > 0) {
            for (GoodsVo goodsVo : goodsList) {
                for (CartVo cartVo : cartList) {
                    if (goodsVo.getId().equals(cartVo.getGoodsId())) {
                        goodsVo.setCartNum(cartVo.getNumber());
                    }
                }
            }
        }
        ApiPageUtils goodsData = new ApiPageUtils(goodsList, total, query.getLimit(), query.getPage());
        goodsData.setGoodsList(goodsData.getData());
        return toResponseSuccess(goodsData);
    }
}
