package com.integral.system.message.dao;

import java.util.List;
import java.util.Map;

import com.integral.system.message.bean.SystemMessage;

public interface ISystemMessageDao {
    /**
     * <p>Discription:[保存消息]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(SystemMessage instance);
    /**
     * <p>Discription:[新增消息]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(SystemMessage instance);
    /**
     * <p>Discription:[批量保存消息]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdateAll(List<SystemMessage> persistentInstances);
    /**
     * <p>Discription:[删除消息]</p>
     * @param persistentInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void delete(SystemMessage persistentInstance);
    /**
     * <p>Discription:[批量删除消息]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(List<SystemMessage> persistentInstances);
    /**
     * <p>Discription:[属性查找消息]</p>
     * @param propertyName
     * @param value
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<SystemMessage> findByProperty(String propertyName, Object value);
    /**
     * <p>Discription:[主键查询]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public SystemMessage findById(java.lang.String id);
    /**
     * <p>Discription:[实体查询]</p>
     * @param instance
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<SystemMessage> findByExample(SystemMessage instance);
    /**
     * <p>Discription:[分页查询]</p>
     * @param sql
     * @param isHql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<SystemMessage> findByParams(String sql, boolean isHql, int start, int limit,
            Map<String, Object> params);
    
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
}
