package com.integral.system.role.service;

import java.util.List;

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
}
