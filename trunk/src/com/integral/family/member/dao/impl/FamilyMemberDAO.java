package com.integral.family.member.dao.impl;

// Generated Dec 15, 2011 2:21:35 PM by Hibernate Tools 3.4.0.CR1

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

import com.integral.family.member.bean.FamilyMember;

/**
 * Home object for domain model class FamilyMember.
 * @see com.integral.family.member.bean.FamilyMember
 * @author Hibernate Tools
 */
public class FamilyMemberDAO extends HibernateDaoSupport {

    private static final Log log = LogFactory.getLog(FamilyMemberDAO.class);

    protected void initDao() {
        //do nothing
    }

    public void saveOrUpdate(FamilyMember instance) {
        log.debug("saving or updating dirty FamilyMember instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("save or update successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void saveOrUpdateAll(List<FamilyMember> persistentInstances) {
        log.debug("saveOrUpdateAll role");
        try {
            getHibernateTemplate().saveOrUpdateAll(persistentInstances);
        }
        catch (RuntimeException re) {
            log.error("saveOrUpdateAll failed", re);
            throw re;
        }
    }

    public void delete(FamilyMember persistentInstance) {
        log.debug("deleting FamilyMember instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void deleteAll(List<FamilyMember> persistentInstances) {
        log.debug("deleting FamilyMember instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstances);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public FamilyMember merge(FamilyMember detachedInstance) {
        log.debug("merging FamilyMember instance");
        try {
            FamilyMember result = (FamilyMember) getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<FamilyMember> findByProperty(String propertyName, Object value) {
        log.debug("finding FamilyMember instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from FamilyMember as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public FamilyMember findById(java.lang.String id) {
        log.debug("getting FamilyMember instance with id: " + id);
        try {
            FamilyMember instance = (FamilyMember) getHibernateTemplate().get(
                    "com.integral.family.member.bean.FamilyMember", id);
            if (instance == null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<FamilyMember> findByExample(FamilyMember instance) {
        log.debug("finding FamilyMember instance by example");
        try {
            List<FamilyMember> results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<FamilyMember> findByParams(final String sql, final boolean isHql, final int start, final int limit,
            final Map<String, Object> params) {
        log.debug("finding by FamilyMember instance by params : " + params);
        return getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = null;
                if ("".equals(sql) || sql == null) {
                    query = session.createQuery("from FamilyMember");
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

}
