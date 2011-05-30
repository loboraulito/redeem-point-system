package com.integral.system.role.service;

import java.util.List;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-18
 */
public interface IRoleMenuService {
    /**
     * <p>Discription:[根据角色查询该角色能访问的URL]</p>
     * @param role
     * @return
     * @author cdai
     * @update May 19, 2011 cdai[变更描述]
     */
    public List<String> getRoleMenuMap(String role);
    /**
     * <p>Discription:[根据url查询有权限访问该url的角色]</p>
     * @param menu
     * @return
     * @author cdai
     * @update May 19, 2011 cdai[变更描述]
     */
    public List<String> getMenuRoleMap(String menu);
    /**
     * <p>Discription:[根据角色查询菜单信息]</p>
     * @param role
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    public List getRootMenuMap(String role);
    
    /**
     * <p>Discription:[根据角色查询菜单信息]</p>
     * @param role
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    public List getChildMenuMap(String role, String rootId);
}
