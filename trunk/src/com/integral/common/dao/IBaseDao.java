package com.integral.common.dao;

import java.util.List;

public interface IBaseDao {
    /**
     * <p>Discription:[使用Hql语句查询数据]</p>
     * @param hql
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List queryByHQL(String hql, String[] params);
    /**
     * <p>Discription:[使用Sql查询数据]</p>
     * @param sql
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List queryBySQL(String sql, String[] params);
}
