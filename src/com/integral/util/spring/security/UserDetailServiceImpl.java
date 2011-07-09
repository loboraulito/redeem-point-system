package com.integral.util.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.integral.system.role.dao.IRoleDao;
import com.integral.system.user.bean.UserInfo;
import com.integral.system.user.dao.IUserDao;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 17, 2011
 */
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Log log = LogFactory
            .getLog(UserDetailServiceImpl.class);
    private IUserDao userDao;
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

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param arg0
     * @return
     * @throws UsernameNotFoundException
     * @author cdai
     * @update May 17, 2011 cdai[变更描述]
     */

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (username == null) {
            log.error("username is null");
        }
        if (userDao == null) {
            log.error("userDao is null");
        }
        Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
        List userList = userDao.getUserByName(username);
        if (userList == null || userList.size() < 1) {
            log.error(username + " is not exist",
                    new UsernameNotFoundException(username + " is not exist"));
            throw new UsernameNotFoundException(username + " is not exist");
        }
        log.debug("userList is not null :" + userList.size());
        UserInfo user = (UserInfo) userList.get(0);
        List<String> userAuths = this.roleDao.findRoleByUserIdName(user.getUserName());
        for(String auth : userAuths){
            auths.add(new GrantedAuthorityImpl(auth));
        }
        
        User userDetail = new User(user.getUserName(), user.getPassword(),
                true, true, true, true, auths);
        return userDetail;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

}
