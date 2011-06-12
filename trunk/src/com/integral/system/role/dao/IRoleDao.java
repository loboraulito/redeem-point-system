package com.integral.system.role.dao;

import java.util.List;

public interface IRoleDao {
    /**
     * <p>Discription:[查询所有角色]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public List findAllRole();
    /**
     * <p>Discription:[通过用户ID查询该用户的角色]</p>
     * @param userId
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public List findRoleByUserIdName(String userId);
    /**
     * <p>Discription:[分页查询角色列表]</p>
     * @param start
     * @param limit
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public List findRoleListByPage(int start, int limit);
}
