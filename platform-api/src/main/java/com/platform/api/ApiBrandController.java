package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.entity.BrandVo;
import com.platform.service.ApiBrandService;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiPageUtils;
import com.platform.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 * 描述: ApiIndexController <br>
 */
@Api(tags = "品牌制造商-ApiBrandController")
@RestController
@RequestMapping("/api/brand")
public class ApiBrandController extends ApiBaseAction {
    @Autowired
    private ApiBrandService brandService;

    /**
     * 分页获取品牌
     */
    @ApiOperation(value = "分页获取品牌")
    @IgnoreAuth
    @PostMapping("list")
    public Object list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("fields", "id, name, floor_price, app_list_pic_url");
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "id");
        params.put("order", "asc");

        Query query = new Query(params);
        List<BrandVo> brandEntityList = brandService.queryList(query);
        int total = brandService.queryTotal(query);
        ApiPageUtils pageUtil = new ApiPageUtils(brandEntityList, total, query.getLimit(), query.getPage());
        //
        return toResponseSuccess(pageUtil);
    }

    /**
     * 品牌详情
     */
    @ApiOperation(value = "品牌详情")
    @IgnoreAuth
    @PostMapping("detail")
    public Object detail(@RequestParam Integer id) {
        Map<String, Object> resultObj = new HashMap<>();
        //查询列表数据
        BrandVo entity = brandService.queryObject(id);
        //
        resultObj.put("brand", entity);
        return toResponseSuccess(resultObj);
    }
}
