package com.integral.system.codelist.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.system.codelist.dao.ICodeListDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CodeListDao extends HibernateDaoSupport implements ICodeListDao {
    private static final Log log = LogFactory.getLog(CodeListDao.class);
    protected void initDao() {
        //do nothing
    }
    
    public List getCodeListByPage(final boolean isSql, final String sql, final int start, final int limit, final Object[] params) {
        return getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = null;
                if(sql == null || "".equals(sql)){
                    query = session.createQuery("from CodeList");
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
                return query.list();
            }
        });
    }
}
