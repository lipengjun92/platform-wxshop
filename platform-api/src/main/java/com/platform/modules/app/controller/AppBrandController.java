package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.IgnoreAuth;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallBrandEntity;
import com.platform.modules.mall.service.MallBrandService;
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
 * <p>
 * 描述: AppController <br>
 */
@Tag(name = "品牌制造商-AppBrandController")
@RestController
@RequestMapping("/app/brand")
public class AppBrandController extends AppBaseController {
    @Autowired
    private MallBrandService brandService;

    /**
     * 分页获取品牌
     */
    @Operation(summary = "分页获取品牌")
    @IgnoreAuth
    @PostMapping("list")
    public RestResponse<Page<MallBrandEntity>> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("fields", "id, name, floor_price, app_list_pic_url");
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "id");
        params.put("order", "asc");

        Query<MallBrandEntity> query = new Query<>(params);
        List<MallBrandEntity> list = brandService.queryList(query);
        Page<MallBrandEntity> data = new Page<MallBrandEntity>().setRecords(list);
        data.setTotal(brandService.count());
        data.setCurrent(page);

        return RestResponse.ok(data);
    }

    /**
     * 品牌详情
     */
    @Operation(summary = "品牌详情")
    @IgnoreAuth
    @PostMapping("detail")
    public RestResponse<Map<String, Object>> detail(@RequestParam Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        //查询列表数据
        MallBrandEntity entity = brandService.getById(id);
        //
        resultObj.put("brand", entity);
        return RestResponse.ok(resultObj);
    }
}
