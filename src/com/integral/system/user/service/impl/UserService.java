package com.integral.system.user.service.impl;

import java.util.Collection;
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
    
    public void saveOrUpdate(UserInfo entity){
        this.userDao.saveOrUpdate(entity);
    }
    
    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    public UserInfo findById(String id){
        return this.userDao.findById(id);
    }
    
    public void deleteAll(Collection entities){
        this.userDao.deleteAll(entities);
    }
    
    /**
     * <p>Discription:[根据用户名查询用户信息]</p>
     * @param userName
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public List getUserByName(String userName){
        return this.userDao.getUserByName(userName);
    }
    
    public List getUserByRole(String roleId){
        String sql = "SELECT model.operater_id from supplier_role model where model.role_id = ?";
        return this.baseDao.queryBySQL(sql, new Object[]{roleId});
    }
}
