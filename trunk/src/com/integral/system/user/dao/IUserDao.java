package com.integral.system.user.dao;

import java.util.Collection;
import java.util.List;

import com.integral.system.user.bean.UserInfo;

public interface IUserDao {
    /**
     * <p>Discription:[根据用户名查询用户信息]</p>
     * @param userName
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public List getUserByName(String userName);

    /**
     * <p>Discription:[使用用户属性查询用户信息]</p>
     * @param propertyName
     * @param value
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public List findByProperties(String propertyName, Object value);
    /**
     * <p>Discription:[用户列表，分页查询]</p>
     * @param start
     * @param limit
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public List findUserByPage(String sql, int start, int limit, Object [] params);
    /**
     * 新增或修改用户信息
     * @param entity
     */
    public void saveOrUpdate(UserInfo entity);
    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    public UserInfo findById(String id);
    /**
     * 批量删除用户信息
     * @param entities
     */
    public void deleteAll(Collection entities);
}
