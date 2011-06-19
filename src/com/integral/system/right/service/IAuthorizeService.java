/**
 * 
 */
package com.integral.system.right.service;

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
     * 
     * <p>Discription:[查询授权菜单信息]</p>
     * @param roleId 角色ID
     * @param rootId 根节点ID
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public List showAuthorzieMenu(String roleId, String rootId);
}
