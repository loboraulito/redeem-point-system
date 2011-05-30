package com.integral.system.user.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.user.dao.IUserDao;

public class UserDao extends HibernateDaoSupport implements IUserDao {
    private static final Log log = LogFactory.getLog(UserDao.class);
    protected void initDao() {
        //do nothing
    }
    public List getUserByName(String userName) {
        return findByProperties("userName", userName);
    }

    public List findByProperties(String propertyName, Object value) {
        log.debug("finding UserInfo instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from UserInfo as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
}
