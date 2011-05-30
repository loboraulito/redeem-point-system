package com.integral.system.role.service.impl;

import java.util.List;

import com.integral.system.role.dao.IUserRoleDao;
import com.integral.system.role.dao.impl.RoleDao;
import com.integral.system.role.service.IUserRoleService;

public class UserRoleService implements IUserRoleService {
    private IUserRoleDao userRoleDao;
    private RoleDao roleDao;

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
}
