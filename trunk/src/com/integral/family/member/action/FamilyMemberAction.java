package com.integral.family.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
import com.integral.family.member.service.IFamilyMemberService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyMemberAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private IFamilyMemberService familyMemberService;
    private DataSourceTransactionManager transactionManager;
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IFamilyMemberService familyMemberService.
     */
    public IFamilyMemberService getFamilyMemberService() {
        return familyMemberService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param familyMemberService The familyMemberService to set.
     */
    public void setFamilyMemberService(IFamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
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
     * @param arg0
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param arg0
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
