/**
 * 
 */
package com.integral.system.right.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.common.action.BaseAction;
import com.integral.system.menu.service.IMenuService;
import com.integral.system.right.bean.RightInfo;
import com.integral.system.right.service.IAuthorizeService;
import com.integral.system.role.bean.RoleMenuInfo;
import com.integral.system.role.bean.UserRole;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.role.service.IRoleService;
import com.integral.system.role.service.IUserRoleService;
import com.integral.system.user.service.IUserService;
import com.integral.util.menu.MenuUtils;
import com.integral.util.spring.security.ResourceDetailsMonitor;

/**
 * @author cdai 权限授权
 */
public class AuthorizeAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;

    private IAuthorizeService authorizeService;
    private IMenuService menuService;
    private MenuUtils menuUtil;
    private IRoleMenuService roleMenuService;
    private IUserService userService;
    private IRoleService roleService;
    private IUserRoleService userRoleService;
    

    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    
    /** 当更新了访问权限信息后, 需要手动的刷新系统内存, 以保证内存中的菜单权限信息最新 **/
    private ResourceDetailsMonitor resourceDetailsMonitor;
    

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return ResourceDetailsMonitor resourceDetailsMonitor.
     */
    public ResourceDetailsMonitor getResourceDetailsMonitor() {
        return resourceDetailsMonitor;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param resourceDetailsMonitor The resourceDetailsMonitor to set.
     */
    public void setResourceDetailsMonitor(
            ResourceDetailsMonitor resourceDetailsMonitor) {
        this.resourceDetailsMonitor = resourceDetailsMonitor;
    }

    /**
     * @return the transactionManager
     */
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
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
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IUserService userService.
     */
    public IUserService getUserService() {
        return userService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param userService
     *            The userService to set.
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IRoleService roleService.
     */
    public IRoleService getRoleService() {
        return roleService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param roleService
     *            The roleService to set.
     */
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IRoleMenuService roleMenuService.
     */
    public IRoleMenuService getRoleMenuService() {
        return roleMenuService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param roleMenuService
     *            The roleMenuService to set.
     */
    public void setRoleMenuService(IRoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IMenuService menuService.
     */
    public IMenuService getMenuService() {
        return menuService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param menuService
     *            The menuService to set.
     */
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return MenuUtils menuUtil.
     */
    public MenuUtils getMenuUtil() {
        return menuUtil;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param menuUtil
     *            The menuUtil to set.
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
     * @param authorizeService
     *            the authorizeService to set
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

    public String begin() {
        return SUCCESS;
    }

    /**
     * <p>
     * Discription:[授权页面]
     * </p>
     * 
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public String authorizeList() {
        // 授权界面上的三个列表标志
        String flag = request.getParameter("flag");
        if ("authorize_user".equals(flag)) {
            // return authorizeUser();
        } else if ("authorize_menu".equals(flag)) {
            // return authorizeMenu();
        } else if ("authorize_role".equals(flag)) {
            // return authorizeRole();
        }
        return null;
    }

    /**
     * <p>
     * Discription:[显示角色用户成员]
     * </p>
     * 
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public String showAuthorizeUser() {
        String roleId = request.getParameter("roleId");
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        Long userListSize = this.authorizeService.showAuthorzieUser(new String[] { roleId });
        List userList = this.authorizeService.showAuthorzieUser(start, limit, new String[] { roleId });
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:" + userListSize + ",userList:" + Json.toJson(userList) + "}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return null;
    }

    /**
     * <p>
     * 第一种方案使用方法： <code>
     * list = this.authorizeService.showAuthorzieMenu(roleId, rootId);
     * out.print(Json.toJson(list));
	 * </code>
     * </p>
     * <p>
     * 第二种方案使用方法： <code>
     * if(roleId == null || "".equals(roleId)){
     * 	list = this.authorizeService.getChildList(rootId);
     * }else{
     * 	list = this.authorizeService.showAuthorzieMenu(roleId);
     * }
     * out.print(Json.toJson(list));
	 * </code>
     * </p>
     * <p>
     * Discription:[角色菜单]
     * </p>
     * 
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public String showAuthorizeMenu() {
        String roleId = request.getParameter("roleId");
        // 父级菜单id
        String rootId = request.getParameter("rootId");

        PrintWriter out = null;
        // 采用第一种方案，只需要下面一句即可
        // list = this.authorizeService.showAuthorzieMenu(roleId, rootId);
        // 采用第二种方案，需要下面这种方法
        List list = null;
        try {
            out = super.getPrintWriter(request, response);
            if (roleId == null || "".equals(roleId)) {
                list = this.authorizeService.getChildList(rootId);
                out.print(Json.toJson(list));
            } else {
                list = this.authorizeService.showAuthorzieMenu(roleId);
                out.print("{success:true,menus:" + Json.toJson(list) + "}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return null;
    }

    /**
     * <p>
     * Discription:[系统角色]
     * </p>
     * 
     * @return
     * @author: 代超
     * @update: 2011-6-19 代超[变更描述]
     */
    public String showAuthorizeRole() {
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);

        long roleSize = this.roleService.findRoleSize();
        List roleList = this.roleService.findRoleListByPage(start, limit);

        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:" + roleSize + ",roleList:" + Json.toJson(roleList) + "}");
        } catch (IOException e) {
            if("no right".equals(e.getMessage())){
                out.print("{success:false, msg:'noRight'}");
            }else{
                out.print("{success:false}");
            }
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return null;
    }

    /**
     * 根据角色权限菜单
     * 
     * @return
     */
    public String updateAuthorizeRoleMenu() {
        // 角色ID
        String roleId = request.getParameter("roleId");
        // 权限菜单ID
        String rightId = request.getParameter("rightId");
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            if(roleId == null || "".equals(roleId)){
                throw new Exception("roleId is null");
            }
            //第一步：删除该角色已分配的菜单，按钮权限
            List menuRight = this.authorizeService.findAuthorizeMenu(roleId);
            List buttonRight = this.authorizeService.findAuthorizeButton(roleId);
            this.roleMenuService.deleteAll(menuRight);
            this.authorizeService.deleteAll(buttonRight);
            //第二步：解析分配的权限菜单，按钮
            //如果为空，则不给该角色分配新的菜单
            if(rightId!=null && !"".equals(rightId)){
                List buttons = new ArrayList();
                List menus = new ArrayList();
                String [] right = rightId.split(",");
                for(String r : right){
                    RightInfo rightInfo = new RightInfo();
                    RoleMenuInfo menuInfo = new RoleMenuInfo();
                    if(r.indexOf("button")>=0){
                        //按钮权限
                        rightInfo.setButtonId(r.replace("button_", ""));
                        rightInfo.setRoleId(roleId);
                        buttons.add(rightInfo);
                    }else if(r.indexOf("menu")>=0){
                        //菜单权限
                        menuInfo.setMenuId(r.replace("menu_", ""));
                        menuInfo.setRoleId(roleId);
                        menus.add(menuInfo);
                    }
                }
                //存储数据
                this.roleMenuService.saveOrUpdateAll(menus);
                this.authorizeService.saveOrUpdateAll(buttons);
            }
            out.print("{success:true}");
        } catch (Exception e) {
            status.setRollbackOnly();
            out.print("{success:false}");
        } finally {
            transactionManager.commit(status);
            //刷新系统内存
            resourceDetailsMonitor.refresh();
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * 删除用户角色信息
     * <p>Discription:[方法功能描述]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-6 代超[变更描述]
     */
    public String authorizeUserDelete(){
        String userNameList = request.getParameter("userNameList");
        String [] userNames = userNameList.split(",");
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            //删除用户的角色信息
            this.userRoleService.deleteByUser(userNames);
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            this.transactionManager.commit(status);
            this.resourceDetailsMonitor.refresh();
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * 添加权限用户
     * @return
     */
    public String authorizeUserAdd(){
        String destRoleId = request.getParameter("roleId");
        String users = request.getParameter("userList");
        String []user_role = users.split(",");
        List newUser = new ArrayList();
        StringBuffer sb = new StringBuffer(destRoleId);
        
        if(user_role!=null && user_role.length>0){
            for(int i=0;i<user_role.length;i++){
                String []user = user_role[i].split("_");
                if(user !=null){
                    if(user.length<2){
                        //当前选中的用户赞未分配角色
                        //查询该用户是否已经存在在数据库
                        UserRole userRole = null;
                        
                        List existRole = this.userRoleService.findRoleByUserID(user[0]);
                        if(existRole != null && existRole.size() > 0){
                            userRole = (UserRole) existRole.get(0);
                        }else{
                            userRole = new UserRole();
                            userRole.setUserId(user[0]);
                        }
                        userRole.setRoleId(destRoleId);
                        newUser.add(userRole);
                    }else{
                        //当前用户已有角色
                        sb.append(",").append(user[0]);
                    }
                }
            }
        }
        
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            this.userRoleService.saveOrUpdateAll(newUser);
            this.userRoleService.updateByUser(sb.toString().split(","));
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            this.transactionManager.commit(status);
            this.resourceDetailsMonitor.refresh();
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * 查询所有的用户信息，包括角色信息
     * @return
     */
    public String findAllAuthorizeUserAndRole(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        List auths = this.authorizeService.findAllAuthorizeUserAndRole(start, limit);
        long allUsers = this.userService.findUserSize();
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+allUsers+",userList:"+Json.toJson(auths)+"}");
        }catch(Exception e){
            out.print("{success:false}");
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
