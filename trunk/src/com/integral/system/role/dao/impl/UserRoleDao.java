package com.integral.system.role.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.role.bean.UserRole;
import com.integral.system.role.dao.IUserRoleDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public class UserRoleDao extends HibernateDaoSupport implements IUserRoleDao {
    private static final Log log = LogFactory.getLog(UserRoleDao.class);
    
    protected void initDao() {
        //do nothing
    }
    
    public List findRoleByUserIdName(String userId){
        log.debug("finding role");
        try {
            String queryString = "select model.roleId from UserRole as model where model.userId=?";
            return getHibernateTemplate().find(queryString,userId);
        } catch (RuntimeException re) {
            log.error("find role failed", re);
            throw re;
        }
    }
    
    /**
     * 新增用户角色信息
     * @param entities
     */
    public void saveOrUpdateAll(Collection entities){
        log.debug("saveOrUpdateAll role");
        try {
            getHibernateTemplate().saveOrUpdateAll(entities);
        } catch (RuntimeException re) {
            log.error("saveOrUpdateAll failed", re);
            throw re;
        }
    }
    
    /**
     * 新增用户角色信息
     * @param entities
     */
    public void saveOrUpdate(UserRole userRole){
        log.debug("saveOrUpdateAll role");
        try {
            getHibernateTemplate().saveOrUpdate(userRole);
        } catch (RuntimeException re) {
            log.error("saveOrUpdateAll failed", re);
            throw re;
        }
    }
}
