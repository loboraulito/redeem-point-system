package com.integral.applications.account.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.applications.account.bean.AccountCardInfo;
import com.integral.applications.account.dao.IAccountCardInfoDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class AccountCardInfoDao extends HibernateDaoSupport implements IAccountCardInfoDao{

    private Log log = LogFactory.getLog(AccountCardInfoDao.class);
    /**
     * <p>Discription:[构造器方法描述]</p>
     * @coustructor 方法.
     */
    public AccountCardInfoDao() {
    }
    
    public void initDao(){
        //do nothing
    }
    
    public void save(AccountCardInfo cardInfo){
        log.debug("saving AccountCardInfo instance");
        try {
            getHibernateTemplate().save(cardInfo);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void update(AccountCardInfo cardInfo){
        log.debug("saving AccountCardInfo instance");
        try {
            getHibernateTemplate().update(cardInfo);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdate(AccountCardInfo cardInfo){
        log.debug("saving AccountCardInfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(merge(cardInfo));
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdateAll(Collection<AccountCardInfo> cardInfos){
        log.debug("saving AccountCardInfo instance");
        try {
            List<AccountCardInfo> updateList = new ArrayList<AccountCardInfo>();
            for(AccountCardInfo card : cardInfos){
                updateList.add(merge(card));
            }
            getHibernateTemplate().saveOrUpdateAll(updateList);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void delete(AccountCardInfo cardInfos){
        log.debug("deleting AccountCardInfo instance");
        try {
            getHibernateTemplate().delete(merge(cardInfos));
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public void deleteAll(Collection<AccountCardInfo> cardInfos){
        log.debug("deleting all AccountCardInfo instance");
        try {
            //getHibernateTemplate().getSessionFactory().getCurrentSession().merge(cardInfos);
            List<AccountCardInfo> deleteList = new ArrayList<AccountCardInfo>();
            for(AccountCardInfo card : cardInfos){
                deleteList.add(merge(card));
            }
            getHibernateTemplate().deleteAll(deleteList);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public AccountCardInfo findById(java.lang.String id) {
        log.debug("getting AccountCardInfo instance with id: " + id);
        try {
            AccountCardInfo instance = (AccountCardInfo) getHibernateTemplate()
                    .get(AccountCardInfo.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List<AccountCardInfo> findByExample(AccountCardInfo instance) {
        log.debug("finding AccountCardInfo instance by example");
        try {
            List<AccountCardInfo> results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
    
    public List<AccountCardInfo> findByProperty(String propertyName, Object value) {
        log.debug("finding AccountCardInfo instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from AccountCardInfo as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    
    /**
     * <p>Discription:[自定义sql查询]</p>
     * @param sql 自定义sql语句
     * @param isSql sql是否是标准sql语句
     * @param paramMap 参数集合
     * @param start 分页参数
     * @param limit 分页参数
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findBySql(final String sql, final boolean isSql, final Map<String, Object> paramMap, final int start, final int limit){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String querySql = "from AccountCardInfo model";
                Query query = null;
                if(sql == null || "".equals(sql.trim())){
                    query = session.createQuery(querySql);
                }else{
                    if(isSql){
                        query = session.createSQLQuery(sql);
                    }else{
                        query = session.createQuery(sql);
                    }
                }
                if(paramMap != null && paramMap.size() > 0){
                    for(String key : paramMap.keySet()){
                        query.setParameter(key, paramMap.get(key));
                    }
                }
                if(start > -1){
                    query.setFirstResult(start);
                }
                if(limit > 0){
                    query.setMaxResults(limit);
                }
                return query.list();
            }
        });
    }
    
    public AccountCardInfo merge(AccountCardInfo detachedInstance) {
        log.debug("merging AccountCardInfo instance");
        try {
            AccountCardInfo result = (AccountCardInfo) getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
}
