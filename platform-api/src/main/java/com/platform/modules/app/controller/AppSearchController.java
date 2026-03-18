package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallKeywordsEntity;
import com.platform.modules.mall.entity.MallOrderEntity;
import com.platform.modules.mall.entity.MallSearchHistoryEntity;
import com.platform.modules.mall.service.MallKeywordsService;
import com.platform.modules.mall.service.MallSearchHistoryService;
import com.platform.modules.wx.entity.WxUserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API登录授权
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-03-23 15:31
 */
@Tag(name = "会员搜索-AppSearchController")
@RestController
@RequestMapping("/app/search")
public class AppSearchController extends AppBaseController {
    @Autowired
    private MallKeywordsService keywordsService;
    @Autowired
    private MallSearchHistoryService searchHistoryService;

    /**
     * index
     */
    @Operation(summary = "搜索商品列表")
    @PostMapping("index")
    public RestResponse<Object> index(@LoginUser WxUserEntity loginUser) {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("isDefault", "1");
        param.put("page", 1);
        param.put("limit", 1);
        param.put("sidx", "id");
        param.put("order", "asc");
        Query<MallKeywordsEntity> query = new Query<>(param);
        List<MallKeywordsEntity> keywordsEntityList = keywordsService.queryList(query);
        //取出输入框默认的关键词
        MallKeywordsEntity defaultKeyword = null != keywordsEntityList && !keywordsEntityList.isEmpty() ? keywordsEntityList.get(0) : null;
        //取出热闹关键词
        param = new HashMap<>();
        param.put("fields", "distinct keyword,is_hot");
        param.put("page", 1);
        param.put("limit", 10);
        param.put("sidx", "id");
        param.put("order", "asc");
        query = new Query<>(param);
        List<MallKeywordsEntity> hotKeywordList = keywordsService.queryList(query);
        //
        param = new HashMap<>();
        param.put("userId", loginUser.getId());
        param.put("fields", "distinct keyword");
        param.put("page", 1);
        param.put("limit", 10);
        param.put("sidx", "id");
        param.put("order", "asc");
        query = new Query<>(param);
        List<MallSearchHistoryEntity> historyVoList = searchHistoryService.queryList(query);
        String[] historyKeywordList = new String[historyVoList.size()];
        if (null != historyVoList) {
            int i = 0;
            for (MallSearchHistoryEntity historyVo : historyVoList) {
                historyKeywordList[i] = historyVo.getKeyword();
                i++;
            }
        }
        //
        resultObj.put("defaultKeyword", defaultKeyword);
        resultObj.put("historyKeywordList", historyKeywordList);
        resultObj.put("hotKeywordList", hotKeywordList);
        return RestResponse.ok(resultObj);
    }

    /**
     * 　　helper
     */
    @Operation(summary = "搜索商品")
    @IgnoreAuth
    @PostMapping("helper")
    public RestResponse<Object> helper(@LoginUser WxUserEntity loginUser, String keyword) {
        Map<String, Object> param = new HashMap<>();
        param.put("fields", "distinct keyword");
        param.put("keyword", keyword);
        param.put("limit", 10);
        param.put("offset", 0);
        Query<MallKeywordsEntity> query = new Query<>(param);
        List<MallKeywordsEntity> keywords = keywordsService.queryList(query);
        String[] keys = new String[keywords.size()];
        if (null != keywords) {
            int i = 0;
            for (MallKeywordsEntity keywordsVo : keywords) {
                keys[i] = keywordsVo.getKeyword();
                i++;
            }
        }
        //
        return RestResponse.ok(keys);
    }

    /**
     * 　　clearhistory
     */
    @Operation(summary = "清空搜索记录")
    @PostMapping("clearhistory")
    public RestResponse<Object> clearhistory(@LoginUser WxUserEntity loginUser) {
        searchHistoryService.getBaseMapper().delete(new UpdateWrapper<MallSearchHistoryEntity>().eq("user_id", loginUser.getId()));
        //
        return RestResponse.ok("");
    }
}
