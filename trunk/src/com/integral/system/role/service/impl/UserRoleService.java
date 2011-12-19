package com.integral.system.role.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import com.integral.common.dao.IBaseDao;
import com.integral.system.role.bean.UserRole;
import com.integral.system.role.dao.IUserRoleDao;
import com.integral.system.role.dao.impl.RoleDao;
import com.integral.system.role.service.IUserRoleService;

public class UserRoleService implements IUserRoleService {
    private IUserRoleDao userRoleDao;
    private RoleDao roleDao;
    private IBaseDao baseDao;
    

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IBaseDao baseDao.
     */
    public IBaseDao getBaseDao() {
        return baseDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param baseDao The baseDao to set.
     */
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IUserRoleDao userRoleDao.
     */
    public IUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param userRoleDao
     *            The userRoleDao to set.
     */
    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return RoleDao roleDao.
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param roleDao
     *            The roleDao to set.
     */
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List findRoleByUserName(String userName) {
        return this.userRoleDao.findRoleByUserIdName(userName);
    }
    
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
    public void deleteByRole(String[] roles) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
        if(roles == null || roles.length<1){
            return;
        }
        String sql = "delete from supplier_role where role_id in ( ? ";
        if(roles != null && roles.length>1){
            for(int i=1;i<roles.length;i++){
                sql += " , ? ";
            }
        }
        sql += " )";
        this.baseDao.excuteSQL(sql, roles);
    }
    
    /**
     * <p>Discription:[通过用户ID删除用户角色信息]</p>
     * @param roles
     * @author: 代超
     * @throws SQLException 
     * @throws IllegalStateException 
     * @throws HibernateException 
     * @throws DataAccessResourceFailureException 
     * @update: 2011-7-3 代超[变更描述]
     */
    public void deleteByUser(String[] users) throws DataAccessResourceFailureException, HibernateException, IllegalStateException, SQLException{
        if(users == null || users.length<1){
            return;
        }
        String sql = "delete from supplier_role where operater_id in ( ? ";
        if(users != null && users.length>1){
            for(int i=1;i<users.length;i++){
                sql += " , ? ";
            }
        }
        sql += " )";
        this.baseDao.excuteSQL(sql, users);
    }
    
    /**
     * 新增用户角色信息
     * @param entities
     */
    public void saveOrUpdateAll(Collection entities){
        this.userRoleDao.saveOrUpdateAll(entities);
    }
    
    /**
     * 更新用户的角色信息
     * @param users - users 的第一项是角色ID，之后才是userID
     * @throws Exception 
     */
    public void updateByUser(String [] users) throws Exception{
        if(users == null || users.length<2){
            return;
        }
        String sql = "update supplier_role set role_id = ? where operater_id in ( ? ";
        if(users != null && users.length>1){
            for(int i=2;i<users.length;i++){
                sql += " , ? ";
            }
        }
        sql += " )";
        this.baseDao.excuteSQL(sql, users);
    }
    
    /**
     * 新增用户角色信息
     * @param entities
     */
    public void saveOrUpdate(UserRole userRole){
        this.userRoleDao.saveOrUpdate(userRole);
    }
    
    /**
     * <p>Discription:[根据用户ID查询用户角色]</p>
     * @param userId
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findRoleByUserID(String userId){
        return this.userRoleDao.findRoleByUserID(userId);
    }
}
