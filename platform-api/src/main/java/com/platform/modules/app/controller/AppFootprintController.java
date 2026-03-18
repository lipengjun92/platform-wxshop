package com.platform.modules.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mall.entity.MallBrandEntity;
import com.platform.modules.mall.entity.MallFootprintEntity;
import com.platform.modules.mall.entity.MallOrderGoodsEntity;
import com.platform.modules.mall.service.MallFootprintService;
import com.platform.modules.wx.entity.WxUserEntity;
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
@Tag(name = "会员足迹-AppFootprintController")
@RestController
@RequestMapping("/app/footprint")
public class AppFootprintController extends AppBaseController {
    @Autowired
    private MallFootprintService footprintService;

    /**
     *
     */
    @Operation(summary = "删除足迹")
    @PostMapping("delete")
    public RestResponse<Map<String, Object>> delete(@LoginUser WxUserEntity loginUser, Integer footprintId) {
        if (footprintId == null) {
            return RestResponse.fail("删除出错");
        }
        //删除当天的同一个商品的足迹
        MallFootprintEntity footprintEntity = footprintService.getById(footprintId);
        //
        if (loginUser == null || loginUser.getId() == null || footprintEntity == null || footprintEntity.getGoodsId() == null) {
            return RestResponse.fail("删除出错");
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", loginUser.getId());
        param.put("goodsId", footprintEntity.getGoodsId());
        footprintService.deleteByParam(param);

        return RestResponse.ok("删除成功");
    }

    /**
     *
     */
    @Operation(summary = "获取足迹列表")
    @PostMapping("list")
    public RestResponse<Map<String, Object>> list(@LoginUser WxUserEntity loginUser,
                                                  @RequestParam Map<String, Object> params) {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        //查询列表数据
        List<MallFootprintEntity> footprintVos = footprintService.queryListFootprint(loginUser.getId() + "");

        Page<MallFootprintEntity> page = new Query<MallFootprintEntity>(params).getPage();
        page.setRecords(footprintService.queryListFootprint(loginUser.getId() + ""));

        //
        Map<String, List<MallFootprintEntity>> footPrintMap = new TreeMap<String, List<MallFootprintEntity>>(new Comparator<String>() {
            /*
             * int compare(Object o1, Object o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            @Override
            public int compare(String o1, String o2) {

                //指定排序器按照降序排列
                return o2.compareTo(o1);
            }
        });

        if (null != footprintVos && footprintVos.size() > 0) {
            for (MallFootprintEntity footprintVo : footprintVos) {
                String addTime = DateUtils.timeToStr(footprintVo.getAddTime(), DateUtils.DATE_PATTERN);
                List<MallFootprintEntity> tmpList = footPrintMap.get(addTime);
                if (null == footPrintMap.get(addTime)) {
                    tmpList = new ArrayList<MallFootprintEntity>();
                }
                tmpList.add(footprintVo);
                footPrintMap.put(addTime, tmpList);
            }
            List<List<MallFootprintEntity>> footprintVoList = new ArrayList<List<MallFootprintEntity>>();
            for (Map.Entry<String, List<MallFootprintEntity>> entry : footPrintMap.entrySet()) {
                footprintVoList.add(entry.getValue());
            }
            resultObj.put("count", page.getTotal());
            resultObj.put("totalPages", page.getPages());
            resultObj.put("numsPerPage", page.getSize());
            resultObj.put("currentPage", page.getCurrent());
            resultObj.put("data", footprintVoList);
        }

        return RestResponse.ok(resultObj);
    }


    /**
     *
     */
    @Operation(summary = "分享足迹")
    @PostMapping("sharelist")
    public RestResponse<Map<String, List<MallFootprintEntity>>> sharelist(@LoginUser WxUserEntity loginUser,
                                                                          @RequestParam(value = "page") Integer page,
                                                                          @RequestParam(value = "size") Integer size) {
        Map<String, List<MallFootprintEntity>> resultObj = new HashMap<>();

        //查询列表数据
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sidx", "f.id");
        params.put("order", "desc");
        params.put("referrer", loginUser.getId());
        Query<MallFootprintEntity> query = new Query<>(params);
        List<MallFootprintEntity> footprintVos = footprintService.shareList(query);
        //
        resultObj.put("data", footprintVos);
        return RestResponse.ok(resultObj);
    }
}
