package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.*;
import com.platform.modules.app.request.ApiCommentPostRequest;
import com.platform.modules.mall.entity.*;
import com.platform.modules.mall.service.MallCommentPictureService;
import com.platform.modules.mall.service.MallCommentService;
import com.platform.modules.mall.service.MallCouponService;
import com.platform.modules.mall.service.MallUserCouponService;
import com.platform.modules.wx.entity.WxUserEntity;
import com.platform.modules.wx.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * <p>
 * 描述: AppController <br>
 */
@Tag(name = "会员评论-AppCommentController")
@RestController
@RequestMapping("/app/comment")
public class AppCommentController extends AppBaseController {
    @Autowired
    private MallCommentService commentService;
    @Autowired
    private WxUserService userService;
    @Autowired
    private MallCommentPictureService commentPictureService;
    @Autowired
    private MallCouponService apiCouponService;
    @Autowired
    private MallUserCouponService apiUserCouponService;

    /**
     * 发表评论
     */
    @Operation(summary = "发表评论")
    @PostMapping("post")
    public RestResponse<Map<String, Object>> post(@LoginUser WxUserEntity loginUser, @RequestBody ApiCommentPostRequest request) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        Integer typeId = request.getTypeId();
        Integer valueId = request.getValueId();
        String content = request.getContent();
        List<String> imagesList = request.getImagesList();
        MallCommentEntity commentEntity = new MallCommentEntity();
        commentEntity.setTypeId(typeId);
        commentEntity.setValueId(valueId);
        commentEntity.setContent(content);
        commentEntity.setStatus(0);
        //
        commentEntity.setAddTime(System.currentTimeMillis() / 1000);
        commentEntity.setUserId(loginUser.getId());
        commentEntity.setContent(Base64Util.encode(commentEntity.getContent()));
        boolean insertId = commentService.save(commentEntity);
        //
        if (insertId && null != imagesList && !imagesList.isEmpty()) {
            int i = 0;
            for (String imgLink : imagesList) {
                i++;
                MallCommentPictureEntity pictureVo = new MallCommentPictureEntity();
                pictureVo.setCommentId(commentEntity.getId());
                pictureVo.setPicUrl(imgLink);
                pictureVo.setSortOrder(i);
                commentPictureService.save(pictureVo);
            }
        }
        // 是否领取优惠券
        if (insertId && typeId == 0) {
            // 当前评价的次数
            Map<String, Object> param = new HashMap<>();
            param.put("valueId", valueId);
            List<MallCommentEntity> commentVos = commentService.queryList(param);
            boolean hasComment = false;
            for (MallCommentEntity commentVo : commentVos) {
                if (commentVo.getUserId().equals(loginUser.getId())) {
                    hasComment = true;
                    break;
                }
            }
            if (!hasComment) {
                Map<String, Object> couponParam = new HashMap<>();
                couponParam.put("sendType", 6);
                MallCouponEntity newCouponConfig = apiCouponService.queryMaxUserEnableCoupon(couponParam);
                if (null != newCouponConfig
                        && newCouponConfig.getMinTransmitNum() >= commentVos.size()) {
                    MallUserCouponEntity userCouponVo = new MallUserCouponEntity();
                    userCouponVo.setAddTime(new Date());
                    userCouponVo.setCouponId(newCouponConfig.getId());
                    userCouponVo.setCouponNumber(CharUtil.getRandomString(12));
                    userCouponVo.setUserId(loginUser.getId());
                    apiUserCouponService.save(userCouponVo);
                    resultObj.put("coupon", newCouponConfig);
                }
            }
        }
        if (insertId) {
            return RestResponse.ok("评论添加成功", resultObj);
        } else {
            return RestResponse.fail("评论保存失败");
        }
    }

    /**
     *
     */
    @Operation(summary = "评论数量")
    @PostMapping("count")
    public RestResponse<Map<String, Object>> count(Integer typeId, Integer valueId) {
        Map<String, Object> resultObj = new HashMap<>();
        //
        Map<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("valueId", valueId);
        Integer allCount = commentService.queryTotal(param);
        Integer hasPicCount = commentService.queryhasPicTotal(param);
        //
        resultObj.put("allCount", allCount);
        resultObj.put("hasPicCount", hasPicCount);
        return RestResponse.ok(resultObj);
    }

    @Operation(summary = "选择评论类型")
    @IgnoreAuth
    @PostMapping("list")
    public RestResponse<Page<MallCommentEntity>> commentList(Integer typeId, Integer valueId, Integer showType,
                                                             @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                             String sort, String order) {

        Map<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("valueId", valueId);
        param.put("page", page);
        param.put("limit", size);
        if (StringUtils.isNullOrEmpty(sort)) {
            param.put("order", "desc");
        } else {
            param.put("order", sort);
        }
        if (StringUtils.isNullOrEmpty(order)) {
            param.put("sidx", "id");
        } else {
            param.put("sidx", order);
        }
        if (null != showType && showType == 1) {
            param.put("hasPic", 1);
        }

        Query<MallCommentEntity> query = new Query<>(param);
        List<MallCommentEntity> list = commentService.queryList(query);
        Page<MallCommentEntity> data = new Page<MallCommentEntity>().setRecords(list);
        data.setTotal(commentService.count());
        data.setCurrent(page);

        //查询列表数据
        for (MallCommentEntity commentItem : data.getRecords()) {
            commentItem.setContent(Base64Util.decode(commentItem.getContent()));
            commentItem.setUserInfo(userService.getOne(new QueryWrapper<WxUserEntity>().eq("ID", commentItem.getUserId())));

            Map<String, Object> paramPicture = new HashMap<>();
            paramPicture.put("commentId", commentItem.getId());
            List<MallCommentPictureEntity> commentPictureEntities = commentPictureService.queryList(paramPicture);
            commentItem.setPicList(commentPictureEntities);
        }

        return RestResponse.ok(data);
    }
}
