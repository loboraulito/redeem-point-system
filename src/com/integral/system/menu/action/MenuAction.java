package com.integral.system.menu.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.common.action.BaseAction;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.service.IButtonService;
import com.integral.system.menu.service.IMenuService;
import com.integral.util.RequestUtil;
import com.integral.util.menu.MenuUtils;
import com.integral.util.spring.security.ResourceDetailsMonitor;

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
    private IButtonService buttonService;
    private MenuUtils menuUtil;
    
    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    /** 当更新了菜单信息后, 需要手动的刷新系统内存, 以保证内存中的菜单权限信息最新 **/
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
     * <p>Discription:[方法功能描述]</p>
     * @return DataSourceTransactionManager transactionManager.
     */
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param transactionManager The transactionManager to set.
     */
    public void setTransactionManager(
            DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
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

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    /**
     * <p>Discription:[菜单管理的入口]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public String begin(){
        return SUCCESS;
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
        
        List childList = this.menuUtil.getMenuTree(this.menuUtil.showChildMenu(userName, rootId), null);
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            //菜单页面,不显示checkBox
            out.print(Json.toJson(childList).replaceAll("checked :false,", ""));
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
    /**
     * <p>Discription:[菜单管理的入口]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-2 代超[变更描述]
     */
    public String menuList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        long menuSize = this.menuService.findAllMenuSize();
        List menuList = this.menuService.findMenuByPageWithParentName(start, limit);
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+menuSize+",menuList:"+Json.toJson(menuList)+"}");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[菜单下拉树]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public String menuComboTree(){
        String rootId = this.request.getParameter("node");
        List list = null;
        if(rootId == null || "".equals(rootId.trim())){
            list = this.menuService.findRootMenu();
        }else{
            list = this.menuService.findChildMenu(rootId);
        }
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print(Json.toJson(this.menuUtil.getMenuTree(list, null)).replaceAll("checked :false,", ""));
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[新增菜单]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public String addMenu(){
        Map requestMap = RequestUtil.getRequestMap(request);
        MenuInfo menu = new MenuInfo();
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            BeanUtils.populate(menu, requestMap);
            //当主键为空字符串时，必须这样操作一次。否则提交的时候将会异常
            if(menu.getMenuId() == null || "".equals(menu.getMenuId().trim())){
                menu.setMenuId(null);
            }
            if(menu.getParentMenuId() == null || "".equals(menu.getParentMenuId().trim())){
                menu.setParentMenuId(null);
            }
            this.menuService.saveOrUpdateMenu(menu);
            //新的菜单或者旧菜单修改完成之后，需要对它的上级菜单做处理。
            String parentId = menu.getParentMenuId();
            if(parentId != null && !"".equals(parentId.trim())){
                MenuInfo parentMenu = this.menuService.findById(parentId);
                if(parentMenu != null && parentMenu.getIsLeave() != null && "1".equals(parentMenu.getIsLeave())){
                    //其父节点不再是子菜单了
                    parentMenu.setIsLeave("0");
                    this.menuService.saveOrUpdateMenu(parentMenu);
                }
            }
            //刷新系统内存
            resourceDetailsMonitor.refresh();
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[删除菜单, 无法删除含有子菜单的菜单. 删除菜单的同时, 它的按钮也应该同时删除]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public String deleteMenu(){
        String menuIds = request.getParameter("menuIds");
        List menuList = new ArrayList();
        List buttonList = new ArrayList();
        boolean hasChild = false;
        if(menuIds != null && !"".equals(menuIds)){
            String [] menuId = menuIds.split(",");
            for(int i=0;i< menuId.length;i++){
                String menu = menuId[i];
                List childMenu = this.menuService.findChildMenu(menu);
                if(childMenu != null && childMenu.size()>0){
                    hasChild = true;
                    break;
                }
                buttonList.addAll(this.buttonService.findButtonByMenuId(menu));
                MenuInfo menuInfo = new MenuInfo();
                menuInfo.setMenuId(menu);
                menuList.add(menuInfo);
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
            if(!hasChild){
                this.menuService.deleteAll(menuList);
                this.buttonService.deleteAll(buttonList);
                out.print("{success:true}");
            }else{
                out.print("{success:false,msg:'您所选菜单包含下级菜单，无法删除！'}");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    
    public String editMenu(){
        Map requestMap = RequestUtil.getRequestMap(request);
        MenuInfo menu = new MenuInfo();
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            BeanUtils.populate(menu, requestMap);
            //当主键为空字符串时，必须这样操作一次。否则提交的时候将会异常
            if(menu.getMenuId() == null || "".equals(menu.getMenuId().trim())){
                menu.setMenuId(null);
            }
            if(menu.getParentMenuId() == null || "".equals(menu.getParentMenuId().trim())){
                menu.setParentMenuId(null);
            }
            this.menuService.saveOrUpdateMenu(menu);
            //新的菜单或者旧菜单修改完成之后，需要对它的上级菜单做处理。
            String parentId = menu.getParentMenuId();
            if(parentId != null && !"".equals(parentId.trim())){
                MenuInfo parentMenu = this.menuService.findById(parentId);
                if(parentMenu != null && parentMenu.getIsLeave() != null && "1".equals(parentMenu.getIsLeave())){
                    //其父节点不再是子菜单了
                    parentMenu.setIsLeave("0");
                    this.menuService.saveOrUpdateMenu(parentMenu);
                }
            }
            //刷新系统内存
            resourceDetailsMonitor.refresh();
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
