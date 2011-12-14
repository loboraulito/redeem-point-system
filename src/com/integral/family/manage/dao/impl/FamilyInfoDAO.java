package com.integral.family.manage.dao.impl;

// Generated Dec 13, 2011 1:16:43 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.integral.family.manage.bean.FamilyInfo;
import com.integral.family.manage.dao.IFamilyInfoDAO;

/**
 * Home object for domain model class FamilyInfo.
 * @see com.integral.family.manage.bean.FamilyInfo
 * @author Hibernate Tools
 */
public class FamilyInfoDAO implements IFamilyInfoDAO {

    private static final Log log = LogFactory.getLog(FamilyInfoDAO.class);

    private final SessionFactory sessionFactory = getSessionFactory();

    protected SessionFactory getSessionFactory() {
        try {
            return (SessionFactory) new InitialContext().lookup("SessionFactory");
        }
        catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
        }
    }

    public void persist(FamilyInfo transientInstance) {
        log.debug("persisting FamilyInfo instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(FamilyInfo instance) {
        log.debug("attaching dirty FamilyInfo instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(FamilyInfo instance) {
        log.debug("attaching clean FamilyInfo instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(FamilyInfo persistentInstance) {
        log.debug("deleting FamilyInfo instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public FamilyInfo merge(FamilyInfo detachedInstance) {
        log.debug("merging FamilyInfo instance");
        try {
            FamilyInfo result = (FamilyInfo) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public FamilyInfo findById(java.lang.String id) {
        log.debug("getting FamilyInfo instance with id: " + id);
        try {
            FamilyInfo instance = (FamilyInfo) sessionFactory.getCurrentSession().get("com.FamilyInfo", id);
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

    public List findByExample(FamilyInfo instance) {
        log.debug("finding FamilyInfo instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.FamilyInfo")
                    .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
}
