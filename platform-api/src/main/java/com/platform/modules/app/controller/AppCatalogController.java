package com.platform.modules.app.controller;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallCategoryEntity;
import com.platform.modules.mall.service.MallCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 *
 * 描述: AppController <br>
 */
@Tag(name = "商品分类-AppCatalogController")
@RestController
@RequestMapping("/app/catalog")
public class AppCatalogController extends AppBaseController {
    @Autowired
    private MallCategoryService categoryService;

    /**
     * 获取分类栏目数据
     */
    @Operation(summary = "获取分类栏目数据")
    @IgnoreAuth
    @PostMapping("index")
    public RestResponse<Map<String, Object>> index(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("sidx", "sort_order");
        params.put("order", "asc");
        params.put("parentId", "0");
        //查询列表数据
        List<MallCategoryEntity> data = categoryService.queryList(params);
        //
        MallCategoryEntity currentCategory = null;
        if (null != id) {
            currentCategory = categoryService.getById(id);
        }
        if (null == currentCategory && null != data && !data.isEmpty()) {
            currentCategory = data.get(0);
        } else {
            currentCategory = new MallCategoryEntity();
        }

        //获取子分类数据
        if (null != currentCategory && null != currentCategory.getId()) {
            params.put("parentId", currentCategory.getId());
            currentCategory.setSubCategoryList(categoryService.queryList(params));
        }

        resultObj.put("categoryList", data);
        resultObj.put("currentCategory", currentCategory);
        return RestResponse.ok(resultObj);
    }

    /**
     *
     */
    @Operation(summary = "分类目录当前分类数据接口")
    @IgnoreAuth
    @PostMapping("current")
    public RestResponse<Map<String, Object>> current(Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", 0);
        MallCategoryEntity currentCategory = null;
        if (null != id) {
            currentCategory = categoryService.getById(id);
        }
        //获取子分类数据
        if (null != currentCategory && null != currentCategory.getId()) {
            params.put("parentId", currentCategory.getId());
            currentCategory.setSubCategoryList(categoryService.queryList(params));
        }
        resultObj.put("currentCategory", currentCategory);
        return RestResponse.ok(resultObj);
    }
}
