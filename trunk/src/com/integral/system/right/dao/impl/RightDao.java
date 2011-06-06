package com.integral.system.right.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.right.dao.IRightDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public class RightDao extends HibernateDaoSupport implements IRightDao {
    private static final Log log = LogFactory.getLog(RightDao.class);
    protected void initDao() {
        //do nothing
    }
    
    public List getButtonByRight(String menuId,String roleId){
        log.debug("finding all right button");
        try {
            String queryString = "from ButtonInfo as model, RightInfo modelr WHERE modelr.buttonId = model.buttonId AND modelr.roleId = ? AND modelr.menuId = ?";
            return getHibernateTemplate().find(queryString,roleId,menuId);
        } catch (RuntimeException re) {
            log.error("find all right button failed", re);
            throw re;
        }
    }
}
