package com.integral.exchange.gifts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
import com.integral.exchange.gifts.service.IGiftService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public class GiftAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IGiftService giftService;

    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IGiftService giftService.
     */
    public IGiftService getGiftService() {
        return giftService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param giftService The giftService to set.
     */
    public void setGiftService(IGiftService giftService) {
        this.giftService = giftService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return DataSourceTransactionManager transactionManager.
     */
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param transactionManager The transactionManager to set.
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param request The request to set.
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param response The response to set.
     */
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    /**
     * <p>Discription:[礼品管理页面]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
}
