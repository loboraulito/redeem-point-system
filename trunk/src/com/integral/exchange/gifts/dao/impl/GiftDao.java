package com.integral.exchange.gifts.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
}
