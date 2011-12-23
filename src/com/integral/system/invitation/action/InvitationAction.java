package com.integral.system.invitation.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.integral.common.action.BaseAction;
import com.integral.system.invitation.bean.SystemInviteProcess;
import com.integral.system.invitation.service.ISystemInviteProcessService;

/** 
 * <p>Description: [处理系统请求]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class InvitationAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private ISystemInviteProcessService systemInviteProcessService;
    private DataSourceTransactionManager transactionManager;
    
    public ISystemInviteProcessService getSystemInviteProcessService() {
        return systemInviteProcessService;
    }

    public void setSystemInviteProcessService(ISystemInviteProcessService systemInviteProcessService) {
        this.systemInviteProcessService = systemInviteProcessService;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

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
     * <p>Discription:[系统请求开始]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[查询本人所有系统请求]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String invitationList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        String userId = request.getParameter("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            if(userId == null || "".equals(userId.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "用户ID为空，不能查询您的请求信息");
            }else{
                List<SystemInviteProcess> list = this.systemInviteProcessService.findByUserId(userId, start, limit);
                int listSize = this.systemInviteProcessService.findCountByUserId(userId);
                resultMap.put("success", true);
                resultMap.put("invitationList", list);
                resultMap.put("totalCount", listSize);
            }
        }catch(Exception e){
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码："+ e.getMessage());
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
