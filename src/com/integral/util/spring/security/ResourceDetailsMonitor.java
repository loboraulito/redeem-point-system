package com.integral.util.spring.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.integral.system.menu.dao.IMenuDao;
import com.integral.system.role.dao.IRoleDao;
import com.integral.system.role.dao.IRoleMenuDao;
import com.integral.system.role.dao.IUserRoleDao;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.user.dao.IUserDao;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 20, 2011
 */
public class ResourceDetailsMonitor implements InitializingBean {
    private static final Log log = LogFactory.getLog(ResourceDetailsMonitor.class);
    private AccessDecisionManager accessDecisionManager;
    private FilterSecurityInterceptor filterSecurityInterceptor;
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    
    private IUserDao userDao;
    private IRoleDao roleDao;
    private IUserRoleDao userRoleDao;
    private IMenuDao menuDao;
    private IRoleMenuDao roleMenuDao;
    private IRoleMenuService roleMenuService;
        
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
     * <p>Discription:[方法功能描述]</p>
     * @return IUserRoleDao userRoleDao.
     */
    public IUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param userRoleDao The userRoleDao to set.
     */
    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IMenuDao menuDao.
     */
    public IMenuDao getMenuDao() {
        return menuDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param menuDao The menuDao to set.
     */
    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRoleMenuDao roleMenuDao.
     */
    public IRoleMenuDao getRoleMenuDao() {
        return roleMenuDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param roleMenuDao The roleMenuDao to set.
     */
    public void setRoleMenuDao(IRoleMenuDao roleMenuDao) {
        this.roleMenuDao = roleMenuDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRoleMenuService roleMenuService.
     */
    public IRoleMenuService getRoleMenuService() {
        return roleMenuService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param roleMenuService The roleMenuService to set.
     */
    public void setRoleMenuService(IRoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return FilterInvocationSecurityMetadataSource securityMetadataSource.
     */
    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param securityMetadataSource The securityMetadataSource to set.
     */
    public void setSecurityMetadataSource(
            FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return FilterSecurityInterceptor filterSecurityInterceptor.
     */
    public FilterSecurityInterceptor getFilterSecurityInterceptor() {
        return filterSecurityInterceptor;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param filterSecurityInterceptor The filterSecurityInterceptor to set.
     */
    public void setFilterSecurityInterceptor(
            FilterSecurityInterceptor filterSecurityInterceptor) {
        this.filterSecurityInterceptor = filterSecurityInterceptor;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return AccessDecisionManager accessDecisionManager.
     */
    public AccessDecisionManager getAccessDecisionManager() {
        return accessDecisionManager;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param accessDecisionManager The accessDecisionManager to set.
     */
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refresh();
    }
    
    public void refresh(){
        FilterInvocationSecurityMetadataSource newSource = new SecurityMetadataSourceServiceImpl(this.userDao,this.roleDao,this.userRoleDao,this.menuDao,this.roleMenuDao,this.roleMenuService);
        if(newSource!=null){
            log.debug(newSource);
            filterSecurityInterceptor.setSecurityMetadataSource(newSource);
        }
    }
}
