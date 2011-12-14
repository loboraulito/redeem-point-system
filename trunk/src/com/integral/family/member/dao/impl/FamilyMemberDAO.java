package com.integral.family.member.dao.impl;

// Generated Dec 13, 2011 1:16:43 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.integral.family.member.bean.FamilyMember;
import com.integral.family.member.dao.IFamilyMemberDAO;

/**
 * Home object for domain model class FamilyMember.
 * @see com.integral.family.member.bean.FamilyMember
 * @author Hibernate Tools
 */
public class FamilyMemberDAO implements IFamilyMemberDAO {

    private static final Log log = LogFactory.getLog(FamilyMemberDAO.class);

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

    public void persist(FamilyMember transientInstance) {
        log.debug("persisting FamilyMember instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(FamilyMember instance) {
        log.debug("attaching dirty FamilyMember instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(FamilyMember instance) {
        log.debug("attaching clean FamilyMember instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(FamilyMember persistentInstance) {
        log.debug("deleting FamilyMember instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
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
            FamilyMember result = (FamilyMember) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public FamilyMember findById(java.lang.String id) {
        log.debug("getting FamilyMember instance with id: " + id);
        try {
            FamilyMember instance = (FamilyMember) sessionFactory.getCurrentSession().get("com.FamilyMember", id);
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

    public List findByExample(FamilyMember instance) {
        log.debug("finding FamilyMember instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.FamilyMember")
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
