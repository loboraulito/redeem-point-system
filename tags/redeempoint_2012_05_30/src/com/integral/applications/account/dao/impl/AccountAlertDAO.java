package com.integral.applications.account.dao.impl;

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
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.applications.account.bean.AccountAlert;
import com.integral.applications.account.dao.IAccountAlertDAO;

public class AccountAlertDAO extends HibernateDaoSupport implements
        IAccountAlertDAO {
    private static final Log log = LogFactory.getLog(AccountAlertDAO.class);
    // property constants
    public static final String ALERTTYPE = "alerttype";
    public static final String USERID = "userid";
    public static final String USERNAME = "username";
    /** 查询在指定时间区域内是否存在已经定义的报警点信息 * */
    private static final String SHOWALERTPIROD = "from AccountAlert sm "
            + "where (sm.begindate<=? and sm.enddate>=? or sm.begindate<=? and sm.enddate>=? or sm.begindate>=? and sm.enddate<=?) "
            + "and sm.userid=? and sm.username=? and sm.alerttype=? ";
    /** 格式化日期之后的查询报警点信息 **/
    private static final String SHOWALERTPIRODO = "from AccountAlert sm "
            + "where (sm.begindate<=DATE_FORMAT(?,'%Y-%m-%d') and sm.enddate>=DATE_FORMAT(?,'%Y-%m-%d') or sm.begindate<=DATE_FORMAT(?,'%Y-%m-%d') and sm.enddate>=DATE_FORMAT(?,'%Y-%m-%d') or sm.begindate>=DATE_FORMAT(?,'%Y-%m-%d') and sm.enddate<=DATE_FORMAT(?,'%Y-%m-%d')) "
            + "and sm.userid=? and sm.username=? and sm.alerttype=? ";

    protected void initDao() {
        // do nothing
    }

    public void save(AccountAlert transientInstance) {
        log.debug("saving AccountAlert instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(AccountAlert transientInstance) {
        log.debug("saving AccountAlert instance");
        try {
            getHibernateTemplate().delete(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void deleteAll(Collection transientInstance) {
        log.debug("saving AccountAlert instance");
        try {
            getHibernateTemplate().deleteAll(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public AccountAlert findById(String id) {
        log.debug("getting AccountAlert instance with id: " + id);
        try {
            AccountAlert instance = (AccountAlert) getHibernateTemplate()
                    .get(
                            "com.systemsoft.application.accountbalance.bean.AccountAlert",
                            id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(AccountAlert instance) {
        log.debug("finding AccountAlert instance by example");
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

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding AccountAlert instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from AccountAlert as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    
    public List findByProperty(final Map<String,Object> properties) {
        log.info("finding AccountAlert instance with hashMap property: "
                + properties);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer queryString = new StringBuffer("from AccountAlert as model where model.");
                Set keySet = properties.keySet();
                Iterator<String> it = keySet.iterator();
                List params = new ArrayList();
                int limit = 999999;
                int start = 0;
                while (it.hasNext()) {
                    String key = it.next();
                    Object obj = properties.get(key);
                    if("limit".equals(key)){
                        limit = NumberUtils.toInt(obj.toString());
                        continue;
                    }
                    if("start".equals(key)){
                        start = NumberUtils.toInt(obj.toString());
                        continue;
                    }
                    params.add(obj);
                    queryString.append(key).append(" =? and model.");
                }
                String sql = queryString.substring(0, queryString.lastIndexOf("and"));
                log.info(sql);
                Query query = session.createQuery(sql);
                //设置起始页
                query.setFirstResult(start);
                //设置页面最大显示记录数
                query.setMaxResults(limit);
                //设置参数
                for(int i=0,j=params.size();i<j;i++){
                    query.setParameter(i, params.get(i));
                }
                return query.list();
            }
        });
    }

    public List findByAlerttype(Object accountenter) {
        return findByProperty(ALERTTYPE, accountenter);
    }

    public List findByUserid(Object accountenter) {
        return findByProperty(USERID, accountenter);
    }

    public List findByUsername(Object accountenter) {
        return findByProperty(USERNAME, accountenter);
    }

    public List findAll() {
        log.debug("finding all AccountAlert instances");
        try {
            String queryString = "from AccountAlert";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByCustom(final Object[] params) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = null;
                if ("1".equals(params[5])) {
                    query = session.createQuery(SHOWALERTPIROD);
                } else {
                    StringBuffer sb = new StringBuffer(SHOWALERTPIROD);
                    sb.append(" and sm.alertid!=?");
                    query = session.createQuery(sb.toString());
                    query.setParameter(9, params[6]);
                }

                query.setParameter(0, params[0]);
                query.setParameter(1, params[0]);
                query.setParameter(2, params[1]);
                query.setParameter(3, params[1]);
                query.setParameter(4, params[0]);
                query.setParameter(5, params[1]);
                query.setParameter(6, params[2]);
                query.setParameter(7, params[3]);
                query.setParameter(8, params[4]);
                return query.list();
            }

        });
    }
    public List findByCustom(final String sql, final Object[] params) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(sql);
                query.setParameter(0, params[0]);
                query.setParameter(1, params[0]);
                query.setParameter(2, params[1]);
                query.setParameter(3, params[2]);
                query.setParameter(4, params[3]);
                return query.list();
            }
            
        });
    }
    public AccountAlert merge(AccountAlert detachedInstance) {
        log.debug("merging AccountAlert instance");
        try {
            AccountAlert result = (AccountAlert) getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void saveOrUpdate(AccountAlert instance) {
        log.debug("attaching dirty AccountAlert instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void saveOrUpdateAll(Collection instance) {
        log.debug("attaching dirty AccountAlert instance");
        try {
            getHibernateTemplate().saveOrUpdateAll(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(AccountAlert instance) {
        log.debug("attaching clean AccountAlert instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountAlert> findInstanceList(final String sqlOrHql, final boolean isSql, 
            final Map<String, Object> paramMap, final int start, final int limit) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = null;
                if(isSql){
                    query = session.createSQLQuery(sqlOrHql);
                }else{
                    query = session.createQuery(sqlOrHql);
                }
                if(paramMap != null && !paramMap.isEmpty()){
                    Set<String> keySet = paramMap.keySet();
                    for(String key : keySet){
                        query.setParameter(key, paramMap.get(key));
                    }
                }
                if(start > -1 && limit > 0){
                    query.setFirstResult(start);
                    query.setMaxResults(limit);
                }
                return query.list();
            }
            
        });
    }
    
    @Override
    public void update(AccountAlert transientInstance) {
        log.debug("update AccountAlert instance");
        try {
            getHibernateTemplate().update(transientInstance);
            log.debug("update successful");
        } catch (RuntimeException re) {
            log.error("update failed", re);
            throw re;
        }
    }
    
    public static AccountAlertDAO getFromApplicationContext(
            ApplicationContext ctx) {
        return (AccountAlertDAO) ctx.getBean("AccountAlertDAO");
    }

}
