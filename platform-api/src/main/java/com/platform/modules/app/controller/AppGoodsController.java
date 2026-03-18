package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.*;
import com.platform.modules.mall.entity.*;
import com.platform.modules.mall.service.*;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import com.qiniu.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * <p>
 * 描述: AppController <br>
 */
@Tag(name = "商品信息-AppGoodsController")
@RestController
@RequestMapping("/app/goods")
public class AppGoodsController extends AppBaseController {
    @Autowired
    private MallGoodsService goodsService;
    @Autowired
    private MallGoodsSpecificationService goodsSpecificationService;
    @Autowired
    private MallProductService productService;
    @Autowired
    private MallGoodsGalleryService goodsGalleryService;
    @Autowired
    private MallGoodsIssueService goodsIssueService;
    @Autowired
    private MallAttributeService attributeService;
    @Autowired
    private MallBrandService brandService;
    @Autowired
    private MallCommentService commentService;
    @Autowired
    private WxUserService userService;
    @Autowired
    private MallCommentPictureService commentPictureService;
    @Autowired
    private MallCollectService collectService;
    @Autowired
    private MallFootprintService footprintService;
    @Autowired
    private MallCategoryService categoryService;
    @Autowired
    private MallSearchHistoryService searchHistoryService;
    @Autowired
    private MallRelatedGoodsService relatedGoodsService;
    @Autowired
    private MallCouponService apiCouponService;
    @Autowired
    private MallUserCouponService apiUserCouponService;
    @Autowired
    private MallCartService cartService;

    /**
     *
     */
    @Operation(summary = "商品首页")
    @IgnoreAuth
    @PostMapping("index")
    public RestResponse<Object> index() {
        //
        Map<String, Object> param = new HashMap<>();
        param.put("isDelete", 0);
        param.put("isOnSale", 1);
        List<MallGoodsEntity> goodsList = goodsService.queryList(param);
        //
        return RestResponse.ok(goodsList);
    }

    /**
     * 获取商品规格信息，用于购物车编辑时选择规格
     */
    @Operation(summary = " 获取商品规格信息")
    @IgnoreAuth
    @PostMapping("sku")
    public RestResponse<Object> sku(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", id);
        List<MallGoodsSpecificationEntity> goodsSpecificationEntityList = goodsSpecificationService.queryList(param);
        //
        List<MallProductEntity> productEntityList = productService.queryList(param);
        //
        resultObj.put("specificationList", goodsSpecificationEntityList);
        resultObj.put("productList", productEntityList);
        return RestResponse.ok(resultObj);
    }

    /**
     * 商品详情页数据
     */
    @Operation(summary = " 商品详情页数据")
    @PostMapping("detail")
    public RestResponse<Object> detail(@LoginUser WxUserEntity loginUser, Integer id, Long referrer) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        Long userId = loginUser.getId();
        MallGoodsEntity info = goodsService.getById(id);
        Map<String, Object> param = new HashMap<>();
        param.put("goodsId", id);
        //
        Map<String, Object> specificationParam = new HashMap<>();
        specificationParam.put("fields", "gs.*, s.name");
        specificationParam.put("goodsId", id);
        specificationParam.put("specification", true);
        specificationParam.put("sidx", "s.sort_order");
        specificationParam.put("order", "asc");
        List<MallGoodsSpecificationEntity> goodsSpecificationEntityList = goodsSpecificationService.queryList(specificationParam);

