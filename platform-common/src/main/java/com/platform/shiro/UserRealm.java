package com.platform.shiro;

import com.platform.Global;
import com.platform.dao.SysMenuDao;
import com.platform.dao.SysUserDao;
import com.platform.entity.SysMenuEntity;
import com.platform.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 认证
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年11月10日 上午11:55:49
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        List<String> permsList = null;

        //系统管理员，拥有最高权限
        if (userId == 1) {
            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<String, Object>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        SysUserEntity user = sysUserDao.queryByUserName(username);

        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        // 把当前用户放入到session中
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        session.setAttribute(Global.CURRENT_USER, user);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

}
