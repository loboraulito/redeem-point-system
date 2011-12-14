package com.integral.family.relationship.dao.impl;

// Generated Dec 13, 2011 1:16:43 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.integral.family.relationship.bean.FamilyRelation;
import com.integral.family.relationship.dao.IFamilyRelationDAO;

/**
 * Home object for domain model class FamilyRelation.
 * @see com.integral.family.relationship.bean.FamilyRelation
 * @author Hibernate Tools
 */
public class FamilyRelationDAO implements IFamilyRelationDAO {

    private static final Log log = LogFactory.getLog(FamilyRelationDAO.class);

    private final SessionFactory sessionFactory = getSessionFactory();

    protected SessionFactory getSessionFactory() {
        try {
            return (SessionFactory) new InitialContext().lookup("sessionFactory");
        }
        catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
        }
    }

    public void persist(FamilyRelation transientInstance) {
        log.debug("persisting FamilyRelation instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(FamilyRelation instance) {
        log.debug("attaching dirty FamilyRelation instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(FamilyRelation instance) {
        log.debug("attaching clean FamilyRelation instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(FamilyRelation persistentInstance) {
        log.debug("deleting FamilyRelation instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
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
            FamilyRelation result = (FamilyRelation) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public FamilyRelation findById(java.lang.String id) {
        log.debug("getting FamilyRelation instance with id: " + id);
        try {
            FamilyRelation instance = (FamilyRelation) sessionFactory.getCurrentSession().get("com.FamilyRelation", id);
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

    public List findByExample(FamilyRelation instance) {
        log.debug("finding FamilyRelation instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.FamilyRelation")
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
