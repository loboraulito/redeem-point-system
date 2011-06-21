/**
 * 
 */
package com.integral.system.right.dao;

import java.util.Collection;

/**
 * @author cdai
 *
 */
public interface IAuthorizeDao {
	/**
	 * 批量删除
	 * @param entities
	 */
	public void deleteAll(Collection entities);
	
	/**
     * 批量新增或修改
     * @param entities
     */
    public void saveOrUpdateAll(Collection entities);
}
