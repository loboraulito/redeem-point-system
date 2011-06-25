package com.integral.system.menu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
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

}
