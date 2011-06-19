package com.integral.system.user.service;

import java.util.List;

/** 
 * <p>Description: [用户管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-11
 */
public interface IUserService {
    /**
     * <p>Discription:[用户列表，分页自定义sql条件查询]</p>
     * @param start
     * @param limit
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public List findUserByPageCondition(String sql, int start, int limit, Object [] params);
    /**
     * <p>Discription:[查询所有用户的数量]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public Long findUserSize();
    /**
     * <p>Discription:[查询用户列表，被保护的信息]</p>
     * @param start
     * @param limit
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public List findUserByPageWithProtect(int start, int limit);
}
