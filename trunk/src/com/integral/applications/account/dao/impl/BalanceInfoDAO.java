package com.integral.applications.account.dao.impl;
// default package

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Property;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.applications.account.bean.BalanceInfo;
import com.integral.applications.account.dao.IBalanceInfoDAO;

/**
 	* A data access object (DAO) providing persistence and search support for BalanceInfo entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .BalanceInfo
  * @author MyEclipse Persistence Tools 
 */

public class BalanceInfoDAO extends HibernateDaoSupport implements IBalanceInfoDAO {
    private static final Log log = LogFactory.getLog(BalanceInfoDAO.class);
	//property constants
	public static final String BALANCETYPE = "balancetype";
	public static final String ACCOUNTENTER = "accountenter";
	public static final String ACCOUNTOUT = "accountout";
	public static final String ACCOUNTMARGIN = "accountmargin";
	public static final String BALANCE = "balance";
	public static final String REMARK = "remark";



	protected void initDao() {
		//do nothing
	}
    
    public void save(BalanceInfo transientInstance) {
        log.debug("saving BalanceInfo instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(BalanceInfo persistentInstance) {
        log.debug("deleting BalanceInfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
	
	public void deleteAll(Collection persistentInstance) {
        log.debug("deleting BalanceInfo instance");
        try {
            getHibernateTemplate().deleteAll(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public BalanceInfo findById( java.lang.String id) {
        log.debug("getting BalanceInfo instance with id: " + id);
        try {
            BalanceInfo instance = (BalanceInfo) getHibernateTemplate()
                    .get("com.systemsoft.application.accountbalance.bean.BalanceInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(BalanceInfo instance) {
        log.debug("finding BalanceInfo instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
	public List findByExampleOrderBy(final BalanceInfo instance) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(BalanceInfo.class).add(Example.create(instance)).addOrder(Property.forName("balancemonth").asc()).list();
			}
			
		});
	}
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding BalanceInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from BalanceInfo as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByBalancetype(Object balancetype
	) {
		return findByProperty(BALANCETYPE, balancetype
		);
	}
	
	public List findByAccountenter(Object accountenter
	) {
		return findByProperty(ACCOUNTENTER, accountenter
		);
	}
	
	public List findByAccountout(Object accountout
	) {
		return findByProperty(ACCOUNTOUT, accountout
		);
	}
	
	public List findByAccountmargin(Object accountmargin
	) {
		return findByProperty(ACCOUNTMARGIN, accountmargin
		);
	}
	
	public List findByBalance(Object balance
	) {
		return findByProperty(BALANCE, balance
		);
	}
	
	public List findByRemark(Object remark
	) {
		return findByProperty(REMARK, remark
		);
	}
	

	public List findAll() {
		log.debug("finding all BalanceInfo instances");
		try {
			String queryString = "from BalanceInfo";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public BalanceInfo merge(BalanceInfo detachedInstance) {
        log.debug("merging BalanceInfo instance");
        try {
            BalanceInfo result = (BalanceInfo) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void saveOrUpdate(BalanceInfo instance) {
        log.debug("attaching dirty BalanceInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdateAll(Collection instance) {
        log.debug("attaching dirty BalanceInfo instance");
        try {
            getHibernateTemplate().saveOrUpdateAll(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(BalanceInfo instance) {
        log.debug("attaching clean BalanceInfo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static BalanceInfoDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (BalanceInfoDAO) ctx.getBean("BalanceInfoDAO");
	}
}