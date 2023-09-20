package com.platform.service.impl;

import com.platform.dao.HelpIssueDao;
import com.platform.entity.HelpIssueEntity;
import com.platform.service.HelpIssueService;
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
@Service("helpIssueService")
public class HelpIssueServiceImpl implements HelpIssueService {
    @Autowired
    private HelpIssueDao helpIssueDao;

    @Override
    public HelpIssueEntity queryObject(Integer id) {
        return helpIssueDao.queryObject(id);
    }

    @Override
    public List<HelpIssueEntity> queryList(Map<String, Object> map) {
        return helpIssueDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return helpIssueDao.queryTotal(map);
    }

    @Override
    public int save(HelpIssueEntity helpIssue) {
        return helpIssueDao.save(helpIssue);
    }

    @Override
    public int update(HelpIssueEntity helpIssue) {
        return helpIssueDao.update(helpIssue);
    }

    @Override
    public int delete(Integer id) {
        return helpIssueDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return helpIssueDao.deleteBatch(ids);
    }
}
