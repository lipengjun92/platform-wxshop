package com.platform.api;

import com.platform.annotation.LoginUser;
import com.platform.entity.FootprintVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiFootprintService;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiPageUtils;
import com.platform.utils.DateUtils;
import com.platform.utils.Query;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@RestController
@RequestMapping("/api/footprint")
public class ApiFootprintController extends ApiBaseAction {
    @Autowired
    private ApiFootprintService footprintService;

    /**
     */
    @ApiOperation(value = "删除足迹")
    @ApiImplicitParams({@ApiImplicitParam(name = "footprintId", value = "足迹id", paramType = "path", required = true)})
    @GetMapping("delete")
    public Object delete(@LoginUser UserVo loginUser, Integer footprintId) {
        if (footprintId == null) {
            return toResponsFail("删除出错");
        }
        //删除当天的同一个商品的足迹
        FootprintVo footprintEntity = footprintService.queryObject(footprintId);
        //
        if (loginUser == null || loginUser.getUserId() == null || footprintEntity == null || footprintEntity.getGoods_id() == null) {
            return toResponsFail("删除出错");
        }

        Map param = new HashMap();
        param.put("userId", loginUser.getUserId());
        param.put("goodsId", footprintEntity.getGoods_id());
        footprintService.deleteByParam(param);

        return toResponsMsgSuccess("删除成功");
    }

    /**
     */
    @ApiOperation(value = "获取足迹列表")
    @GetMapping("list")
    public Object list(@LoginUser UserVo loginUser,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map resultObj = new HashMap();

        //查询列表数据
        Map params = new HashMap();
        params.put("user_id", loginUser.getUserId());
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "f.id");
        params.put("maxFoot", true);
        params.put("order", "desc");
        Query query = new Query(params);
        List<FootprintVo> footprintVos = footprintService.queryList(query);
        int total = footprintService.queryTotal(query);
        ApiPageUtils pageUtil = new ApiPageUtils(footprintVos, total, query.getLimit(), query.getPage());
        //
        Map<String, List<FootprintVo>> footPrintMap = new TreeMap<String, List<FootprintVo>>(new Comparator<String>() {
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
            for (FootprintVo footprintVo : footprintVos) {
                String addTime = DateUtils.timeToStr(footprintVo.getAdd_time(), DateUtils.DATE_PATTERN);
                List<FootprintVo> tmpList = footPrintMap.get(addTime);
                if (null == footPrintMap.get(addTime)) {
                    tmpList = new ArrayList();
                }
                tmpList.add(footprintVo);
                footPrintMap.put(addTime, tmpList);
            }
            List<FootprintVo>[] footprintVoList = new List[footPrintMap.size()];
            int i = 0;
            for (Map.Entry<String, List<FootprintVo>> entry : footPrintMap.entrySet()) {
                footprintVoList[i] = entry.getValue();
                i++;
            }
            resultObj.put("count", pageUtil.getCount());
            resultObj.put("totalPages", pageUtil.getTotalPages());
            resultObj.put("numsPerPage", pageUtil.getNumsPerPage());
            resultObj.put("currentPage", pageUtil.getCurrentPage());
            resultObj.put("data", footprintVoList);
        }

        return this.toResponsSuccess(resultObj);
    }

    /**
     */
    @RequestMapping("sharelist")
    public Object sharelist(@LoginUser UserVo loginUser,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map resultObj = new HashMap();

        //查询列表数据
        Map params = new HashMap();
        params.put("sidx", "f.id");
        params.put("order", "desc");
        params.put("referrer", loginUser.getUserId());
        List<FootprintVo> footprintVos = footprintService.shareList(params);
        //
        resultObj.put("data", footprintVos);
        return this.toResponsSuccess(resultObj);
    }
}