package com.platform.modules.app.controller;

import com.platform.annotation.LoginUser;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.config.RedisTemplateUtil;
import com.platform.modules.app.request.ApiBuyAddRequest;
import com.platform.modules.mall.dto.BuyGoodsRequest;
import com.platform.modules.wx.entity.WxUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lipengjun
 */
@Tag(name = "商品购买-AppBuyController")
@RestController
@RequestMapping("/app/buy")
@RequiredArgsConstructor
public class AppBuyController extends AppBaseController {
    private final RedisTemplateUtil redisTemplateUtil;

    @Operation(summary = "商品添加")
    @PostMapping("/add")
    public RestResponse<String> addBuy(@LoginUser WxUserEntity loginUser, @RequestBody ApiBuyAddRequest request) {
        Integer goodsId = request.getGoodsId();
        Integer productId = request.getProductId();
        Integer number = request.getNumber();
        BuyGoodsRequest goodsVo = new BuyGoodsRequest();
        goodsVo.setGoodsId(goodsId);
        goodsVo.setProductId(productId);
        goodsVo.setNumber(number);
        redisTemplateUtil.set(Constant.MTM_CACHE + "goods" + loginUser.getId(), goodsVo);
        return RestResponse.ok("添加成功");
    }
}
