package com.integral.system.menu.service;

import java.util.List;

public interface IMenuService {
    /**
     * <p>Discription:[查询父菜单下的子菜单]</p>
     * @param rootMenuId
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List findChildMenu(String rootMenuId);
    /**
     * <p>Discription:[查询父菜单下的子菜单,组装成树形结构]</p>
     * @param rootMenuId
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List findChildMenuTree(String rootMenuId);
    /**
     * <p>Discription:[查询用户能访问的主菜单]</p>
     * @param userName
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List findRootMenu(String userName);
}
