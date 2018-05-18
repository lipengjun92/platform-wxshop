package com.platform.service;

import com.platform.dao.ApiGoodsBargainMapper;
import com.platform.entity.GoodsBargainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-28 10:31<br>
 * 描述: ApiGoodsBargainService <br>
 */
@Service
public class ApiGoodsBargainService {
    @Autowired
    private ApiGoodsBargainMapper goodsBargainDao;

    public GoodsBargainVo queryObject(Integer id) {
        return goodsBargainDao.queryObject(id);
    }

    public List<GoodsBargainVo> queryList(Map<String, Object> map) {
        return goodsBargainDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return goodsBargainDao.queryTotal(map);
    }

    public int save(GoodsBargainVo goodsBargain) {
        return goodsBargainDao.save(goodsBargain);
    }

    public int update(GoodsBargainVo goodsBargain) {
        return goodsBargainDao.update(goodsBargain);
    }

    public int delete(Integer id) {
        return goodsBargainDao.delete(id);
    }

    public int deleteBatch(Integer[] ids) {
        return goodsBargainDao.deleteBatch(ids);
    }
}