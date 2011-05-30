package com.integral.system.role.service;

import java.util.List;

public interface IUserRoleService {
    /**
     * <p>Discription:[查询用户的角色ID]</p>
     * @param userName
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    public List findRoleByUserName(String userName);
}
