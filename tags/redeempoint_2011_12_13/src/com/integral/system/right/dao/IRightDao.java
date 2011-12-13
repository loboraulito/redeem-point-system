package com.integral.system.right.dao;

import java.util.List;

/** 
 * <p>Description: [按钮权限信息DAO]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public interface IRightDao {
    /**
     * <p>Discription:[根据菜单ID，角色ID查询有权限访问的按钮]</p>
     * @param menuId
     * @param roleId
     * @return
     * @author: 代超
     * @update: 2011-6-6 代超[变更描述]
     */
    public List getButtonByRight(String menuId,String roleId);
}
