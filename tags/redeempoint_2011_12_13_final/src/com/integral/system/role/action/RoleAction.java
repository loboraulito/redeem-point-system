package com.integral.system.role.action;

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
import com.integral.system.right.service.IRightService;
import com.integral.system.role.bean.RoleInfo;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.role.service.IRoleService;
import com.integral.system.role.service.IUserRoleService;
import com.integral.util.RequestUtil;
import com.integral.util.spring.security.ResourceDetailsMonitor;

/** 
 * <p>Description: [角色管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-12
 */
public class RoleAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IRoleService roleService;
    private IRoleMenuService roleMenuService;
    private IUserRoleService userRoleService;
    private IRightService rightService;
    
    
    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    
    /** 当更新了角色信息后, 需要手动的刷新系统内存, 以保证内存中的菜单角色信息最新 **/
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

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    /**
     * <p>Discription:[角色管理]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    
    /**
     * <p>Discription:[角色列表]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public String roleManageList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        
        long roleSize = this.roleService.findRoleSize();
        List roleList = this.roleService.findRoleListByPage(start, limit);
        
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+roleSize+",roleList:"+Json.toJson(roleList)+"}");
        }
        catch (IOException e) {
            e.printStackTrace();
            out.print("{success:false,msg:'noRoght'}");
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[新增角色信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public String addRole(){
        Map requestMap = RequestUtil.getRequestMap(request);
        RoleInfo role = new RoleInfo();
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            BeanUtils.populate(role, requestMap);
            if(role.getRoleId() == null || "".equals(role.getRoleId().trim())){
                role.setRoleId(null);
            }
            this.roleService.saveOrUpdate(role);
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            //this.resourceDetailsMonitor.refresh();
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[修改角色信息, 方法与新增一致]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public String editRole(){
        Map requestMap = RequestUtil.getRequestMap(request);
        RoleInfo role = new RoleInfo();
        
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            BeanUtils.populate(role, requestMap);
            if(role.getRoleId() == null || "".equals(role.getRoleId().trim())){
                role.setRoleId(null);
            }
            this.roleService.saveOrUpdate(role);
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            //this.resourceDetailsMonitor.refresh();
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[删除选中的角色信息,应该需要同时删除该角色对应的用户角色信息以及角色菜单信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public String deleteRole(){
        String roles = request.getParameter("roleList");
        List roleList = new ArrayList();
        String [] roleArray = null;
        if(roles != null && !"".equals(roles.trim())){
            roleArray = roles.split(",");
            for(int i=0;i<roleArray.length;i++){
                RoleInfo role = new RoleInfo();
                role.setRoleId(roleArray[i]);
                roleList.add(role);
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
            this.roleService.deleteAll(roleList);
            this.userRoleService.deleteByRole(roleArray);
            this.roleMenuService.deleteByRoleId(roleArray);
            this.rightService.deleteByRole(roleArray);
            out.print("{success:true}");
        }catch(Exception e){
            e.printStackTrace();
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            this.resourceDetailsMonitor.refresh();
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
