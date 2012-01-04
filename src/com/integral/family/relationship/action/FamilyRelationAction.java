package com.integral.family.relationship.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
import com.integral.family.relationship.service.IFamilyRelationService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyRelationAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    private IFamilyRelationService familyRelationService;
    private DataSourceTransactionManager transactionManager;
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IFamilyRelationService familyRelationService.
     */
    public IFamilyRelationService getFamilyRelationService() {
        return familyRelationService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param familyRelationService The familyRelationService to set.
     */
    public void setFamilyRelationService(IFamilyRelationService familyRelationService) {
        this.familyRelationService = familyRelationService;
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
    /**
     * <p>Discription:[入口函数]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[家庭成员关系列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyRelationList(){
        
        return null;
    }
    
    /**
     * <p>Discription:[家庭成员关系变更]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyRelationEdit(){
        
        return null;
    }

}
