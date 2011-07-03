package com.integral.system.role.service;

import java.util.Collection;
import java.util.List;

import com.integral.system.role.bean.RoleInfo;

/** 
 * <p>Description: [角色管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-28
 */
public interface IRoleService {
    /**
     * <p>Discription:[角色数量]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public Long findRoleSize();
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
