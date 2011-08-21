package com.integral.system.codelist.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.codelist.bean.CodeList;
import com.integral.system.codelist.bean.CodeListData;
import com.integral.system.codelist.dao.ICodeListDataDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CodeListDataDao extends HibernateDaoSupport implements ICodeListDataDao {
    private static final Log log = LogFactory.getLog(CodeListDataDao.class);
    public static final String DATAKEY = "dataKey";
    public static final String DATAVALUE = "dataValue";
    protected void initDao() {
        //do nothing
    }
    public List findCodeListDataByPage(final boolean isSql, final String sql, final int start, final int limit, final Object[] params) {
        return getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = null;
                if(sql == null || "".equals(sql)){
                    query = session.createQuery("from CodeListData");
                }else{
                    if(isSql){
                        query = session.createSQLQuery(sql);
                    }else{
                        query = session.createQuery(sql);
                    }
                }
                
                if(params != null && params.length>0){
                    for(int i=0;i<params.length;i++){
                        query.setParameter(i, params[i]);
                    }
                }
                if(start>-1 && limit>0){
                    query.setFirstResult(start);
                    query.setMaxResults(limit);
                }
                List list = query.list();
                return list;
            }
        });
    }
    
    public void saveOrUpdate(CodeListData entity) {
        log.debug("saveOrUpdate CodeListData");
        try {
            getHibernateTemplate().saveOrUpdate(entity);
        } catch (RuntimeException re) {
            log.error("saveOrUpdate CodeListData ", re);
            throw re;
        }
    }
    
    public void deleteAll(Collection<CodeListData> entities) {
        log.debug("deleteAll CodeListData");
        try {
            getHibernateTemplate().deleteAll(entities);
        } catch (RuntimeException re) {
            log.error("deleteAll CodeListData ", re);
            throw re;
        }
    }
    
    public List findByExample(CodeListData instance) {
        log.debug("finding CodeListData instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CodeListData instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CodeListData as model where model." + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByDataKey(Object dataKey) {
        return findByProperty(DATAKEY, dataKey);
    }
    @Override
    public List findByDataValue(Object dataValue) {
        return findByProperty(DATAVALUE, dataValue);
    }
    @Override
    public void saveOrUpdateAll(Collection<CodeListData> entities) {
        log.debug("saveOrUpdate CodeListData");
        try {
            getHibernateTemplate().saveOrUpdateAll(entities);
        } catch (RuntimeException re) {
            log.error("saveOrUpdate CodeListData ", re);
            throw re;
        }
    }

}
