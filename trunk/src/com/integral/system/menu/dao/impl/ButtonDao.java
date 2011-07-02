package com.integral.system.menu.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.menu.bean.ButtonInfo;
import com.integral.system.menu.dao.IButtonDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public class ButtonDao extends HibernateDaoSupport  implements IButtonDao {
    private static final Log log = LogFactory.getLog(ButtonDao.class);
    protected void initDao() {
        //do nothing
    }
    
    public List<String> findAllButtonUrl(){
        log.debug("finding all button");
        try {
            String queryString = "select model.buttonUrl from ButtonInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all button failed", re);
            throw re;
        }
    }
    
    public List<ButtonInfo> findAllButton(){
        log.debug("finding all button");
        try {
            String queryString = "From ButtonInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all button failed", re);
            throw re;
        }
    }
    
    public void deleteAll(Collection button){
        log.debug("delete all button");
        try {
            getHibernateTemplate().deleteAll(button);
        } catch (RuntimeException re) {
            log.error("delete all button ", re);
            throw re;
        }
    }
    
    public List findButtonByMenuId(String menuId){
        log.debug("finding all button");
        try {
            String queryString = "From ButtonInfo as model where model.menuId = ?";
            return getHibernateTemplate().find(queryString, menuId);
        } catch (RuntimeException re) {
            log.error("find all button failed", re);
            throw re;
        }
    }

    @Override
    public void saveOrUpdate(ButtonInfo button) {
        log.debug("saveOrUpdate button");
        try {
            getHibernateTemplate().saveOrUpdate(button);
        } catch (RuntimeException re) {
            log.error("saveOrUpdate button ", re);
            throw re;
        }
    }
}
