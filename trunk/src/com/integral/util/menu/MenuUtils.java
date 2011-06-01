package com.integral.util.menu;

import java.util.ArrayList;
import java.util.List;

import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.bean.MenuTree;
import com.integral.system.menu.service.IMenuService;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.role.service.IRoleService;
import com.integral.system.role.service.IUserRoleService;

/** 
 * <p>Description: [菜单工具类]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-29
 */
public class MenuUtils {
    private IMenuService menuService;
    private IRoleMenuService roleMenuService;
    private IRoleService roleService;
    private IUserRoleService userRoleService;
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
     * @return IUserRoleService userRoleService.
     */
    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }
    /**
     * <p>Discription:[方法功能描述]</p>
     * @param userRoleService The userRoleService to set.
     */
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    
    /**
     * <p>Discription:[查询用户有权限访问的根菜单]</p>
     * @param userName
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    public List showRootMenu(String userName){
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
    /**
     * <p>Discription:[查询用户有权限访问的主菜单的子菜单]</p>
     * @param userName
     * @param rootMenu
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List showChildMenu(String userName, String rootMenu){
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
        List roleMenus = this.roleMenuService.getChildMenuMap(roleId, rootMenu);
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
    /**
     * <p>Discription:[将菜单信息组装成树形结构]</p>
     * @param menus
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List getMenuTree(List menus){
        List menuTrees = new ArrayList();
        if(menus == null){
            return menuTrees;
        }
        for(int i=0,j = menus.size();i<j;i++){
            MenuInfo menu = (MenuInfo) menus.get(i);
            MenuTree menuTree = new MenuTree();
            
            menuTree.setId(menu.getMenuId());
            menuTree.setText(menu.getMenuName());
            menuTree.setQtip(menu.getMenuName());
            
            if("0".equals(menu.getIsLeave())){//非叶子节点
                menuTree.setCls("folder");
                menuTree.setHref(null);
                menuTree.setLeaf(false);
                menuTree.setExpandable(true);
            }else{ //叶子节点
                menuTree.setCls("file");
                menuTree.setHref(null);
                menuTree.setLeaf(true);
                menuTree.setExpandable(false);
                // TODO 链接的目标位置
                //menuTree.setHrefTarget("mainFrame");
                menuTree.setHrefComment(menu.getPagePath());
            }
            menuTree.setTarget(false);
            menuTree.setSingleClickExpand(true);
            menuTrees.add(menuTree);
        }
        return menuTrees;
    }
}
