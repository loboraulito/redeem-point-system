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
    /**
     * <p>Discription:[新增系统请求]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(SystemInviteProcess instance);
    /**
     * <p>Discription:[新增或者更新系统请求]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(SystemInviteProcess instance);
    /**
     * <p>Discription:[批量更新或者新增系统请求]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdateAll(List<SystemInviteProcess> persistentInstances);
    /**
     * <p>Discription:[删除系统请求]</p>
     * @param persistentInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void delete(SystemInviteProcess persistentInstance);
    /**
     * <p>Discription:[批量删除系统请求]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(List<SystemInviteProcess> persistentInstances);
    /**
     * <p>Discription:[主键查询]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public SystemInviteProcess findById(java.lang.String id);
}
