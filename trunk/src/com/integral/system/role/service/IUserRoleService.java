package com.integral.system.role.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

public interface IUserRoleService {
    /**
     * <p>Discription:[查询用户的角色ID]</p>
     * @param userName
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    public List findRoleByUserName(String userName);
    /**
     * <p>Discription:[通过角色ID删除用户角色信息]</p>
     * @param roles
     * @author: 代超
     * @throws SQLException 
     * @throws IllegalStateException 
     * @throws HibernateException 
     * @throws DataAccessResourceFailureException 
     * @update: 2011-7-3 代超[变更描述]
     */
    public void deleteByRole(String[] roles) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException;
    
    /**
     * <p>Discription:[通过用户名删除用户角色信息]</p>
     * @param roles
     * @author: 代超
     * @throws SQLException 
     * @throws IllegalStateException 
     * @throws HibernateException 
     * @throws DataAccessResourceFailureException 
     * @update: 2011-7-3 代超[变更描述]
     */
    public void deleteByUser(String[] users) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException;
}
