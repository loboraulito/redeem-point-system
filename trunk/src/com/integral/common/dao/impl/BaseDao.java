package com.integral.common.dao.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.integral.common.util.SQLParameter;
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
    public List queryPageByHQL(final String hql, final Map<String, Object> params, final int start, final int limit) {
        log.info("query by sql: " + hql);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session){
                Query query = session.createQuery(hql);
                if(params!=null && params.size()>0){
                    for (String key : params.keySet()) {
                        query.setParameter(key, params.get(key));
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
    
    @Override
    public List queryPageBySQL(final String sql, final Map<String, Object> params, final int start, final int limit) {
        log.info("query by sql: " + sql);
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session){
                Query query = session.createSQLQuery(sql);
                if(params!=null && params.size()>0){
                    for (String key : params.keySet()) {
                        query.setParameter(key, params.get(key));
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
        Session session = getSession();
        Connection conn = session.connection();
        PreparedStatement prepareStatement = conn.prepareStatement(sql);
        if(params != null){
            for(int i=0;i<params.length;i++){
                prepareStatement.setObject(i+1, params[i]);
            }
        }
        prepareStatement.execute();
        prepareStatement.close();
        conn.close();
        session.close();
        return 0;
    }
    
    /**
     * <p>Discription:[使用Hibernate自带的批量执行sql语句]</p>
     * @param sql
     * @param paramList
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int excuteSqlBatchWithHibernate(String sql, Object ... paramList){
        log.info("excute by sql: " + sql);
        return getHibernateTemplate().bulkUpdate(sql, paramList);
    }
    /**
     * <p>Discription:[JDBC批量执行SQL语句]</p>
     * @param sql
     * @param paramList 一个数组集合
     * @return
     * @throws Exception
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int excuteSQLBatch(String sql, List<Object[]> paramList) throws Exception{
        SessionFactory sessionFactory = getSessionFactory();
        SessionFactoryImpl s = (SessionFactoryImpl) sessionFactory;
        Session session = getSession();
        Connection con = session.connection();
        //使用预编译
        PreparedStatement prest = null;
        boolean isAutoCommit = false;
        try{
            isAutoCommit = con.getAutoCommit();
            DatabaseMetaData dbmd=con.getMetaData();
            //关闭自动提交
            con.setAutoCommit(false);
            //ResultSet.TYPE_FORWARD_ONLY：缺省类型。只允许向前访问一次，并且不会受到其他用户对该数据库所作更改的影响。
            //ResultSet.TYPE_SCROLL_INSENSITIVE：允许在列表中向前或向后移动，甚至可以进行特定定位，例如移至列表中的第四个记录或者从当前位置向后移动两个记录。不会受到其他用户对该数据库所作更改的影响。
            //ResultSet.TYPE_SCROLL_SENSITIVE：象 TYPE_SCROLL_INSENSITIVE 一样，允许在记录中定位。
            //ResultSet.CONCUR_READ_ONLY：这是缺省值，指定不可以更新ResultSet
            //ResultSet.CONCUR_UPDATABLE: 指定可以更新 ResultSet
            prest = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //如果底层数据库支持批量更新的话
            if(dbmd.supportsBatchUpdates()){
                if(paramList != null && paramList.size() > 0){
                    for(int i=0, j=paramList.size(); i < j; i++){
                        Object[] obj = paramList.get(i);
                        if(obj != null && obj.length > 0){
                            for(int k=0;k<obj.length;k++){
                                prest.setObject(k+1, obj[k]);
                            }
                            prest.addBatch();
                        }
                    }
                }
                prest.executeBatch();
            }else{
                if(paramList != null && paramList.size() > 0){
                    for(int i=0, j=paramList.size(); i < j; i++){
                        Object[] obj = paramList.get(i);
                        excuteSQL(sql, obj);
                    }
                }
            }
            con.commit();
        }catch(Exception e){
            log.error(e.getMessage());
            try{
                con.rollback();
            }catch(SQLException e1){
                log.error(e1.getMessage());
                throw e1;
            }
        }finally{
            log.info("execute batch sql by JDBC : " + sql);
            if(prest != null){
                prest.close();
            }
            if(con != null){
                con.close();
                con.setAutoCommit(isAutoCommit);
            }
            if(session != null){
                session.close();
            }
        }
        return 0;
    }
    
    public List<Object []> queryListByPageByJDBC(String sql, int start, int limit, Object[] params) {
        SessionFactory sessionFactory = getSessionFactory();
        SessionFactoryImpl s = (SessionFactoryImpl) sessionFactory;
        Session session = getSession();//sessionFactory.getCurrentSession();
        Connection con = session.connection();
        sql = HibernateUtils.getHibernateLimitString(s.getDialect(), sql, start, limit);
        log.info("excute by sql jdbc: " + sql);
        List<Object []> result = new ArrayList<Object []>();
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
                prepareStatement.setInt(position+1, start);
                prepareStatement.setInt(position+2, limit);
            }else if(limit > 0){
                prepareStatement.setInt(position+1, limit);
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
    
    public List<Object []> queryListByPageByJDBC(String sql, int start, int limit, SQLParameter[] params) {
        SessionFactory sessionFactory = getSessionFactory();
        SessionFactoryImpl s = (SessionFactoryImpl) sessionFactory;
        Session session = getSession();//sessionFactory.getCurrentSession();
        Connection con = session.connection();
        sql = HibernateUtils.getHibernateLimitString(s.getDialect(), sql, start, limit);
        log.info("excute by sql jdbc: " + sql);
        List<Object []> result = new ArrayList<Object []>();
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try{
            prepareStatement = con.prepareStatement(sql);
            int position = 0;
            if(params != null){
                for(int i=0;i<params.length;i++){
                    SQLParameter param = params[i];
                    prepareStatement.setObject(i+1, param.getObj(), param.getTargetSqlType());
                }
                position = params.length;
            }
            if(start > 0){
                prepareStatement.setInt(position+1, start);
                prepareStatement.setInt(position+2, limit);
            }else if(limit > 0){
                prepareStatement.setInt(position+1, limit);
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
