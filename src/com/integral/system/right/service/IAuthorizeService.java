/**
 * 
 */
package com.integral.system.right.service;

import java.util.Collection;
import java.util.List;

/**
 * @author cdai
 *
 */
public interface IAuthorizeService {
	/**
	 * 查询授权菜单信息
	 * @return
	 */
	public List showAuthorizeMenuInfo(String rootId);
	/**
	 * <p>Discription:[分页查询授权用户]</p>
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 * @author: 代超
	 * @update: 2011-6-19 代超[变更描述]
	 */
	public List showAuthorzieUser(int start, int limit, Object [] params);
	
	/**
     * <p>Discription:[分页查询授权用户的数量]</p>
     * @param start
     * @param limit
     * @param params
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public Long showAuthorzieUser(Object [] params);
    /**
     * 第一种方案: 一次性查询出所有的菜单，有权限的则选中<p>
     * 缺点：每次加载都将要去递归的加载整个树，将会导致系统慢<p>
     * 优点：简单明了，加载完就已经显示出是否选中<p>
     * <p>Discription:[查询授权菜单信息]</p>
     * @param roleId 角色ID
     * @param rootId 根节点ID
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public List showAuthorzieMenu(String roleId, String rootId);
    /**
     * 第二种方案: 先查询出所有的菜单，但是不选中<p>
     * 缺点：需要二次加载，并且在JS中做最后处理<p>
     * 优点：分批加载，先加载整个树，直接显示，降低系统使用率<p>
     * 查询树形菜单结构，未设置选中状态
     * @param rootId
     * @return
     */
    public List getChildList(String rootId);
    /**
     * 第二种方案: 根据角色，查询有权限的菜单，再到js中去处理是否选中<p>
     * 缺点：需要二次加载，并且在JS中做最后处理<p>
     * 优点：分批加载，先加载整个树，直接显示，降低系统使用率<p>
     * 查询所有有权限的菜单ID,按钮ID
     * @param roleId
     * @return
     */
    public List showAuthorzieMenu(String roleId);
    /**
     * 权限分配第一步,查询当前已分配的权限菜单
     * @param roleId
     * @return
     */
    public List findAuthorizeMenu(String roleId);
    
    /**
     * 权限分配第一步,查询当前已分配的权限按钮
     * @param roleId
     * @return
     */
    public List findAuthorizeButton(String roleId);
    /**
     * 批量删除权限按钮
     * @param entities
     */
    public void deleteAll(Collection entities);
    /**
     * 批量新增修改
     * @param entities
     */
    public void saveOrUpdateAll(Collection entities);
}
