package com.integral.system.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.json.Json;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.integral.common.BaseAction;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.service.IMenuService;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.role.service.IRoleService;
import com.integral.system.role.service.IUserRoleService;

/** 
 * <p>Description: [登录成功的处理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 26, 2011
 */
public class LoginSuccessHandler extends BaseAction implements AuthenticationSuccessHandler {
    private Log log = LogFactory.getLog(LoginSuccessHandler.class);
    private IMenuService menuService;
    private IRoleMenuService roleMenuService;
    private IRoleService roleService;
    private IUserRoleService userRoleService;
    
    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @return IUserRoleService userRoleService.
     */
    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @param userRoleService The userRoleService to set.
     */
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @return IRoleService roleService.
     */
    public IRoleService getRoleService() {
        return roleService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @param roleService The roleService to set.
     */
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @return IMenuService menuService.
     */
    public IMenuService getMenuService() {
        return menuService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @param menuService The menuService to set.
     */
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @return IRoleMenuService roleMenuService.
     */
    public IRoleMenuService getRoleMenuService() {
        return roleMenuService;
    }

    /**
     * <p>Discription:[Spring的IOC注入]</p>
     * @param roleMenuService The roleMenuService to set.
     */
    public void setRoleMenuService(IRoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * @author cdai
     * @update May 26, 2011 cdai[变更描述]
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        PrintWriter out = super.getPrintWriter(request, response);
        //获取登录用户信息
        Object principal = authentication.getPrincipal();
        String userName = "";
        if(principal instanceof UserDetails){
            userName = ((UserDetails)principal).getUsername();
        }else{
            userName = principal.toString();
        }
        
        List menus = showRootMenu(userName);
        if(menus == null){
            menus = new ArrayList();
        }
        request.getSession().setAttribute("userName", userName);
        out.print("{success:true,msg:'登录成功',userName:'"+userName+"',roleName:''}");
        out.flush();
        out.close();
        return;
    }
    /**
     * <p>Discription:[查询用户有权限访问的根菜单]</p>
     * @param userName
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    private List showRootMenu(String userName){
        List rootMenus = new ArrayList();
        if(userName == null || "".equals(userName.trim())){
            return null;
        }
        //查询用户的角色
        List userRoles = this.userRoleService.findRoleByUserName(userName);
        if(userRoles == null || userRoles.size()<1){
            return null;
        }
        String roleId = (String) userRoles.get(0);
        //查询角色能访问的菜单
        List roleMenus = this.roleMenuService.getRootMenuMap(roleId);
        if(roleMenus == null || roleMenus.size()<1){
            return null;
        }
        for(int i=0,j=roleMenus.size();i<j;i++){
            Object[] obj = (Object[]) roleMenus.get(i);
            MenuInfo menuInfo = (MenuInfo) obj[0];
            rootMenus.add(menuInfo);
        }
        
        return rootMenus;
    }
}
