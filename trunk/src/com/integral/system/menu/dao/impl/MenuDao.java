package com.integral.system.menu.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.menu.dao.IMenuDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public class MenuDao extends HibernateDaoSupport implements IMenuDao {
    private static final Log log = LogFactory.getLog(MenuDao.class);
    protected void initDao() {
        //do nothing
    }
    public List findAllMenu(){
        log.debug("finding all menu");
        try {
            String queryString = "select model.menuName from MenuInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all menu failed", re);
            throw re;
        }
    }
    
    public List findAllMenuPath(){
        log.debug("finding all menu");
        try {
            String queryString = "select model.pagePath from MenuInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all menu failed", re);
            throw re;
        }
    }
    
    public List findChildMenu(String rootMenuId){
        log.debug("finding Child menu");
        try {
            String queryString = "from MenuInfo as model where model.parentMenuId = ?";
            return getHibernateTemplate().find(queryString,rootMenuId);
        } catch (RuntimeException re) {
            log.error("find Child menu failed", re);
            throw re;
        }
    }
}
