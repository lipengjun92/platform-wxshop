package com.platform.service.impl;

import com.platform.dao.HelpTypeDao;
import com.platform.entity.HelpTypeEntity;
import com.platform.service.HelpTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-11-07 10:09:54
 */
@Service("helpTypeService")
public class HelpTypeServiceImpl implements HelpTypeService {
    @Autowired
    private HelpTypeDao helpTypeDao;

    @Override
    public HelpTypeEntity queryObject(Integer id) {
        return helpTypeDao.queryObject(id);
    }

    @Override
    public List<HelpTypeEntity> queryList(Map<String, Object> map) {
        return helpTypeDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return helpTypeDao.queryTotal(map);
    }

    @Override
    public int save(HelpTypeEntity helpType) {
        return helpTypeDao.save(helpType);
    }

    @Override
    public int update(HelpTypeEntity helpType) {
        return helpTypeDao.update(helpType);
    }

    @Override
    public int delete(Integer id) {
        return helpTypeDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return helpTypeDao.deleteBatch(ids);
    }
}
