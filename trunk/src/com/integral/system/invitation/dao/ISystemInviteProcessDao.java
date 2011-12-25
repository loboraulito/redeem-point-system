package com.integral.system.invitation.dao;

import java.util.List;
import java.util.Map;

import com.integral.system.invitation.bean.SystemInviteProcess;

public interface ISystemInviteProcessDao {
    /**
     * <p>Discription:[查询数量]</p>
     * @param sql
     * @param isHql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCountByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params);
    /**
     * <p>Discription:[分页查询数据]</p>
     * @param sql
     * @param isHql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params);
}
