package com.integral.applications.account.dao.impl;

// default package

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.applications.account.bean.BalanceRight;
import com.integral.applications.account.dao.IBalanceRightDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * BalanceRight entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .BalanceRight
 * @author MyEclipse Persistence Tools
 */

public class BalanceRightDAO extends HibernateDaoSupport implements
		IBalanceRightDAO {
	private static final Log log = LogFactory.getLog(BalanceRightDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String USERNAME = "username";
	public static final String ALLOWUSERID = "allowuserid";
	public static final String ALLOWUSERNAME = "allowusername";

	protected void initDao() {
		// do nothing
	}

	public void save(BalanceRight transientInstance) {
		log.debug("saving BalanceRight instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BalanceRight persistentInstance) {
		log.debug("deleting BalanceRight instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void deleteAll(Collection<BalanceRight> persistentInstance) {
		log.debug("deleting BalanceRight instance");
		try {
			getHibernateTemplate().deleteAll(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BalanceRight findById(java.lang.String id) {
		log.debug("getting BalanceRight instance with id: " + id);
		try {
			BalanceRight instance = (BalanceRight) getHibernateTemplate().get(
					"com.systemsoft.application.accountbalance.bean.BalanceRight", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BalanceRight instance) {
		log.debug("finding BalanceRight instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List findBalanceRightByPage(final int start, final int limit, final String[] params) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery("from BalanceRight model where model.userid=? and model.username=?");
				if(params!=null){
					query.setParameter(0, params[0]);
					query.setParameter(1, params[1]);
				}
				if(start>=0){
					query.setFirstResult(start);
				}
				if(limit>=0){
					query.setMaxResults(limit);
				}
				return query.list();
			}
		});
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BalanceRight instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BalanceRight as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByAllowuserid(Object allowuserid) {
		return findByProperty(ALLOWUSERID, allowuserid);
	}

	public List findByAllowusername(Object allowusername) {
		return findByProperty(ALLOWUSERNAME, allowusername);
	}

	public List findAll() {
		log.debug("finding all BalanceRight instances");
		try {
			String queryString = "from BalanceRight";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BalanceRight merge(BalanceRight detachedInstance) {
		log.debug("merging BalanceRight instance");
		try {
			BalanceRight result = (BalanceRight) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(BalanceRight instance) {
		log.debug("attaching dirty BalanceRight instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BalanceRight instance) {
		log.debug("attaching clean BalanceRight instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BalanceRightDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (BalanceRightDAO) ctx.getBean("BalanceRightDAO");
	}
}