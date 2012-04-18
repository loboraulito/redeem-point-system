package com.integral.applications.account.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.applications.account.service.IAccountAlertService;
import com.integral.applications.account.service.IAccountBaseInfoService;
import com.integral.applications.account.service.IBalanceInfoService;
import com.integral.applications.account.service.IBalanceRightService;
import com.integral.common.action.BaseAction;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class AccountManageAction extends BaseAction {

    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 1L;
    private Log log = LogFactory.getLog(AccountManageAction.class);
    
    private IAccountAlertService accountAlertService;
    private IAccountBaseInfoService accountBaseInfoService;
    private IBalanceInfoService balanceInfoService;
    private IBalanceRightService balanceRightService;
    private DataSourceTransactionManager transactionManager;
    
    public String begin(){
        return SUCCESS;
    }
    
    public IAccountAlertService getAccountAlertService() {
        return accountAlertService;
    }
    public void setAccountAlertService(IAccountAlertService accountAlertService) {
        this.accountAlertService = accountAlertService;
    }
    public IAccountBaseInfoService getAccountBaseInfoService() {
        return accountBaseInfoService;
    }
    public void setAccountBaseInfoService(IAccountBaseInfoService accountBaseInfoService) {
        this.accountBaseInfoService = accountBaseInfoService;
    }
    public IBalanceInfoService getBalanceInfoService() {
        return balanceInfoService;
    }
    public void setBalanceInfoService(IBalanceInfoService balanceInfoService) {
        this.balanceInfoService = balanceInfoService;
    }
    public IBalanceRightService getBalanceRightService() {
        return balanceRightService;
    }
    public void setBalanceRightService(IBalanceRightService balanceRightService) {
        this.balanceRightService = balanceRightService;
    }
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
