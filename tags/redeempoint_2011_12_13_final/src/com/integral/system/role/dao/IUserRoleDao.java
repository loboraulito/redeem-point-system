package com.integral.system.role.dao;

import java.util.Collection;
import java.util.List;

import com.integral.system.role.bean.UserRole;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public interface IUserRoleDao {
    public List findRoleByUserIdName(String userId);
    
    /**
     * 新增用户角色信息
     * @param entities
     */
    public void saveOrUpdateAll(Collection entities);
    
    /**
     * 新增用户角色信息
     * @param entities
     */
    public void saveOrUpdate(UserRole userRole);
}
