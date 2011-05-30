package com.integral.system.role.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.role.dao.IRoleDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public class RoleDao extends HibernateDaoSupport implements IRoleDao {
    private static final Log log = LogFactory.getLog(RoleDao.class);
    
    protected void initDao() {
        //do nothing
    }
    
    public List findAllRole(){
        log.debug("finding all role");
        try {
            String queryString = "select model.roleName from RoleInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all role failed", re);
            throw re;
        }
    }
    
    public List findRoleByUserIdName(String userId){
        log.debug("finding role");
        try {
            String queryString = "select model.roleName from RoleInfo as model, UserRole as urmodel where model.roleId = urmodel.roleId and urmodel.userId = ?";
            return getHibernateTemplate().find(queryString,userId);
        } catch (RuntimeException re) {
            log.error("find role failed", re);
            throw re;
        }
    }
}
