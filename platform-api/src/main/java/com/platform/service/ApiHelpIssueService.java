package com.platform.service;

import com.platform.dao.ApiHelpIssueMapper;
import com.platform.entity.HelpIssueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-11-07 11:04:20
 */
@Service
public class ApiHelpIssueService {
    @Autowired
    private ApiHelpIssueMapper helpIssueDao;

    public HelpIssueVo queryObject(Integer id) {
        return helpIssueDao.queryObject(id);
    }

    public List<HelpIssueVo> queryList(Map<String, Object> map) {
        return helpIssueDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return helpIssueDao.queryTotal(map);
    }

    public int save(HelpIssueVo helpIssue) {
        return helpIssueDao.save(helpIssue);
    }

    public int update(HelpIssueVo helpIssue) {
        return helpIssueDao.update(helpIssue);
    }

    public int delete(Integer id) {
        return helpIssueDao.delete(id);
    }

    public int deleteBatch(Integer[] ids) {
        return helpIssueDao.deleteBatch(ids);
    }
}
