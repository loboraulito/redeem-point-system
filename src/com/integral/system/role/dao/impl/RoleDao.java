package com.integral.system.role.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.role.bean.RoleInfo;
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
    
    public List findRoleByName(String roleName){
        log.debug("finding all role");
        try {
            String queryString = "from RoleInfo as model where model.roleName = ?";
            return getHibernateTemplate().find(queryString,roleName);
        } catch (RuntimeException re) {
            log.error("find all role failed", re);
            throw re;
        }
    }
    
    public List findAllRole(){
        log.debug("finding all role");
        try {
            String queryString = "select model.roleId from RoleInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all role failed", re);
            throw re;
        }
    }
    
    public List findRoleByUserIdName(String userId){
        log.debug("finding role");
        try {
            String queryString = "select model.roleId from RoleInfo as model, UserRole as urmodel where model.roleId = urmodel.roleId and urmodel.userId = ?";
            return getHibernateTemplate().find(queryString,userId);
        } catch (RuntimeException re) {
            log.error("find role failed", re);
            throw re;
        }
    }
    
    public List findRoleListByPage(final int start, final int limit) {
        return getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery("from RoleInfo");
                if(start>-1 && limit>0){
                    query.setFirstResult(start);
                    query.setMaxResults(limit);
                }
                return query.list();
            }
        });
    }
    
    public void deleteAll(Collection entities){
        log.debug("delete role");
        try {
            getHibernateTemplate().deleteAll(entities);
        } catch (RuntimeException re) {
            log.error("delete role", re);
            throw re;
        }
    }
    
    public void saveOrUpdate(RoleInfo role){
        log.debug("save or update role");
        try {
            getHibernateTemplate().saveOrUpdate(role);
        } catch (RuntimeException re) {
            log.error("save or update", re);
            throw re;
        }
    }
}
