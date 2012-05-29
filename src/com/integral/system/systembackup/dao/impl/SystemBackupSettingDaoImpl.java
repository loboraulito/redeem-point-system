package com.integral.system.systembackup.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.systembackup.bean.SystemBackSettingInfo;
import com.integral.system.systembackup.bean.SystemBackupInfo;
import com.integral.system.systembackup.dao.ISystemBackupSettingDao;

public class SystemBackupSettingDaoImpl extends HibernateDaoSupport implements ISystemBackupSettingDao {
    private static final Log log = LogFactory.getLog(SystemBackupSettingDaoImpl.class);
    protected void initDao() {
        //do nothing
    }
    public void save(SystemBackSettingInfo transientInstance) {
        log.debug("saving SystemBackSettingInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void delete(SystemBackSettingInfo persistentInstance) {
        log.debug("deleting SystemBackSettingInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public void deleteAll(Collection<SystemBackSettingInfo> persistentInstance) {
        log.debug("deleting SystemBackSettingInfo instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SystemBackSettingInfo findById( java.lang.String id) {
        log.debug("getting SystemBackSettingInfo instance with id: " + id);
        try {
            SystemBackSettingInfo instance = (SystemBackSettingInfo) getHibernateTemplate()
                    .get("com.integral.system.systembackup.bean.SystemBackupInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByExample(SystemBackSettingInfo instance) {
        log.debug("finding SystemBackSettingInfo instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdate(SystemBackSettingInfo instance) {
        log.debug("attaching dirty SystemBackSettingInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdateAll(Collection<SystemBackSettingInfo> instance) {
        log.debug("attaching dirty SystemBackSettingInfo instance");
        try {
            getHibernateTemplate().saveOrUpdateAll(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public static SystemBackupSettingDaoImpl getFromApplicationContext(ApplicationContext ctx) {
        return (SystemBackupSettingDaoImpl) ctx.getBean("SystemBackupSettingDao");
    }
}
