package com.platform.shiro;

import com.google.common.collect.Sets;
import com.platform.cache.J2CacheUtils;
import com.platform.utils.Constant;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 集群session管理
 *
 * @author lipengjun
 * @date 2018年07月31日 上午14:50
 */
public class CluterShiroSessionDao extends AbstractSessionDAO {

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        final String key = Constant.SESSION_KEY + sessionId.toString();
        setShiroSession(key, session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        final String key = Constant.SESSION_KEY + sessionId.toString();
        return getShiroSession(key);
    }

    @Override
    public void update(Session session) {
        final String key = Constant.SESSION_KEY + session.getId().toString();
        setShiroSession(key, session);
    }

    @Override
    public void delete(Session session) {
        final String key = Constant.SESSION_KEY + session.getId().toString();

        J2CacheUtils.remove(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Collection<String> keys = J2CacheUtils.keys(Constant.SESSION_KEY + "*");

        Set<Session> sessionSet = Sets.newHashSet();
        for (String key : keys) {
            Session session = getShiroSession(key);
            if (session != null) {
                sessionSet.add(session);
            }
        }
        return sessionSet;
    }

    private Session getShiroSession(String key) {
        return (Session) J2CacheUtils.get(key);
    }

    private void setShiroSession(String key, Session session) {
        J2CacheUtils.put(key, session);
    }
}