        List<Map> specificationList = new ArrayList<>();
        //按规格名称分组
        for (int i = 0; i < goodsSpecificationEntityList.size(); i++) {
            MallGoodsSpecificationEntity specItem = goodsSpecificationEntityList.get(i);
            //
            List<MallGoodsSpecificationEntity> tempList = null;
            for (int j = 0; j < specificationList.size(); j++) {
                if (specificationList.get(j).get("specificationId").equals(specItem.getSpecificationId())) {
                    tempList = (List<MallGoodsSpecificationEntity>) specificationList.get(j).get("valueList");
                    break;
                }
            }
            //
            if (null == tempList) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("specificationId", specItem.getSpecificationId());
                temp.put("name", specItem.getValue());
                tempList = new ArrayList<>();
                tempList.add(specItem);
                temp.put("valueList", tempList);
                specificationList.add(temp);
            } else {
                for (int j = 0; j < specificationList.size(); j++) {
                    if (specificationList.get(j).get("specificationId").equals(specItem.getSpecificationId())) {
                        tempList = (List<MallGoodsSpecificationEntity>) specificationList.get(j).get("valueList");
                        tempList.add(specItem);
                        break;
                    }
                }
            }
        }
        //
        List<MallProductEntity> productEntityList = productService.queryList(param);
        //
        List<MallGoodsGalleryEntity> gallery = goodsGalleryService.queryList(param);
        Map<String, Object> ngaParam = new HashMap<>();
        ngaParam.put("fields", "nga.value, na.name");
        ngaParam.put("sidx", "nga.id");
        ngaParam.put("order", "asc");
        ngaParam.put("goodsId", id);
        List<MallAttributeEntity> attribute = attributeService.queryList(ngaParam);
        //
        Map<String, Object> issueParam = new HashMap<>();
        List<MallGoodsIssueEntity> issue = goodsIssueService.queryList(issueParam);
        //
        MallBrandEntity brand = brandService.getById(info.getBrandId());
        //
        param = new HashMap<>();
        param.put("valueId", id);
        param.put("typeId", "0");
        Integer commentCount = commentService.queryTotal(param);
        List<MallCommentEntity> hotComment = commentService.queryList(param);
        Map<String, Object> commentInfo = new HashMap<>();
        if (null != hotComment && hotComment.size() > 0) {
            WxUserEntity commentUser = userService.getById(hotComment.get(0).getUserId());
            commentInfo.put("content", Base64Util.decode(hotComment.get(0).getContent()));
            commentInfo.put("addTime", DateUtils.timeToStr(hotComment.get(0).getAddTime(), DateUtils.DATE_PATTERN));
            commentInfo.put("nickname", commentUser.getNickname());
            commentInfo.put("avatar", commentUser.getHeadImgUrl());
            Map<String, Object> paramPicture = new HashMap<>();
            paramPicture.put("commentId", hotComment.get(0).getId());
            List<MallCommentPictureEntity> commentPictureEntities = commentPictureService.queryList(paramPicture);
            commentInfo.put("picList", commentPictureEntities);
        }
        Map<String, Object> comment = new HashMap<>();
        comment.put("count", commentCount);
        comment.put("data", commentInfo);
        //当前用户是否收藏
        long userHasCollect = collectService.count(new QueryWrapper<MallCollectEntity>().eq("USER_ID", userId).eq("VALUE_ID", id).eq("TYPE_ID", 0));
        if (userHasCollect > 0) {
            userHasCollect = 1L;
        }
        //记录用户的足迹
        MallFootprintEntity footprintEntity = new MallFootprintEntity();
        footprintEntity.setAddTime(System.currentTimeMillis() / 1000);
        footprintEntity.setGoodsId(info.getId());
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
            QueryWrapper<MallUserCouponEntity> userCouponQuery = new QueryWrapper<>();
            userCouponQuery.eq("USER_ID", userId).eq("COUPON_STATUS", 1);
            List<MallUserCouponEntity> userCouponList = apiUserCouponService.list(userCouponQuery);
            Set<Integer> couponIdSet = new HashSet<>();
            for (MallUserCouponEntity userCoupon : userCouponList) {
                if (userCoupon.getCouponId() != null) {
                    couponIdSet.add(userCoupon.getCouponId());
                }
            }
            boolean hasSendTypeCoupon = false;
            if (!couponIdSet.isEmpty()) {
                for (MallCouponEntity coupon : apiCouponService.listByIds(couponIdSet)) {
                    if (coupon != null && Integer.valueOf(2).equals(coupon.getSendType())) {
                        hasSendTypeCoupon = true;
                        break;
                    }
                }
            }
            if (!hasSendTypeCoupon && null != referrer && null != userId) {
                // 获取优惠信息提示
                Map<String, Object> couponParam = new HashMap<>();
                couponParam.put("enabled", true);
                Integer[] sendTypes = new Integer[]{2};
                couponParam.put("sendTypes", sendTypes);
                List<MallCouponEntity> couponVos = apiCouponService.queryList(couponParam);
                if (null != couponVos && !couponVos.isEmpty()) {
                    MallCouponEntity couponVo = couponVos.get(0);
                    long footprintNum = footprintService.count(new QueryWrapper<MallFootprintEntity>().eq("goodsId", id).eq("referrer", referrer));
                    if (footprintNum > 0 && null != couponVo.getMinTransmitNum()
                            && footprintNum > couponVo.getMinTransmitNum()) {
                        MallUserCouponEntity userCouponVo = new MallUserCouponEntity();
                        userCouponVo.setAddTime(new Date());
                        userCouponVo.setCouponId(couponVo.getId());
                        userCouponVo.setCouponNumber(CharUtil.getRandomString(12));
                        userCouponVo.setUserId(userId);
                        apiUserCouponService.save(userCouponVo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestResponse.ok(resultObj);
    }

    /**
     * 　获取分类下的商品
     */
    @Operation(summary = " 获取分类下的商品")
    @IgnoreAuth
    @PostMapping("category")
    public RestResponse<Object> category(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        MallCategoryEntity currentCategory = categoryService.getById(id);
        //
        MallCategoryEntity parentCategory = categoryService.getById(currentCategory.getParentId());
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", currentCategory.getParentId());
        List<MallCategoryEntity> brotherCategory = categoryService.queryList(params);
        //
        resultObj.put("currentCategory", currentCategory);
        resultObj.put("parentCategory", parentCategory);
        resultObj.put("brotherCategory", brotherCategory);
        return RestResponse.ok(resultObj);
    }

    /**
     * 　　获取商品列表
     */
    @Operation(summary = "获取商品列表")
    @IgnoreAuth
    @PostMapping("list")
    public RestResponse<Object> list(@LoginUser WxUserEntity loginUser, Integer categoryId,
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
            MallSearchHistoryEntity searchHistoryVo = new MallSearchHistoryEntity();
            searchHistoryVo.setAddTime(System.currentTimeMillis() / 1000);
            searchHistoryVo.setKeyword(keyword);
            searchHistoryVo.setUserId(null != loginUser ? loginUser.getId().toString() : "");
            searchHistoryVo.setFrom("");
            searchHistoryService.save(searchHistoryVo);

        }
        //筛选的分类
        List<MallCategoryEntity> filterCategory = new ArrayList<>();
        MallCategoryEntity rootCategory = new MallCategoryEntity();
        rootCategory.setId(0);
        rootCategory.setName("全部");
        rootCategory.setChecked(false);
        filterCategory.add(rootCategory);
        //
        params.put("fields", "category_id");
        Query<MallGoodsEntity> query = new Query<>(params);
        List<MallGoodsEntity> categoryEntityList = goodsService.queryList(query);
        params.remove("fields");
        if (null != categoryEntityList && !categoryEntityList.isEmpty()) {
            List<Integer> categoryIds = new ArrayList<>();
            for (MallGoodsEntity goodsVo : categoryEntityList) {
                categoryIds.add(goodsVo.getCategoryId());
            }
            //查找二级分类的parentId
            Map<String, Object> categoryParam = new HashMap<>();
            categoryParam.put("ids", categoryIds);
            categoryParam.put("fields", "parent_id");
            List<MallCategoryEntity> parentCategoryList = categoryService.queryList(categoryParam);
            //
            List<Integer> parentIds = new ArrayList<>();
            for (MallCategoryEntity categoryEntity : parentCategoryList) {
                parentIds.add(categoryEntity.getParentId());
            }
            //一级分类
            categoryParam = new HashMap<>();
            categoryParam.put("fields", "id,name");
            categoryParam.put("order", "asc");
            categoryParam.put("sidx", "sort_order");
            categoryParam.put("ids", parentIds);
            List<MallCategoryEntity> parentCategory = categoryService.queryList(categoryParam);
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
            List<MallCategoryEntity> childIds = categoryService.queryList(categoryParam);
            for (MallCategoryEntity categoryEntity : childIds) {
                categoryIds.add(categoryEntity.getId());
            }
            categoryIds.add(categoryId);
            params.put("categoryIds", categoryIds);
        }
        //查询列表数据
        params.put("fields", "id, name, list_pic_url, market_price, retail_price, goods_brief");

        query = new Query<>(params);
        List<MallGoodsEntity> list = goodsService.queryList(query);
        Page<MallGoodsEntity> goodsList = new Page<MallGoodsEntity>().setRecords(list);
        goodsList.setCurrent(page);
        goodsList.setTotal(goodsService.queryTotal(query));

        //搜索到的商品
        for (MallCategoryEntity categoryEntity : filterCategory) {
            if (null != categoryId && (categoryEntity.getId() == 0 || categoryId.equals(categoryEntity.getId()))
                    || null == categoryId && null == categoryEntity.getId()) {
                categoryEntity.setChecked(true);
            } else {
                categoryEntity.setChecked(false);
            }
        }
        Map<String, Object> resultObj = new HashMap<>();
        resultObj.put("filterCategory", filterCategory);
        resultObj.put("goodsList", goodsList);

        return RestResponse.ok(resultObj);
    }

    /**
     * 　　商品列表筛选的分类列表
     */
    @Operation(summary = "商品列表筛选的分类列表")
    @IgnoreAuth
    @PostMapping("filter")
    public RestResponse<Object> filter(Integer categoryId,
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
            List<MallCategoryEntity> categoryEntityList = categoryService.queryList(categoryParams);
            List<Integer> categoryIds = new ArrayList<>();
            for (MallCategoryEntity categoryEntity : categoryEntityList) {
                categoryIds.add(categoryEntity.getId());
            }
            params.put("categoryId", categoryIds);
        }
        //筛选的分类
        List<MallCategoryEntity> filterCategory = new ArrayList<>();
        MallCategoryEntity rootCategory = new MallCategoryEntity();
        rootCategory.setId(0);
        rootCategory.setName("全部");
        // 二级分类id
        List<MallGoodsEntity> goodsEntityList = goodsService.queryList(params);
        if (null != goodsEntityList && goodsEntityList.size() > 0) {
            List<Integer> categoryIds = new ArrayList<>();
            for (MallGoodsEntity goodsEntity : goodsEntityList) {
                categoryIds.add(goodsEntity.getCategoryId());
            }
            //查找二级分类的parentId
            Map<String, Object> categoryParam = new HashMap<>();
            categoryParam.put("categoryIds", categoryIds);
            List<MallCategoryEntity> parentCategoryList = categoryService.queryList(categoryParam);
            //
            List<Integer> parentIds = new ArrayList<>();
            for (MallCategoryEntity categoryEntity : parentCategoryList) {
                parentIds.add(categoryEntity.getId());
            }
            //一级分类
            categoryParam.put("categoryIds", parentIds);
            List<MallCategoryEntity> parentCategory = categoryService.queryList(categoryParam);
            if (null != parentCategory) {
                filterCategory.addAll(parentCategory);
            }
        }
        return RestResponse.ok(filterCategory);
    }

    /**
     * 　　新品首发
     */
    @Operation(summary = "新品首发")
    @IgnoreAuth
    @PostMapping("new")
    public RestResponse<Object> newAction() {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> bannerInfo = new HashMap<>();
        bannerInfo.put("url", "");
        bannerInfo.put("name", "坚持初心，为你寻觅世间好物");
        bannerInfo.put("imgUrl", "https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/1504208321fef4.png");
        resultObj.put("bannerInfo", bannerInfo);
        return RestResponse.ok(resultObj);
    }

    /**
     * 　　人气推荐
     */
    @Operation(summary = "人气推荐")
    @IgnoreAuth
    @PostMapping("hot")
    public RestResponse<Object> hot() {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> bannerInfo = new HashMap<>();
        bannerInfo.put("url", "");
        bannerInfo.put("name", "大家都在买的严选好物");
        bannerInfo.put("imgUrl", "https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/1504208321fef4.png");
        resultObj.put("bannerInfo", bannerInfo);
        return RestResponse.ok(resultObj);
    }

    /**
     * 　　商品详情页的大家都在看的商品
     */
    @Operation(summary = "商品详情页")
    @IgnoreAuth
    @PostMapping("related")
    public RestResponse<Object> related(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("GOODS_ID", id);
        List<MallRelatedGoodsEntity> relatedGoodsEntityList = relatedGoodsService.listByMap(param);

        List<Integer> relatedGoodsIds = new ArrayList<>();
        for (MallRelatedGoodsEntity relatedGoodsEntity : relatedGoodsEntityList) {
            relatedGoodsIds.add(relatedGoodsEntity.getRelatedGoodsId());
        }

        List<MallGoodsEntity> relatedGoods = new ArrayList<MallGoodsEntity>();

        if (null == relatedGoodsIds || relatedGoods.size() < 1) {
            //查找同分类下的商品
            MallGoodsEntity goodsCategory = goodsService.getById(id);
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
        return RestResponse.ok(resultObj);
    }

    /**
     * 　　在售的商品总数
     */
    @Operation(summary = "在售的商品总数")
    @IgnoreAuth
    @PostMapping("count")
    public RestResponse<Object> count() {
        Map<String, Object> resultObj = new HashMap<>();
        long goodsCount = goodsService.count(new QueryWrapper<MallGoodsEntity>().eq("is_on_sale", 1));
        resultObj.put("goodsCount", goodsCount);
        return RestResponse.ok(resultObj);
    }

    /**
     * 　　获取商品列表
     */
    @Operation(summary = "获取商品列表")
    @IgnoreAuth
    @PostMapping("productlist")
    public RestResponse<Object> productlist(Integer categoryId,
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
            List<MallCategoryEntity> childIds = categoryService.queryList(categoryParam);
            for (MallCategoryEntity categoryEntity : childIds) {
                categoryIds.add(categoryEntity.getId());
            }
            categoryIds.add(categoryId);
            params.put("categoryIds", categoryIds);
        }
        //查询列表数据

        Query<MallGoodsEntity> query = new Query<>(params);
        List<MallGoodsEntity> list = goodsService.queryCatalogProductList(query);
        Page<MallGoodsEntity> data = new Page<MallGoodsEntity>().setRecords(list);
        data.setTotal(goodsService.count());
        data.setCurrent(page);

        Long userId = getUserId();

        // 当前购物车中
        List<MallCartEntity> cartList = new ArrayList<>();
        if (null != userId) {
            //查询列表数据
            Map<String, Object> cartParam = new HashMap<>();
            cartParam.put("userId", userId);
            cartList = cartService.queryList(cartParam);
        }
        if (null != cartList && cartList.size() > 0 && null != list && list.size() > 0) {
            for (MallGoodsEntity goodsVo : list) {
                for (MallCartEntity cartVo : cartList) {
                    if (goodsVo.getId().equals(cartVo.getGoodsId())) {
                        goodsVo.setCartNum(cartVo.getNumber());
                    }
                }
            }
        }
        return RestResponse.ok(data);
    }
}
