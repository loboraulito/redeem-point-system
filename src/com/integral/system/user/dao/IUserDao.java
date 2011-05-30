package com.integral.system.user.dao;

import java.util.List;

public interface IUserDao {
    public List getUserByName(String userName);

    public List findByProperties(String propertyName, Object value);
}
