package com.integral.system.menu.service;

import java.util.List;

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
}
