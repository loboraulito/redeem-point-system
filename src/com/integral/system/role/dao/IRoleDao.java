package com.integral.system.role.dao;

import java.util.List;

public interface IRoleDao {
    public List findAllRole();
    public List findRoleByUserIdName(String userId);
}
