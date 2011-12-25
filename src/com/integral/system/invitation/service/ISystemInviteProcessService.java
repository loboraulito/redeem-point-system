package com.integral.system.invitation.service;

import java.util.List;
import java.util.Map;

import com.integral.system.invitation.bean.SystemInviteProcess;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface ISystemInviteProcessService {
    /**
     * <p>Discription:[查询用户的系统请求]</p>
     * @param userId
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<SystemInviteProcess> findByUserId(String userId, int start, int limit);

    /**
     * <p>Discription:[查询用户的系统请求数量]</p>
     * @param userId
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCountByUserId(String userId);
    
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
}
