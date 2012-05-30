/**
 * 
 */
package com.integral.system.right.dao.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.right.dao.IAuthorizeDao;

/**
 * @author cdai
 *
 */
public class AuthorizeDao extends HibernateDaoSupport implements IAuthorizeDao {
	private static final Log log = LogFactory.getLog(AuthorizeDao.class);
	protected void initDao() {
        //do nothing
    }
	
	public void deleteAll(Collection entities){
		log.debug("Hibernate delete all entities");
		try {
            getHibernateTemplate().deleteAll(entities);
        } catch (RuntimeException re) {
            log.error("delete all entities error", re);
            throw re;
        }
	}

    @Override
    public void saveOrUpdateAll(Collection entities) {
        log.debug("Hibernate saveOrUpdateAll entities");
        try {
            getHibernateTemplate().saveOrUpdateAll(entities);
        } catch (RuntimeException re) {
            log.error("saveOrUpdateAll entities error", re);
            throw re;
        }
    }
}
