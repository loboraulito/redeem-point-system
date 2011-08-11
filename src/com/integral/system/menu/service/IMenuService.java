package com.integral.system.menu.service;

import java.util.Collection;
import java.util.List;

import com.integral.system.menu.bean.MenuInfo;

public interface IMenuService {
    /**
     * <p>Discription:[菜单管理功能的列表]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List findAll();
    /**
     * <p>Discription:[查询所有菜单的数量]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public long findAllMenuSize();
    /**
     * <p>Discription:[分页查询菜单]</p>
     * @param start
     * @param limit
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public List findMenuByPage(int start, int limit);
    /**
     * 分页查询，并且查询出上级菜单的名称
     * @param start
     * @param limit
     * @return
     */
    public List findMenuByPageWithParentName(int start, int limit);
    
    /**
     * <p>Discription:[查询父级菜单]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public List findRootMenu();
    
    /**
     * <p>Discription:[查询父菜单下的子菜单]</p>
     * @param rootMenuId
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List findChildMenu(String rootMenuId);
    
    /**
     * <p>Discription:[添加或修改菜单信息]</p>
     * @param menu
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void saveOrUpdateMenu(MenuInfo menu);
    
    /**
     * <p>Discription:[使用主键查询]</p>
     * @param menuId
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public MenuInfo findById(String menuId);
    
    /**
     * <p>Discription:[批量删除菜单信息]</p>
     * @param menus
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void deleteAll(Collection menus);
    /**
     * <p>Discription:[使用菜单路径，查询菜单ID]</p>
     * @param menuPath
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByMenuPath(String menuPath);
    
}
