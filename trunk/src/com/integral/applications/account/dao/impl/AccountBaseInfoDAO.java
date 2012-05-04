package com.integral.applications.account.dao.impl;

// default package

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Property;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.applications.account.bean.AccountBaseInfo;
import com.integral.applications.account.dao.IAccountBaseInfoDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccountBaseInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see .AccountBaseInfo
 * @author MyEclipse Persistence Tools
 */

public class AccountBaseInfoDAO extends HibernateDaoSupport implements
		IAccountBaseInfoDAO {
	private static final Log log = LogFactory.getLog(AccountBaseInfoDAO.class);
	// property constants
	public static final String ACCOUNTENTER = "accountenter";
	public static final String ACCOUNTOUT = "accountout";
	public static final String ACCOUNTMARGIN = "accountmargin";
	public static final String REMARK = "remark";

	protected void initDao() {
		// do nothing
	}

	public void save(AccountBaseInfo transientInstance) {
		log.debug("saving AccountBaseInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AccountBaseInfo persistentInstance) {
		log.debug("deleting AccountBaseInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void deleteAll(Collection persistentInstance) {
		log.debug("deleting AccountBaseInfo instance");
		try {
			getHibernateTemplate().deleteAll(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AccountBaseInfo findById(java.lang.String id) {
		log.debug("getting AccountBaseInfo instance with id: " + id);
		try {
			AccountBaseInfo instance = (AccountBaseInfo) getHibernateTemplate()
					.get(
							"com.systemsoft.application.accountbalance.bean.AccountBaseInfo",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List findByExampleOrderBy(final AccountBaseInfo instance){
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(AccountBaseInfo.class).add(Example.create(instance)).addOrder(Property.forName("basedate").asc()).list();
			}
		});
	}

	public List findByExample(AccountBaseInfo instance) {
		log.debug("finding AccountBaseInfo instance by example");
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

	public List queryBySql(final String sql) {
		log.info("query AccountBaseInfo instance with sql: " + sql);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session){
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
	}

	public List queryByProperty(final Map<String, Object> properties) {
		log.info("query AccountBaseInfo instance with hashMap property: "
				+ properties);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer queryString = new StringBuffer(
						"from AccountBaseInfo as model where model.");
				Set keySet = properties.keySet();
				Iterator<String> it = keySet.iterator();
				List params = new ArrayList();
				int limit = 999999;
				int start = 0;
				while (it.hasNext()) {
					String key = it.next();
					Object obj = properties.get(key);
					if ("limit".equals(key)) {
						limit = NumberUtils.toInt(obj.toString());
						continue;
					}
					if ("start".equals(key)) {
						start = NumberUtils.toInt(obj.toString());
						continue;
					}
					if ("begindate".equals(key) && obj != null) {
						params.add(obj);
						queryString.append("basedate")
								.append(" >=? and model.");
						continue;
					}
					if ("enddate".equals(key) && obj != null) {
						params.add(obj);
						queryString.append("basedate")
								.append(" <=? and model.");
						continue;
					}
					if ("accountenterone".equals(key) && obj != null) {
						params.add(obj);
						queryString.append("accountenter").append(
								" >=? and model.");
						continue;
					}
					if ("accountentertwo".equals(key) && obj != null) {
						params.add(obj);
						queryString.append("accountenter").append(
								" <=? and model.");
						continue;
					}
					if ("accountoutone".equals(key) && obj != null) {
						params.add(obj);
						queryString.append("accountout").append(
								" >=? and model.");
						continue;
					}
					if ("accountouttwo".equals(key) && obj != null) {
						params.add(obj);
						queryString.append("accountout").append(
								" <=? and model.");
						continue;
					}
					params.add(obj);
					queryString.append(key).append(" =? and model.");
				}
				String sql = queryString.substring(0, queryString
						.lastIndexOf("and"));
				sql += " order by model.basedate ";
				log.info(sql);
				Query query = session.createQuery(sql);
				// 设置起始页
				query.setFirstResult(start);
				// 设置页面最大显示记录数
				query.setMaxResults(limit);
				// 设置参数
				for (int i = 0, j = params.size(); i < j; i++) {
					query.setParameter(i, params.get(i));
				}
				return query.list();
			}
		});
	}

	public List findByProperty(final Map<String, Object> properties) {
		log.info("finding AccountBaseInfo instance with hashMap property: "
				+ properties);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer queryString = new StringBuffer(
						"from AccountBaseInfo as model where model.");
				Set keySet = properties.keySet();
				Iterator<String> it = keySet.iterator();
				List params = new ArrayList();
				int limit = 999999;
				int start = 0;
				while (it.hasNext()) {
					String key = it.next();
					Object obj = properties.get(key);
					if ("limit".equals(key)) {
						limit = NumberUtils.toInt(obj.toString());
						continue;
					}
					if ("start".equals(key)) {
						start = NumberUtils.toInt(obj.toString());
						continue;
					}
					if ("begindate".equals(key)) {
						params.add(obj);
						queryString.append("basedate")
								.append(" >=? and model.");
						continue;
					}
					if ("enddate".equals(key)) {
						params.add(obj);
						queryString.append("basedate")
								.append(" <=? and model.");
						continue;
					}
					params.add(obj);
					queryString.append(key).append(" =? and model.");
				}
				String sql = queryString.substring(0, queryString
						.lastIndexOf("and"));
				sql += " order by model.basedate ";
				log.info(sql);
				Query query = session.createQuery(sql);
				// 设置起始页
				query.setFirstResult(start);
				// 设置页面最大显示记录数
				query.setMaxResults(limit);
				// 设置参数
				for (int i = 0, j = params.size(); i < j; i++) {
					query.setParameter(i, params.get(i));
				}
				return query.list();
			}
		});
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AccountBaseInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AccountBaseInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccountenter(Object accountenter) {
		return findByProperty(ACCOUNTENTER, accountenter);
	}

	public List findByAccountout(Object accountout) {
		return findByProperty(ACCOUNTOUT, accountout);
	}

	public List findByAccountmargin(Object accountmargin) {
		return findByProperty(ACCOUNTMARGIN, accountmargin);
	}

	public List findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	public List findAll() {
		log.debug("finding all AccountBaseInfo instances");
		try {
			String queryString = "from AccountBaseInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AccountBaseInfo merge(AccountBaseInfo detachedInstance) {
		log.debug("merging AccountBaseInfo instance");
		try {
			AccountBaseInfo result = (AccountBaseInfo) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(AccountBaseInfo instance) {
		log.debug("attaching dirty AccountBaseInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void saveOrUpdateAll(Collection instance) {
		log.debug("attaching dirty AccountBaseInfo instance");
		try {
			getHibernateTemplate().saveOrUpdateAll(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AccountBaseInfo instance) {
		log.debug("attaching clean AccountBaseInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
    public void update(AccountBaseInfo transientInstance) {
	    log.debug("update AccountBaseInfo instance");
        try {
            getHibernateTemplate().update(transientInstance);
            log.debug("update successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
	
	
	public static AccountBaseInfoDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (AccountBaseInfoDAO) ctx.getBean("AccountBaseInfoDAO");
	}

}