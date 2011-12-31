package com.integral.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

public interface IBaseDao {
    /**
     * <p>Discription:[使用Hql语句查询数据]</p>
     * @param hql
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List queryByHQL(String hql, Object[] params);
    /**
     * <p>Discription:[使用Sql查询数据]</p>
     * @param sql
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List queryBySQL(String sql, Object[] params);
    
    /**
     * <p>Discription:[使用Hql语句查询分页数据]</p>
     * @param hql
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List queryPageByHQL(String hql, Object[] params, int start, int limit);
    
    /**
     * <p>Discription:[使用Sql查询分页数据]</p>
     * @param sql
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List queryPageBySQL(String sql, Object[] params, int start, int limit);
    /**
     * <p>Discription:[调用jdbc执行sql语句,用于insert/update/delete,需要自行组装sql语句]</p>
     * @param sql
     * @return
     * @throws DataAccessResourceFailureException
     * @throws HibernateException
     * @throws IllegalStateException
     * @throws SQLException
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public int excuteSQL(String sql, Object[] params) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException;
    /**
     * <p>Discription:[调用jdbc执行复杂sql的查询]</p>
     * @param sql sql语句
     * @param start 分页查询起始
     * @param limit 分页查询终止
     * @param params 查询的其他参数
     * @return
     * @throws SQLException
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List queryListByPageByJDBC(String sql, int start, int limit, Object[] params) throws SQLException;
    
    /**
     * <p>Discription:[JDBC批量执行SQL语句]</p>
     * @param sql
     * @param paramList 一个数组集合
     * @return
     * @throws Exception
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int excuteSQLBatch(String sql, List<Object[]> paramList) throws Exception;
}
