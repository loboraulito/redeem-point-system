package com.integral.system.user.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
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
    
    public List findUserByPage(final int start, final int limit) {
        return getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery("from UserInfo");
                if(start>-1 && limit>0){
                    query.setFirstResult(start);
                    query.setMaxResults(limit);
                }
                return query.list();
            }
        });
    }
}
