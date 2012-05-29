package com.integral.system.systembackup.dao;

import java.util.Collection;
import java.util.List;

import com.integral.system.systembackup.bean.SystemBackSettingInfo;

public interface ISystemBackupSettingDao {
    /**
     * <p>Discription:[批量新增或修改]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdateAll(Collection<SystemBackSettingInfo> instance);
    /**
     * <p>Discription:[新增或修改]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(SystemBackSettingInfo instance);
    /**
     * <p>Discription:[属性查询]</p>
     * @param instance
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByExample(SystemBackSettingInfo instance);
    /**
     * <p>Discription:[主键查询]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public SystemBackSettingInfo findById( java.lang.String id);
    /**
     * <p>Discription:[批量删除]</p>
     * @param persistentInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(Collection<SystemBackSettingInfo> persistentInstance);
    /**
     * <p>Discription:[删除]</p>
     * @param persistentInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void delete(SystemBackSettingInfo persistentInstance);
    /**
     * <p>Discription:[新增]</p>
     * @param transientInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(SystemBackSettingInfo transientInstance);
}
