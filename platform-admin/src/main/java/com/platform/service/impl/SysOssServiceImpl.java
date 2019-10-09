package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.dao.SysOssDao;
import com.platform.entity.SysOssEntity;
import com.platform.service.SysOssService;
import com.platform.utils.PageUtilsPlus;
import com.platform.utils.QueryPlus;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 李鹏军
 * @email 939961241@qq.com
 * @gitee https://gitee.com/fuyang_lipengjun/platform
 */
@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageUtilsPlus queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "t.create_date");
        params.put("asc", false);
        Page<SysOssEntity> page = new QueryPlus<SysOssEntity>(params).getPage();
        return new PageUtilsPlus(page.setRecords(baseMapper.selectSysOssPage(page, params)));
    }
}
