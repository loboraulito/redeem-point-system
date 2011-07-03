package com.integral.system.role.dao;

import java.util.Collection;
import java.util.List;

import com.integral.system.role.bean.RoleInfo;

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
    /**
     * 批量删除角色信息
     * <p>Discription:[方法功能描述]</p>
     * @param entities
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public void deleteAll(Collection entities);
    /**
     * 新增或修改角色信息
     * <p>Discription:[方法功能描述]</p>
     * @param role
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public void saveOrUpdate(RoleInfo role);
}
