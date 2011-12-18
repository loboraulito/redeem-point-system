package com.integral.family.relationship.dao.impl;

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

import com.integral.family.relationship.bean.FamilyRelation;
import com.integral.family.relationship.dao.IFamilyRelationDAO;

/**
 * Home object for domain model class FamilyRelation.
 * @see com.integral.family.relationship.bean.FamilyRelation
 * @author Hibernate Tools
 */
public class FamilyRelationDAO extends HibernateDaoSupport implements IFamilyRelationDAO {

    private static final Log log = LogFactory.getLog(FamilyRelationDAO.class);

    protected void initDao() {
        //do nothing
    }

    public void saveOrUpdate(FamilyRelation instance) {
        log.debug("saving or updating dirty FamilyRelation instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("save or update successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void save(FamilyRelation instance) {
        log.debug("saving dirty FamilyRelation instance");
        try {
            getHibernateTemplate().save(instance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void saveOrUpdateAll(List<FamilyRelation> persistentInstances) {
        log.debug("saveOrUpdateAll role");
        try {
            getHibernateTemplate().saveOrUpdateAll(persistentInstances);
        }
        catch (RuntimeException re) {
            log.error("saveOrUpdateAll failed", re);
            throw re;
        }
    }

    public void delete(FamilyRelation persistentInstance) {
        log.debug("deleting FamilyRelation instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void deleteAll(List<FamilyRelation> persistentInstances) {
        log.debug("deleting FamilyRelation instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstances);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public FamilyRelation merge(FamilyRelation detachedInstance) {
        log.debug("merging FamilyRelation instance");
        try {
            FamilyRelation result = (FamilyRelation) getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<FamilyRelation> findByProperty(String propertyName, Object value) {
        log.debug("finding FamilyRelation instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from FamilyRelation as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public FamilyRelation findById(java.lang.String id) {
        log.debug("getting FamilyRelation instance with id: " + id);
        try {
            FamilyRelation instance = (FamilyRelation) getHibernateTemplate().get(
                    "com.integral.family.relationship.bean.FamilyRelation", id);
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
    public List<FamilyRelation> findByExample(FamilyRelation instance) {
        log.debug("finding FamilyRelation instance by example");
        try {
            List<FamilyRelation> results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    public List<FamilyRelation> findByParams(final String sql, final boolean isHql, final int start, final int limit,
            final Map<String, Object> params) {
        log.debug("finding by FamilyRelation instance by params : " + params);
        return getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = null;
                if ("".equals(sql) || sql == null) {
                    query = session.createQuery("from FamilyRelation");
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
            query = session.createQuery("from FamilyRelation");
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
