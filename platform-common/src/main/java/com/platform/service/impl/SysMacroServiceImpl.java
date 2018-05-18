package com.platform.service.impl;

import com.platform.dao.SysMacroDao;
import com.platform.entity.SysMacroEntity;
import com.platform.service.SysMacroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用字典表Service实现类
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-22 11:48:16
 */
@Service("sysMacroService")
public class SysMacroServiceImpl implements SysMacroService {
    @Autowired
    private SysMacroDao sysMacroDao;

    @Override
    public SysMacroEntity queryObject(Long macroId) {
        return sysMacroDao.queryObject(macroId);
    }

    @Override
    public List<SysMacroEntity> queryList(Map<String, Object> map) {
        return sysMacroDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMacroDao.queryTotal(map);
    }

    @Override
    public int save(SysMacroEntity sysMacro) {
        sysMacro.setGmtCreate(new Date());
        return sysMacroDao.save(sysMacro);
    }

    @Override
    public int update(SysMacroEntity sysMacro) {
        sysMacro.setGmtModified(new Date());
        return sysMacroDao.update(sysMacro);
    }

    @Override
    public int delete(Long macroId) {
        return sysMacroDao.delete(macroId);
    }

    @Override
    public int deleteBatch(Long[] macroIds) {
        return sysMacroDao.deleteBatch(macroIds);
    }

    @Override
    public List<SysMacroEntity> queryMacrosByValue(String value) {
        return sysMacroDao.queryMacrosByValue(value);
    }
}
