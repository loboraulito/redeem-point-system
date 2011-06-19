package com.integral.system.user.service.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.user.bean.UserInfo;
import com.integral.system.user.dao.IUserDao;
import com.integral.system.user.service.IUserService;
import com.integral.util.user.ProtectUserInfo;

/** 
 * <p>Description: [用户管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-11
 */
public class UserService implements IUserService {
    private IUserDao userDao;
    private BaseDao baseDao;

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return BaseDao baseDao.
     */
    public BaseDao getBaseDao() {
        return baseDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param baseDao The baseDao to set.
     */
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IUserDao userDao.
     */
    public IUserDao getUserDao() {
        return userDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param userDao The userDao to set.
     */
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List findUserByPageCondition(String sql,int start, int limit, Object [] params) {
        return this.userDao.findUserByPage(sql, start, limit, params);
    }
    
    public Long findUserSize(){
        long size = 0L;
        String sql = "select count(employee_info.operater_id) as user_size from employee_info";
        List list = this.baseDao.queryBySQL(sql, null);
        if(list!=null){
            size = NumberUtils.toLong((String.valueOf(list.get(0))), 0L);
        }
        return size;
    }

    @Override
    public List findUserByPageWithProtect(int start, int limit) {
        List<UserInfo> userList = this.userDao.findUserByPage(null, start, limit, null);
        return ProtectUserInfo.protectUserInfo(userList);
    }
}
