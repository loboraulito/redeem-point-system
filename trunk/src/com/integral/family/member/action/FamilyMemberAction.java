package com.integral.family.member.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
import com.integral.family.member.bean.FamilyMember;
import com.integral.family.member.service.IFamilyMemberService;

/** 
 * <p>Description: [家庭成员管理]</p>
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
    
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[查询本人所在直属家庭的家庭成员列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyMemberList(){
        String userId = request.getParameter("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        if(userId == null || "".equals(userId)){
            resultMap.put("success", false);
            resultMap.put("msg", "用户ID为空，不能查询您所在家庭成员信息！");
        }
        try{
            out = super.getPrintWriter(request, response);
            List<FamilyMember> list = this.familyMemberService.findSelfFamilyMemberList(userId, 0, 50);
            int listSize = this.familyMemberService.findSelfFamilyMemberListCount(userId);
            resultMap.put("success", true);
            resultMap.put("memberList", list);
            resultMap.put("totalCount", listSize);
        }catch(Exception e){
            LOG.error(e.getMessage());
        }finally{
            if(out != null){
                out.print(Json.toJson(resultMap, jf));
                out.flush();
                out.close();
            }
        }
        return null;
    }

}
