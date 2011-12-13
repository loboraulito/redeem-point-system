package com.integral.system.right.service;

import java.util.List;

/** 
 * <p>Description: [按钮权限]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public interface IRightService {
    /**
     * <p>Discription:[根据按钮ID查询能访问该按钮的角色名称]</p>
     * @param buttonId
     * @return
     * @author: 代超
     * @update: 2011-6-6 代超[变更描述]
     */
    public List<String> getButtonRoleNameByButton(String buttonId);
    
    /**
     * <p>Discription:[根据菜单ID，角色ID查询有权限访问的按钮]</p>
     * @param menuId
     * @param roleId
     * @return
     * @author: 代超
     * @update: 2011-6-6 代超[变更描述]
     */
    public List getButtonByRight(String menuId,String roleId);
    /**
     * <p>Discription:[根据角色ID，删除对应的权限按钮信息]</p>
     * @param roles
     * @author: 代超
     * @throws Exception 
     * @update: 2011-7-3 代超[变更描述]
     */
    public void deleteByRole(String[] roles) throws Exception;
}
