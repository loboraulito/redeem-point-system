package com.integral.applications.account.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.integral.applications.account.bean.AccountBaseInfo;
import com.integral.applications.account.bean.AccountCardInfo;
import com.integral.applications.account.service.IAccountAlertService;
import com.integral.applications.account.service.IAccountBaseInfoService;
import com.integral.applications.account.service.IAccountCardInfoService;
import com.integral.applications.account.service.IBalanceInfoService;
import com.integral.applications.account.service.IBalanceRightService;
import com.integral.common.action.BaseAction;
import com.integral.util.RequestUtil;
import com.integral.util.Tools;

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
    
    private IAccountAlertService accountAlertService;
    private IAccountBaseInfoService accountBaseInfoService;
    private IBalanceInfoService balanceInfoService;
    private IBalanceRightService balanceRightService;
    /**
     * 账户信息
     */
    private IAccountCardInfoService accountCardService;
    private DataSourceTransactionManager transactionManager;
    
    private int start;
    private int limit;
    private String userName;
    private String accountListId;
    private String balanceListId;
    private AccountBaseInfo account;
    private AccountCardInfo card;
    
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[账目明细列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String accountList(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", this.userName);
        List accountInfoList = this.accountBaseInfoService.queryPage(start, limit, paramMap);
        int accountSize = this.accountBaseInfoService.queryPageSize(paramMap);
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            out = super.getPrintWriter();
            resultMap.put("success", true);
            resultMap.put("account", accountInfoList);
            resultMap.put("totalCount", accountSize);
        }
        catch (IOException e) {
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码：" + e.getMessage());
            LOG.error(e.getMessage());
        }finally{
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[添加个人账目信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String addAccountInfo(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            if(this.account == null){
                throw new Exception("您所提交的信息不完整，请检查！");
            }else{
                //默认不删除
                account.setDeletetag("0");
                //收入减去支出
                account.setAccountmargin(NumberUtils.toDouble((new BigDecimal(account.getAccountenter() + "").add(new BigDecimal(account.getAccountout() + "").negate())).toString(), 0.0));
                account.setBaseyear(Tools.dateToString(account.getBasedate(), "yyyy"));
                account.setBasemonth(Tools.dateToString(account.getBasedate(), "yyyy-MM"));
                
                //获取账目相关账户信息
                AccountCardInfo cInfo = this.accountCardService.findById(account.getAccountcard());
                if(cInfo == null){
                    throw new Exception("您所选账户不存在！");
                }
                cInfo.setCardBalance(NumberUtils.toDouble((new BigDecimal(cInfo.getCardBalance() + "").add(new BigDecimal(account.getAccountmargin() + ""))).toString(), 0.0));
                //新增账目信息
                this.accountBaseInfoService.save(account);
                //更新账户余额
                this.accountCardService.update(cInfo);
                resultMap.put("success", true);
                resultMap.put("msg", "您的账目信息已经成功保存！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[修改账目信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String editAccountInfo(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            if(this.account == null){
                throw new Exception("您所提交的信息不完整，请检查！");
            }else{
                //收入减去支出
                account.setAccountmargin(NumberUtils.toDouble((new BigDecimal(account.getAccountenter() + "").add(new BigDecimal(account.getAccountout() + "").negate())).toString(), 0.0));
                account.setBaseyear(Tools.dateToString(account.getBasedate(), "yyyy"));
                account.setBasemonth(Tools.dateToString(account.getBasedate(), "yyyy-MM"));
                
                //获取账目相关账户信息
                AccountCardInfo cInfo = this.accountCardService.findById(account.getAccountcard());
                if(cInfo == null){
                    throw new Exception("您所选账户不存在！");
                }
                cInfo.setCardBalance(NumberUtils.toDouble((new BigDecimal(cInfo.getCardBalance() + "").add(new BigDecimal(account.getAccountmargin() + ""))).toString(), 0.0));
                //新增账目信息
                this.accountBaseInfoService.update(account);
                //更新账户余额
                this.accountCardService.update(cInfo);
                resultMap.put("success", true);
                resultMap.put("msg", "您的账目信息已经成功保存！");
            }
        }catch(Exception e){
            LOG.error(e.getMessage());
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[删除账目信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String deleteAccountInfo(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            if(this.balanceListId == null || "".equals(balanceListId.trim())){
                throw new Exception("您未选择任何账目信息！");
            }
            String [] listId = this.balanceListId.split(",");
            if(listId == null || listId.length <1){
                throw new Exception("您未选择任何账目信息！");
            }
            //删除账目之前应该将该账目对应的金额返回给对应的账户
            List<AccountBaseInfo> list = this.accountBaseInfoService.queryListByIds(listId);
            if(list == null || list.isEmpty()){
                throw new Exception("您所选账目不存在！");
            }
            //返还金额
            this.accountCardService.backAccount(list);
            //删除账目信息
            this.accountBaseInfoService.deleteAll(list);
            resultMap.put("success", true);
            resultMap.put("msg", "您所选账目信息已成功删除！");
        }catch(Exception e){
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    
    //==========================个人账户=========================================
    
    /**
     * <p>Discription:[个人账户列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String myAccountList(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userName", this.userName);
        List<AccountCardInfo> cardList = this.accountCardService.findInstanceList(paramMap, start, limit);
        int cardSize = this.accountCardService.findInstanceListSize(paramMap);
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            out = super.getPrintWriter();
            resultMap.put("success", true);
            resultMap.put("accountCard", cardList);
            resultMap.put("totalCount", cardSize);
        }catch(Exception e){
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+ e.getMessage());
        }finally{
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[新增账户信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String addAccountCard(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            if(this.card == null){
                throw new Exception("您所提交的信息不完整，请检查！");
            }else{
                this.accountCardService.save(card);
                resultMap.put("success", true);
                resultMap.put("msg", "您的账户信息已经成功保存！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[修改账户信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String editAccountCard(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            if(this.card == null){
                throw new Exception("您所提交的信息不完整，请检查！");
            }else if(this.card.getAccountId() == null || "".equals(this.card.getAccountId().trim())){
                this.accountCardService.save(card);
            }else{
                this.accountCardService.update(card);
                resultMap.put("success", true);
                resultMap.put("msg", "您的账户信息已经成功保存！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[删除账户信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String deleteAccountCard(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 开始事务
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            if(this.accountListId == null || "".equals(this.accountListId.trim())){
                throw new Exception("您没有选择要删除的账户信息！");
            }else{
                String [] accountListArray = this.accountListId.split(",");
                if(accountListArray == null || accountListArray.length < 1){
                    throw new Exception("您没有选择要删除的账户信息！");
                }else{
                    List<AccountCardInfo> cardList = new ArrayList<AccountCardInfo>(accountListArray.length);
                    for(String accountId : accountListArray){
                        AccountCardInfo accountCard = new AccountCardInfo(accountId);
                        cardList.add(accountCard);
                    }
                    this.accountCardService.deleteAll(cardList);
                    resultMap.put("success", true);
                    resultMap.put("msg", "您所选账户信息已成功删除！");
                }
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[账户转账]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String transferAccount(){
        Map<String, Object> paramMap = RequestUtil.getRequestMap(ServletActionContext.getRequest());
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 开始事务
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        try{
            out = super.getPrintWriter();
            AccountCardInfo tranOutCard = this.accountCardService.findById(String.valueOf(paramMap.get("tranOutAccount")));
            AccountCardInfo tranInCard = this.accountCardService.findById(String.valueOf(paramMap.get("tranInAccount")));
            if(tranOutCard == null || tranInCard == null){
                throw new Exception("您所选账户不存在！");
            }
            if(tranOutCard.getAccountId().equals(tranInCard.getAccountId())){
                throw new Exception("请选择两个不同账户！");
            }
            if(NumberUtils.toDouble(String.valueOf(paramMap.get("tranAmount")), 0.0) <= 0){
                throw new Exception("转账金额必须大于 0 ！");
            }
            //转账
            this.accountCardService.transferAccount(tranOutCard, tranInCard, NumberUtils.toDouble(String.valueOf(paramMap.get("tranAmount")), 0.0), String.valueOf(paramMap.get("comment")));
            //记账(两笔-转出，转入)
            //支出
            this.accountBaseInfoService.chargeAccount(NumberUtils.toDouble(String.valueOf(paramMap.get("tranAmount")), 0.0), 0.0, "个人账户转出", tranOutCard.getAccountId(), userName);
            this.accountBaseInfoService.chargeAccount(0.0, NumberUtils.toDouble(String.valueOf(paramMap.get("tranAmount")), 0.0), "个人账户转入", tranInCard.getAccountId(), userName);
            
            resultMap.put("success", true);
            resultMap.put("msg", "转账成功！");
        }catch(Exception e){
            e.printStackTrace();
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        
        return null;
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
    public IAccountCardInfoService getAccountCardService() {
        return accountCardService;
    }
    public void setAccountCardService(IAccountCardInfoService accountCardService) {
        this.accountCardService = accountCardService;
    }
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }
    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public AccountBaseInfo getAccount() {
        return account;
    }
    public void setAccount(AccountBaseInfo account) {
        this.account = account;
    }
    public AccountCardInfo getCard() {
        return card;
    }
    public void setCard(AccountCardInfo card) {
        this.card = card;
    }
    public String getAccountListId() {
        return accountListId;
    }
    public void setAccountListId(String accountListId) {
        this.accountListId = accountListId;
    }
    public String getBalanceListId() {
        return balanceListId;
    }
    public void setBalanceListId(String balanceListId) {
        this.balanceListId = balanceListId;
    }
}
