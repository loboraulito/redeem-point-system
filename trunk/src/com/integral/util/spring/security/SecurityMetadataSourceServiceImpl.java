package com.integral.util.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RegexUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.util.AntPathMatcher;

import com.integral.system.menu.bean.ButtonInfo;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.dao.IMenuDao;
import com.integral.system.menu.service.IButtonService;
import com.integral.system.right.service.IRightService;
import com.integral.system.role.dao.IRoleDao;
import com.integral.system.role.dao.IRoleMenuDao;
import com.integral.system.role.dao.IUserRoleDao;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.user.dao.IUserDao;


/** 
 * <p>Description: [最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。<br>此处使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，事实上你还要用正则的方式来匹配，或者自己实现一个matcher。<br>
 * 此类在初始化时，应该取到所有资源及其对应角色的定义。<br>说明：对于方法的spring注入，只能在方法和成员变量里注入，如果一个类要进行实例化的时候，不能注入对象和操作对象，所以在构造函数里不能进行操作注入的数据。]</p>
 * 
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-17
 */
public class SecurityMetadataSourceServiceImpl implements
        FilterInvocationSecurityMetadataSource {
    private static final Log log = LogFactory.getLog(SecurityMetadataSourceServiceImpl.class);
    //private UrlMatcher urlMatcher = new AntUrlPathMatcher();
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private UrlMatcher urlMatcher = new RegexUrlPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private IUserDao userDao;
    private IRoleDao roleDao;
    private IUserRoleDao userRoleDao;
    private IMenuDao menuDao;
    private IRoleMenuDao roleMenuDao;
    private IRoleMenuService roleMenuService;
    private IRightService rightService;
    private IButtonService buttonService;

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IButtonService buttonService.
     */
    public IButtonService getButtonService() {
        return buttonService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param buttonService The buttonService to set.
     */
    public void setButtonService(IButtonService buttonService) {
        this.buttonService = buttonService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRightService rightService.
     */
    public IRightService getRightService() {
        return rightService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param rightService The rightService to set.
     */
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
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

    public SecurityMetadataSourceServiceImpl(){
        //this.sessionFactory = 
        loadResourceDefine();
    }
    /*
    public SecurityMetadataSourceServiceImpl(IUserDao userDao,IRoleDao roleDao,IUserRoleDao userRoleDao, IMenuDao menuDao, IRoleMenuDao roleMenuDao, IRoleMenuService roleMenuService){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        this.roleMenuDao = roleMenuDao;
        this.menuDao = menuDao;
        this.roleMenuService = roleMenuService;
        loadResourceDefine();
    }
    */
    public SecurityMetadataSourceServiceImpl(IUserDao userDao,IRoleDao roleDao,IUserRoleDao userRoleDao, IMenuDao menuDao, IRoleMenuDao roleMenuDao, IRoleMenuService roleMenuService, IRightService rightService, IButtonService buttonService){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        this.roleMenuDao = roleMenuDao;
        this.menuDao = menuDao;
        this.roleMenuService = roleMenuService;
        this.rightService = rightService;
        this.buttonService = buttonService;
        loadResourceDefine();
    }
    
    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * <p>Discription:[读取资源菜单按钮信息到内存中]</p>
     * @author: 代超
     * @update: 2011-6-6 代超[变更描述]
     */
    public void loadResourceDefine(){
        //List<String> menus = this.menuDao.findAllMenuPath();
        List<MenuInfo> menus = this.menuDao.findAll();
        List<ButtonInfo> buttons = this.buttonService.findAllButton();
        List<String> roles = new ArrayList<String>();
        log.debug(menus);
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        if(menus!=null){
            for(int i=0,j = menus.size();i<j;i++){
                MenuInfo menu =  menus.get(i);
                if(menu == null || "".equals(menu)) {
                    continue;
                }
                roles = this.roleMenuService.getMenuRoleMapByMenuId(menu.getMenuId());
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                for(String role : roles){
                    ConfigAttribute ca = new SecurityConfig(role);
                    atts.add(ca);
                }
                resourceMap.put(menu.getPagePath(), atts);
            }
        }
        if(buttons!= null){
            for(int i=0,j = buttons.size();i<j;i++){
                ButtonInfo button = buttons.get(i);
                if(button.getButtonUrl() == null || "".equals(button.getButtonUrl())){
                    continue;
                }
                roles = this.rightService.getButtonRoleNameByButton(button.getButtonId());
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                for(String role : roles){
                    ConfigAttribute ca = new SecurityConfig(role);
                    atts.add(ca);
                }
                resourceMap.put(button.getButtonUrl(), atts);
            }
        }
    }
    /**
     * <p>Discription:[未使用]</p>
     * @author: 代超
     * @update: 2011-6-6 代超[变更描述]
     */
    public void loadResourceDefine_role(){
        List<String> roles = this.roleDao.findAllRole();
        log.debug(roles);
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        for(String auth : roles){
            //角色名称
            ConfigAttribute ca = new SecurityConfig(auth);
            log.debug(ca.getAttribute());
            List<String> menuName = this.roleMenuService.getRoleMenuMap(ca.getAttribute());
            log.debug(menuName);
            for(String menu : menuName){
                //atts.add(ca);
                // 判断资源文件和权限的对应关系，如果已经存在，要进行增加
                
                if(resourceMap.containsKey(menu)){
                    Collection<ConfigAttribute> value = resourceMap.get(menu);
                    resourceMap.remove(menu);
                    if(!value.contains(ca)){
                        value.add(ca);
                    }
                    resourceMap.put(menu, value);
                }else{
                    if(!atts.contains(ca)){
                        atts.add(ca);
                    }
                    resourceMap.put(menu, atts);
                }
                
                //resourceMap.put(menu, atts);
            }
        }
        //Session session = sessionFactory.openSession();
        /*
        List<String> query=session.createSQLQuery("select role_name from role_info ").list();
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        for (String auth : query){
            ConfigAttribute ca = new SecurityConfig(auth);// "ROLE_ADMIN"
            List<String> query1 = session.createSQLQuery("").list();
            
            for(String res : query1){
                String url = res;
                // 判断资源文件和权限的对应关系，如果已经存在，要进行增加
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(ca);
                    resourceMap.put(url, value);
                }else{
                    atts.add(ca);
                    resourceMap.put(url, atts);
                }
                resourceMap.put(url, atts);
            }
        }
        */
    }
    
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return
     * @author 代超
     * @update 2011-5-17 代超[变更描述]
     */

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * <p>Discription:[当用户点击一个菜单的时候，如果存在在resourceMap资源表的话，则返回访问该资源的角色集合，并且调用decide方法。
     * 如果不存在在resourceMap资源表里面的话，返回null将会不再执行decide方法，而直接放行，使得用户得以访问该资源。
     * 为了保证非法资源不被访问，必须处理这类资源。]</p>
     * @param arg0
     * @return
     * @throws IllegalArgumentException
     * @author 代超
     * @update 2011-5-17 代超[变更描述]
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        //这里才是重点，当用户点击的菜单不在resourceMap这个资源表中的时候，需要处理。
        //如果不在这个资源表里面的话，系统将不允许他访问。如果直接返回null的话，系统将不执行decide方法。
        //所以这里必须要想办法
        while (ite.hasNext()) {
            String resURL = ite.next();
            log.debug("url: "+url);
            log.debug("resURL: "+resURL);
            /*
            if(urlMatcher.pathMatchesUrl(urlMatcher.compile(url), resURL)){
                return resourceMap.get(resURL);
            }
            */
            if(pathMatcher.match(url, resURL)){
                return resourceMap.get(resURL);
            }
        }
        return atts;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param arg0
     * @return
     * @author 代超
     * @update 2011-5-17 代超[变更描述]
     */

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
