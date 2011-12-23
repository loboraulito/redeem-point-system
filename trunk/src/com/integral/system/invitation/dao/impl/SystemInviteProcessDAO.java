package com.integral.system.invitation.dao.impl;

// Generated Dec 23, 2011 2:49:55 PM by Hibernate Tools 3.4.0.CR1

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.invitation.bean.SystemInviteProcess;

/**
 * Home object for domain model class SystemInviteProcess.
 * @see com.integral.system.invitation.bean.SystemInviteProcess
 * @author Hibernate Tools
 */
public class SystemInviteProcessDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(SystemInviteProcessDAO.class);

    protected void initDao() {
        //do nothing
    }

    public void saveOrUpdate(SystemInviteProcess instance) {
        log.info("saving or updating dirty SystemInviteProcess instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.info("save or update successful");
        }
        catch (RuntimeException re) {
            log.error("save or update failed", re);
            throw re;
        }
    }

    public void save(SystemInviteProcess instance) {
        log.info("saving dirty SystemInviteProcess instance");
        try {
            getHibernateTemplate().save(instance);
            log.info("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void saveOrUpdateAll(List<SystemInviteProcess> persistentInstances) {
        log.info("saveOrUpdateAll role");
        try {
            getHibernateTemplate().saveOrUpdateAll(persistentInstances);
        }
        catch (RuntimeException re) {
            log.error("saveOrUpdateAll failed", re);
            throw re;
        }
    }

    public void delete(SystemInviteProcess persistentInstance) {
        log.info("deleting SystemInviteProcess instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.info("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void deleteAll(List<SystemInviteProcess> persistentInstances) {
        log.info("deleting SystemInviteProcess instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstances);
            log.info("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SystemInviteProcess merge(SystemInviteProcess detachedInstance) {
        log.info("merging SystemInviteProcess instance");
        try {
            SystemInviteProcess result = (SystemInviteProcess) getHibernateTemplate().merge(detachedInstance);
            log.info("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<SystemInviteProcess> findByProperty(String propertyName, Object value) {
        log.info("finding SystemInviteProcess instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SystemInviteProcess as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public SystemInviteProcess findById(java.lang.String id) {
        log.info("getting SystemInviteProcess instance with id: " + id);
        try {
            SystemInviteProcess instance = (SystemInviteProcess) getHibernateTemplate().get(
                    "com.integral.system.invitation.bean.SystemInviteProcess", id);
            if (instance == null) {
                log.info("get successful, no instance found");
            }
            else {
                log.info("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<SystemInviteProcess> findByExample(SystemInviteProcess instance) {
        log.info("finding SystemInviteProcess instance by example");
        try {
            List<SystemInviteProcess> results = getHibernateTemplate().findByExample(instance);
            log.info("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<SystemInviteProcess> findByParams(final String sql, final boolean isHql, final int start,
            final int limit, final Map<String, Object> params) {
        log.info("finding by SystemInviteProcess instance by sql : " + sql);
        return getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = null;
                if ("".equals(sql) || sql == null) {
                    query = session.createQuery("from SystemInviteProcess");
                }
                else {
                    if (isHql) {
                        query = session.createQuery(sql);
                    }
                    else {
                        query = session.createSQLQuery(sql);
                    }
                }
                if (start > -1) {
                    query.setFirstResult(start);
                }
                if (limit > -1) {
                    query.setMaxResults(limit);
                }
                if (params != null) {
                    for (String key : params.keySet()) {
                        query.setParameter(key, params.get(key));
                    }
                }
                return query.list();
            }
        });
    }

    public int findCountByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params) {
        log.info("finding count by sql : " + sql);
        Session session = getHibernateTemplate().getSessionFactory().openSession();

        Query query = null;
        if ("".equals(sql) || sql == null) {
            query = session.createQuery("from SystemInviteProcess");
        }
        else {
            if (isHql) {
                query = session.createQuery(sql);
            }
            else {
                query = session.createSQLQuery(sql);
            }
        }
        if (start > -1) {
            query.setFirstResult(start);
        }
        if (limit > -1) {
            query.setMaxResults(limit);
        }
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.list().size();
    }

}
