package com.platform.controller;

import com.platform.BaseSpringTestCase;
import com.platform.entity.SysUserEntity;
import com.platform.service.TestSysUserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员测试
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2018-07-09 10:13:43
 */
public class TestSysUserController extends BaseSpringTestCase {
    @Autowired
    TestSysUserService testSysUserService;
    private Logger logger = getLogger();

    @Test
    public void querySysUserList() {
        Map params = new HashMap();
        List<SysUserEntity> list = testSysUserService.queryList(params);
        if (list != null && list.size() != 0) {
            for (SysUserEntity userEntity : list) {
                logger.info("userId：" + userEntity.getUserId() + "；username：" + userEntity.getUsername() + "；mobile：" + userEntity.getMobile());
            }
        }
    }
}
