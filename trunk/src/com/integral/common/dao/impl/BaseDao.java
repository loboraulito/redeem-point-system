package com.integral.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integral.common.dao.IBaseDao;
import com.integral.util.HibernateUtils;

public class BaseDao extends HibernateDaoSupport implements IBaseDao {
    private static final Log log = LogFactory.getLog(BaseDao.class);
    protected void initDao() {
        //do nothing
    }

    @Override
    public List queryByHQL(final String hql, final Object[] params) {
        log.info("query by hql: " + hql);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session){
                Query query = session.createQuery(hql);
                if(params!=null && params.length>0){
                    for(int i=0;i<params.length;i++){
                        query.setParameter(i, params[i]);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public List queryBySQL(final String sql, final Object[] params) {
        log.info("query by sql: " + sql);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session){
                Query query = session.createSQLQuery(sql);
                if(params!=null && params.length>0){
                    for(int i=0;i<params.length;i++){
                        query.setParameter(i, params[i]);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public List queryPageByHQL(final String hql, final Object[] params, final int start, final int limit) {
        log.info("query by sql: " + hql);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session){
                Query query = session.createQuery(hql);
                if(params!=null && params.length>0){
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

    @Override
    public List queryPageBySQL(final String sql, final Object[] params, final int start, final int limit) {
        log.info("query by sql: " + sql);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session){
                Query query = session.createSQLQuery(sql);
                if(params!=null && params.length>0){
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
    
    public int excuteSQL(final String sql, final Object[] params) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
        log.info("excute by sql: " + sql);
        PreparedStatement prepareStatement = getSession().connection().prepareStatement(sql);
        if(params != null){
            for(int i=0;i<params.length;i++){
                prepareStatement.setObject(i+1, params[i]);
            }
        }
        prepareStatement.execute();
        return 0;
    }
    
    public List queryListByPageByJDBC(String sql, int start, int limit, Object[] params) {
        log.info("excute by sql jdbc: " + sql);
        SessionFactory sessionFactory = getSessionFactory();
        SessionFactoryImpl s = (SessionFactoryImpl) sessionFactory;
        Session session = getSession();//sessionFactory.getCurrentSession();
        Connection con = session.connection();
        sql = HibernateUtils.getHibernateLimitString(s.getDialect(), sql, start, limit);
        List result = new ArrayList();
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try{
            prepareStatement = con.prepareStatement(sql);
            int position = 0;
            if(params != null){
                for(int i=0;i<params.length;i++){
                    prepareStatement.setObject(i+1, params[i]);
                }
                position = params.length;
            }
            if(start > 0){
                prepareStatement.setObject(position+1, start);
                prepareStatement.setObject(position+2, limit);
            }else if(limit > 0){
                prepareStatement.setObject(position+1, limit);
            }
            //获取数据集
            rs = prepareStatement.executeQuery();
            if(rs != null){
                //获取数据列集
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while(rs.next()){
                    Object []obj = new Object[columnCount];
                    for(int i=0; i<columnCount;i++){
                        //String columnName = rsmd.getColumnName(i+1);
                        obj[i] = rs.getObject(i+1);
                    }
                    result.add(obj);
                }
            }
        }catch(SQLException e){
            log.error(e);
            return null;
        }finally{
            try{
                prepareStatement.close();
                rs.close();
                con.close();
                log.info("closing session....");
                session.close();
                log.info("closed session....");
            }catch(Exception e){
                log.error(e);
                return null;
            }
        }
        return result;
    }
}
