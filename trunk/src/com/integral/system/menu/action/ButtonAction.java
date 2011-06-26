package com.integral.system.menu.action;

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
import com.integral.system.menu.bean.ButtonInfo;
import com.integral.system.menu.service.IButtonService;
import com.integral.system.menu.service.IMenuService;
import com.integral.util.menu.MenuUtils;
import com.integral.util.spring.security.ResourceDetailsMonitor;

public class ButtonAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IMenuService menuService;
    private IButtonService buttonService;
    private MenuUtils menuUtil;
    
    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    /** 当更新了菜单信息后, 需要手动的刷新系统内存, 以保证内存中的菜单权限信息最新 **/
    private ResourceDetailsMonitor resourceDetailsMonitor;
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
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
     * <p>Discription:[按钮管理]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[按钮管理列表]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-26 代超[变更描述]
     */
    public String buttonList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        
        long buttonSize = this.buttonService.findAllButtonSize();
        List buttonList = this.buttonService.findButtonByPageWithMenu(start, limit);
        
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+buttonSize+",buttonList:"+Json.toJson(buttonList)+"}");
        }
        catch (IOException e) {
            e.printStackTrace();
            if("no right".equals(e.getMessage())){
                out.print("{success:false, msg:'您无权访问本页面！'}");
            }
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[删除按钮信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-26 代超[变更描述]
     */
    public String deleteButton(){
        String buttonIds = request.getParameter("buttonIds");
        List list = new ArrayList();
        if(buttonIds != null && !"".equals(buttonIds)){
            String [] buttonId = buttonIds.split(",");
            for(int i=0;i<buttonId.length;i++){
                ButtonInfo button = new ButtonInfo();
                button.setButtonId(buttonId[i]);
                list.add(button);
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
            this.buttonService.deleteAll(list);
            out.print("{success:true}");
        }catch(Exception e){
            status.setRollbackOnly();
            out.print("{success:false}");
        }finally{
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
}
