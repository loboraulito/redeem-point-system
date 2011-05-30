package com.integral.system.menu.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;

import com.integral.common.BaseAction;
import com.integral.system.menu.service.IMenuService;
import com.integral.util.menu.MenuUtils;

/** 
 * <p>Description: [菜单管理类]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-28
 */
public class MenuAction extends BaseAction implements
ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    
    private IMenuService menuService;
    private MenuUtils menuUtil;

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
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    /**
     * <p>Discription:[查询父菜单下的子菜单]</p>
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public String showChildMenu(){
        String rootId = this.request.getParameter("mainMenuId");
        if(rootId == null || "".equals(rootId)){
            return null;
        }
        List childList = this.menuService.findChildMenuTree(rootId);
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print(Json.toJson(childList));
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[获取有权限的父菜单下的子菜单]</p>
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public String showChildRightMenu(){
        String rootId = this.request.getParameter("mainMenuId");
        if(rootId == null || "".equals(rootId)){
            return null;
        }
        String userName = request.getParameter("userName");
        if(userName == null || "".equals(userName)){
            userName = String.valueOf(request.getSession().getAttribute("userName"));
            if(userName == null || "".equals(userName) || "null".equals(userName)){
                return null;
            }
        }
        
        List childList = this.menuUtil.getMenuTree(this.menuUtil.showChildMenu(userName, rootId));
        
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print(Json.toJson(childList));
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    
    /**
     * <p>Discription:[查询用户有权限访问的主菜单]</p>
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public String showRootMenu(){
        String userName = request.getParameter("userName");
        if(userName == null || "".equals(userName)){
            userName = String.valueOf(request.getSession().getAttribute("userName"));
            if(userName == null || "".equals(userName) || "null".equals(userName)){
                return null;
            }
        }
        List rootMenu = this.menuUtil.showRootMenu(userName);
        if(rootMenu == null){
            return null;
        }
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,rootMenu:"+Json.toJson(rootMenu)+",menuSize:"+rootMenu.size()+"}");
        } catch (IOException e) {
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
