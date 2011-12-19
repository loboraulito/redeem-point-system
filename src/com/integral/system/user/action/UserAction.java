package com.integral.system.user.action;

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
import com.integral.system.role.bean.RoleInfo;
import com.integral.system.role.bean.UserRole;
import com.integral.system.role.service.IRoleService;
import com.integral.system.role.service.IUserRoleService;
import com.integral.system.user.bean.UserInfo;
import com.integral.system.user.service.IUserService;
import com.integral.util.CipherUtil;
import com.integral.util.RequestUtil;
import com.integral.util.spring.security.ResourceDetailsMonitor;

public class UserAction extends BaseAction implements ServletRequestAware, ServletResponseAware{
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IUserService userService;
    private IUserRoleService userRoleService;
    private IRoleService roleService;
    /**
     * 系统配置的默认用户注册成功之后的角色
     */
    private String systemRoleName;
    
    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    
    /** 当更新了角色信息后, 需要手动的刷新系统内存, 以保证内存中的菜单角色信息最新 **/
    private ResourceDetailsMonitor resourceDetailsMonitor;
    
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
     * @return the userRoleService
     */
    public IUserRoleService getUserRoleService() {
        return userRoleService;
    }

    /**
     * @param userRoleService the userRoleService to set
     */
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * @return the transactionManager
     */
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * @param transactionManager the transactionManager to set
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the resourceDetailsMonitor
     */
    public ResourceDetailsMonitor getResourceDetailsMonitor() {
        return resourceDetailsMonitor;
    }

    /**
     * @param resourceDetailsMonitor the resourceDetailsMonitor to set
     */
    public void setResourceDetailsMonitor(ResourceDetailsMonitor resourceDetailsMonitor) {
        this.resourceDetailsMonitor = resourceDetailsMonitor;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IUserService userService.
     */
    public IUserService getUserService() {
        return userService;
    }
    
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return String systemRoleName.
     */
    public String getSystemRoleName() {
        return systemRoleName;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param systemRoleName The systemRoleName to set.
     */
    public void setSystemRoleName(String systemRoleName) {
        this.systemRoleName = systemRoleName;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param userService The userService to set.
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
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
     * <p>Discription:[用户管理列表页面]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public String userManageList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        
        long userSize = this.userService.findUserSize();
        List userList = this.userService.findUserByPageWithProtect(start, limit);
        
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+userSize+",userList:"+Json.toJson(userList)+"}");
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
     * <p>Discription:[用户管理的入口]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[删除用户信息,需要同时删除用户的角色信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public String deleteUser(){
        String userIdList = request.getParameter("userList");
        String userNameList = request.getParameter("userNameList");
        String [] userIds = userIdList.split(",");
        String [] userNames = userNameList.split(",");
        List userList = new ArrayList();
        if(userIds != null){
            for(String id : userIds){
                UserInfo user = new UserInfo();
                user.setUserId(id);
                userList.add(user);
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
            //删除用户信息
            this.userService.deleteAll(userList);
            //删除用户的角色信息
            this.userRoleService.deleteByUser(userNames);
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[修改用户信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public String editUser(){
        Map requestMap = RequestUtil.getRequestMap(request);
        String userId = request.getParameter("userId");
        UserInfo user = null;
        String passWord = "";
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            if(userId == null || "".equals(userId.trim())){
                user = new UserInfo();
                BeanUtils.populate(user, requestMap);
                user.setUserId(null);
                passWord = CipherUtil.generatePassword("0000",user.getUserName());
                user.setPassword(passWord);
            }else{
                user = this.userService.findById(userId);
                if(user != null){
                    passWord = user.getPassword();
                    BeanUtils.populate(user, requestMap);
                    user.setPassword(passWord);
                }
            }
            if(user != null){
                this.userService.saveOrUpdate(user);
            }
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[增加用户信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-3 代超[变更描述]
     */
    public String addUser(){
        Map requestMap = RequestUtil.getRequestMap(request);
        UserInfo user = new UserInfo();
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            BeanUtils.populate(user, requestMap);
            if(user.getUserId() == null || "".equals(user.getUserId().trim())){
                user.setUserId(null);
                //为新增的用户设置初始密码
                if(user.getPassword() == null || "".equals(user.getPassword().trim())){
                    //设置初始密码为0000
                    String passWord = CipherUtil.generatePassword("0000",user.getUserName());
                    user.setPassword(passWord);
                }
            }
            List l = this.userService.getUserByName(user.getUserName());
            if(l!=null && l.size()>0){
                out.print("{success:false, msg:'您输入的用户名已被别人使用，请更换用户名！'}");
            }else{
                this.userService.saveOrUpdate(user);
                out.print("{success:true}");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * 校验用户名是否重复
     * @return
     */
    public String validateUserName(){
        String userName = "";
        String field = request.getParameter("field");
        String value = request.getParameter("value");
        if(field != null && "userName".equals(field)){
            userName = value;
        }
        List list = this.userService.getUserByName(userName);
        String bool = "true";
        String reason = "该用户名可以使用";
        if(list == null || list.size()<1){
            //验证通过
            bool = "true";
        }else{
            //验证失败
            bool = "false";
            reason = "该用户名已被使用";
        }
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            out.print("{success:true,valid:"+bool+",reason:'"+reason+"'}");
        }catch(Exception e){
            out.print("{success:true,valid:false,reason:'数据异常'}");
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[系统用户注册]</p>
     * @return
     * @author: 代超
     * @update: 2011-7-9 代超[变更描述]
     */
    public String registerUser(){
        Map requestMap = RequestUtil.getRequestMap(request);
        UserInfo user = new UserInfo();
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        
        String msg = "";
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            BeanUtils.populate(user, requestMap);
            if(user.getUserId() == null || "".equals(user.getUserId().trim())){
                user.setUserId(null);
                //为新增的用户设置初始密码
                if(user.getPassword() == null || "".equals(user.getPassword().trim())){
                    //设置初始密码为0000
                    String passWord = CipherUtil.generatePassword("0000",user.getUserName());
                    user.setPassword(passWord);
                }else{
                    String passWord = CipherUtil.generatePassword(user.getPassword(), user.getUserName());
                    user.setPassword(passWord);
                }
            }
            List l = this.userService.getUserByName(user.getUserName());
            if(l!=null && l.size()>0){
                out.print("{success:false, msg:'您输入的用户名已被别人使用，请更换用户名！'}");
            }else{
                //构建用户角色
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserName());
                String roleId = "";
                if(this.systemRoleName != null && !"".equals(this.systemRoleName)){
                    List roles = this.roleService.findRoleByName(systemRoleName);
                    if(roles!=null && roles.size()>0){
                        RoleInfo role = (RoleInfo) roles.get(0);
                        roleId = role.getRoleId();
                        msg = "您已成功注册，当前权限是：["+systemRoleName+"]";
                    }else{
                        msg = "您已成功注册，但是尚未分配任何权限，请联系管理员为您分配权限！";
                    }
                }else{
                    msg = "您已成功注册，但是尚未分配任何权限，请联系管理员为您分配权限！";
                }
                userRole.setRoleId(roleId);
                
                this.userService.saveOrUpdate(user);
                this.userRoleService.saveOrUpdate(userRole);
                out.print("{success:true,msg:'"+msg+"'}");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false,msg:'系统异常，请联系管理员！'}");
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
