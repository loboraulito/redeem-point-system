package com.integral.system.systembackup.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.applications.account.bean.BalanceInfo;
import com.integral.applications.account.dao.impl.BalanceInfoDAO;
import com.integral.system.systembackup.bean.SystemBackupInfo;
import com.integral.system.systembackup.dao.ISystemBackupDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class SystemBackupDaoImpl extends HibernateDaoSupport implements ISystemBackupDao {
    private static final Log log = LogFactory.getLog(SystemBackupDaoImpl.class);
    protected void initDao() {
        //do nothing
    }
    public void save(SystemBackupInfo transientInstance) {
        log.debug("saving SystemBackupInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void delete(SystemBackupInfo persistentInstance) {
        log.debug("deleting SystemBackupInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public void deleteAll(Collection<SystemBackupInfo> persistentInstance) {
        log.debug("deleting SystemBackupInfo instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SystemBackupInfo findById( java.lang.String id) {
        log.debug("getting SystemBackupInfo instance with id: " + id);
        try {
            SystemBackupInfo instance = (SystemBackupInfo) getHibernateTemplate()
                    .get("com.integral.system.systembackup.bean.SystemBackupInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByExample(SystemBackupInfo instance) {
        log.debug("finding SystemBackupInfo instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdate(SystemBackupInfo instance) {
        log.debug("attaching dirty SystemBackupInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdateAll(Collection<SystemBackupInfo> instance) {
        log.debug("attaching dirty SystemBackupInfo instance");
        try {
            getHibernateTemplate().saveOrUpdateAll(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public static SystemBackupDaoImpl getFromApplicationContext(ApplicationContext ctx) {
        return (SystemBackupDaoImpl) ctx.getBean("SystemBackupDaoImpl");
    }
}
