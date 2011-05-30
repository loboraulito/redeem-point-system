package com.integral.system.menu.dao;

import java.util.List;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public interface IMenuDao {
    public List findAllMenu();
    public List findAllMenuPath();
    /**
     * <p>Discription:[查询父菜单下的子菜单]</p>
     * @param rootMenuId
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List findChildMenu(String rootMenuId);
}
