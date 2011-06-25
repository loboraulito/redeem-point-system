package com.integral.system.menu.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.menu.bean.MenuInfo;
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
    
    public List findRootMenu(){
        log.debug("finding Root menu");
        try {
            String queryString = "from MenuInfo as model where model.parentMenuId is null";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find Root menu failed", re);
            throw re;
        }
    }
    
    @Override
    public List findAll() {
        log.debug("finding all menu");
        try {
            String queryString = "from MenuInfo";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all menu failed", re);
            throw re;
        }
    }
    @Override
    public List findMenuByPage(final int start, final int limit) {
        return getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery("from MenuInfo");
                if(start>-1 && limit>0){
                    query.setFirstResult(start);
                    query.setMaxResults(limit);
                }
                return query.list();
            }
        });
    }
    
    /**
     * <p>Discription:[添加或修改菜单信息]</p>
     * @param menu
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void saveOrUpdateMenu(MenuInfo menu){
        log.debug("save or update menu");
        try {
            getHibernateTemplate().saveOrUpdate(menu);
        } catch (RuntimeException re) {
            log.error("save or update menu ", re);
            throw re;
        }
    }
    
    public MenuInfo findById(String menuId){
        log.debug("find by menu id");
        try {
            MenuInfo menuInfo = (MenuInfo)getHibernateTemplate().get(MenuInfo.class, menuId);
            return menuInfo;
        } catch (RuntimeException re) {
            log.error("find by menu id ", re);
            throw re;
        }
    }
}
