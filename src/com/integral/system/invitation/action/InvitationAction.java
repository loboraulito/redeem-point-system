package com.integral.system.invitation.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import org.nutz.lang.Lang;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.common.action.BaseAction;
import com.integral.family.member.bean.FamilyMember;
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
    /**
     * 保存跳转路径
     */
    private String successUrl = "";
    
    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

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
        setSuccessUrl("/jsp/system/invitation/invitation.jsp");
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
        String menuId = request.getParameter("menuId");
        String status = request.getParameter("status");
        String fromUserId = request.getParameter("fromUserId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            if(userId == null || "".equals(userId.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "用户ID为空，不能查询您的请求信息");
            }else if(fromUserId != null && !"".equals(fromUserId.trim())){
                List<SystemInviteProcess> list = this.systemInviteProcessService.findByUserId(userId, fromUserId, menuId, status, start, limit);
                int listSize = this.systemInviteProcessService.findCountByUserId(userId, fromUserId, menuId, status);
                resultMap.put("success", true);
                resultMap.put("invitationList", list);
                resultMap.put("totalCount", listSize);
            }else{
                List<SystemInviteProcess> list = this.systemInviteProcessService.findByUserId(userId, "", menuId, status, start, limit);
                int listSize = this.systemInviteProcessService.findCountByUserId(userId, "", menuId, status);
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
    /**
     * <p>Discription:[请求通过]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String invitationPass(){
        String invitationId = request.getParameter("invitationId");
        String data = request.getParameter("data");
        String url = request.getParameter("urls");
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            if(invitationId == null || "".equals(invitationId.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "所选系统请求数据不完整，无法处理！");
            }else{
                String [] invitationIds = invitationId.split(",");
                String [] datas = data.split("@");
                List<SystemInviteProcess> list = new ArrayList<SystemInviteProcess>();
                List relationDataList = new ArrayList();
                for(int i=0; i<invitationIds.length; i++){
                    SystemInviteProcess process = this.systemInviteProcessService.findById(invitationIds[i]);
                    String className = "";
                    if(process != null){
                        className = process.getRelationEntityName();
                        process.setProcessTime(new Date());
                        process.setProcessStatus("2");
                        process.setProcessResultCode("1");
                        list.add(process);
                    }
                    Class c = Class.forName(className);
                    Object relationData = Json.fromJson(c, Lang.inr(datas[i]));
                    relationDataList.add(relationData);
                    //转发只能转发一次, 不能带参数：如abc.action?method=abc
                    //request.getRequestDispatcher(urls[i]).forward(request, response);
                }
                this.systemInviteProcessService.saveOrUpdateAll(list);
                
                request.getSession().setAttribute("relationDataList", relationDataList);
                //重定向无法使用request传递参数，只能在url上传递
                response.sendRedirect(url);
                resultMap.put("success", true);
                resultMap.put("msg", "所选系统请求已成功处理！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码："+e.getMessage());
            LOG.error(e.getMessage());
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.print(Json.toJson(resultMap, jf));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[请求被拒绝]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String invitationReject(){
        String invitationId = request.getParameter("invitationId");
        String data = request.getParameter("data");
        String url = request.getParameter("urls");
        String text = request.getParameter("reason");
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            if(invitationId == null || "".equals(invitationId.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "所选系统请求数据不完整，无法处理！");
            }else{
                String invitationIds[] = invitationId.split(",");
                List<SystemInviteProcess> list = new ArrayList<SystemInviteProcess>();
                for(int i=0; i<invitationIds.length; i++){
                    SystemInviteProcess process = this.systemInviteProcessService.findById(invitationIds[i]);
                    if(process != null){
                        process.setProcessTime(new Date());
                        process.setProcessStatus("2");
                        process.setProcessResultCode("2");
                        process.setInvitationReason(text);
                        list.add(process);
                    }
                }
                this.systemInviteProcessService.saveOrUpdateAll(list);
                resultMap.put("success", true);
                resultMap.put("msg", "您已成功拒绝所选请求！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码："+e.getMessage());
            LOG.error(e.getMessage());
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.print(Json.toJson(resultMap, jf));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    
}
