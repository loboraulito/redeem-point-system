package com.integral.system.role.dao;

import java.util.Collection;
import java.util.List;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 18, 2011
 */
public interface IRoleMenuDao {
    public List queryBySQL(String sql, String[] params);
    
    public List queryByHQL(String hql, String[] params);
    /**
     * 批量删除
     * @param entities
     */
    public void deleteAll(Collection entities);
    
    
    /**
     * 批量新增修改
     * @param entities
     */
    public void saveOrUpdateAll(Collection entities);
}
