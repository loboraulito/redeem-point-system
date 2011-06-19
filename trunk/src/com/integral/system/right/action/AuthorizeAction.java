/**
 * 
 */
package com.integral.system.right.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;

import com.integral.common.action.BaseAction;
import com.integral.system.menu.service.IMenuService;
import com.integral.system.right.service.IAuthorizeService;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.role.service.IRoleService;
import com.integral.system.user.service.IUserService;
import com.integral.util.menu.MenuUtils;

/**
 * @author cdai
 * 权限授权
 */
public class AuthorizeAction extends BaseAction implements ServletRequestAware, ServletResponseAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private IAuthorizeService authorizeService;
	private IMenuService menuService;
	private MenuUtils menuUtil;
	private IRoleMenuService roleMenuService;
	private IUserService userService;
	private IRoleService roleService;
	

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IUserService userService.
     */
    public IUserService getUserService() {
        return userService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param userService The userService to set.
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRoleService roleService.
     */
    public IRoleService getRoleService() {
        return roleService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param roleService The roleService to set.
     */
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
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
     * @return IMenuService menuService.
     */
    public IMenuService getMenuService() {
        return menuService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param menuService The menuService to set.
     */
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return MenuUtils menuUtil.
     */
    public MenuUtils getMenuUtil() {
        return menuUtil;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param menuUtil The menuUtil to set.
     */
    public void setMenuUtil(MenuUtils menuUtil) {
        this.menuUtil = menuUtil;
    }

    /**
	 * @return the authorizeService
	 */
	public IAuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	/**
	 * @param authorizeService the authorizeService to set
	 */
	public void setAuthorizeService(IAuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String begin(){
		return SUCCESS;
	}
	
	public String authorizeList(){
	    //授权界面上的三个列表标志
	    String flag = request.getParameter("flag");
	    if("authorize_user".equals(flag)){
	        return authorizeUser();
	    }else if("authorize_menu".equals(flag)){
	        return authorizeMenu();
	    }else if("authorize_role".equals(flag)){
	        
	    }
        return null;
	}
	
	public String authorizeUser(){
		String roleId = request.getParameter("roleId");
		int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        
        String sql = "from UserInfo model, UserRole role where model.userName = role.userId and role.roleId = ? ";
        
        
		return null;
	}
	
	public String authorizeMenu(){
        //父级菜单id
        String rootId = request.getParameter("rootId");
        List list = null;
        if(rootId == null || "".equals(rootId) || "roleMenuTree".equals(rootId)){
            //查询顶级菜单
            list = this.authorizeService.showAuthorizeMenuInfo(null);
        }else{
            //查询rootId下的第一级子菜单
            list = this.authorizeService.showAuthorizeMenuInfo(rootId);
        }
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            //out.print("{success:true,totalCount:"+list.size()+",data:"+Json.toJson(list)+"}");
            out.print(Json.toJson(list));
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
		return null;
	}

}
