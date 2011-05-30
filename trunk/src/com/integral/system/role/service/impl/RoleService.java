package com.integral.system.role.service.impl;

import com.integral.system.role.dao.IRoleDao;
import com.integral.system.role.service.IRoleService;

/** 
 * <p>Description: [角色管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-28
 */
public class RoleService implements IRoleService {
    private IRoleDao roleDao;

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRoleDao roleDao.
     */
    public IRoleDao getRoleDao() {
        return roleDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param roleDao The roleDao to set.
     */
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
