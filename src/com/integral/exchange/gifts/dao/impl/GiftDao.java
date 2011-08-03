package com.integral.exchange.gifts.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.exchange.gifts.bean.GiftInfo;
import com.integral.exchange.gifts.dao.IGiftDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public class GiftDao extends HibernateDaoSupport implements IGiftDao {
    private static final Log log = LogFactory.getLog(GiftDao.class);
    protected void initDao() {
        //do nothing
    }
    
    public List findAll(){
        log.debug("finding all GiftInfo");
        try {
            String queryString = "from GiftInfo as model";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all GiftInfo failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdate(GiftInfo entity){
        log.debug("saveOrUpdate GiftInfo");
        try {
            getHibernateTemplate().saveOrUpdate(entity);
        } catch (RuntimeException re) {
            log.error("saveOrUpdate GiftInfo failed", re);
            throw re;
        }
    }
    
    public GiftInfo findById(String id){
        log.debug("saveOrUpdate GiftInfo");
        try {
            return (GiftInfo) getHibernateTemplate().get(GiftInfo.class, id);
        } catch (RuntimeException re) {
            log.error("saveOrUpdate GiftInfo failed", re);
            throw re;
        }
    }
    /**
     * <p>Discription:[批量删除礼品信息]</p>
     * @param entities
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(Collection<GiftInfo> entities){
        log.debug("deleteAll GiftInfo");
        try {
            getHibernateTemplate().deleteAll(entities);
        } catch (RuntimeException re) {
            log.error("deleteAll GiftInfo failed", re);
            throw re;
        }
    }
}
