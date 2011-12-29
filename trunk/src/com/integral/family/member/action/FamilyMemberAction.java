package com.integral.family.member.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.common.action.BaseAction;
import com.integral.family.member.bean.FamilyMember;
import com.integral.family.member.service.IFamilyMemberService;
import com.integral.system.invitation.bean.SystemInviteProcess;
import com.integral.system.invitation.service.ISystemInviteProcessService;
import com.integral.util.ListUtils;
import com.integral.util.RequestUtil;

/** 
 * <p>Description: [家庭成员管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyMemberAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private IFamilyMemberService familyMemberService;
    
    private ISystemInviteProcessService systemInviteProcessService;
    private DataSourceTransactionManager transactionManager;
    
    
    
    public ISystemInviteProcessService getSystemInviteProcessService() {
        return systemInviteProcessService;
    }

    public void setSystemInviteProcessService(ISystemInviteProcessService systemInviteProcessService) {
        this.systemInviteProcessService = systemInviteProcessService;
    }

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
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
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
            List<FamilyMember> list = this.familyMemberService.findSelfFamilyMemberList(userId, start, limit);
            int listSize = this.familyMemberService.findSelfFamilyMemberListCount(userId);
            if(listSize < 1){
                resultMap.put("success", false);
                resultMap.put("msg", "您当前未加入任何家庭，请加入家庭或者创建家庭！");
                resultMap.put("msg1", "申请加入家庭，或者是创建家庭？");
            }else{
                resultMap.put("success", true);
                resultMap.put("memberList", list);
                resultMap.put("totalCount", listSize);
            }
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
    /**
     * <p>Discription:[申请加入家庭]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyMemberApply(){
        String sponsor = request.getParameter("sponsor");
        String recipient = request.getParameter("recipient");
        String menuId = request.getParameter("menuId");
        String familyId = request.getParameter("familyId");
        String familyName = request.getParameter("familyName");
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
            if(familyId == null || "".equals(familyId.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "所选家庭为空，请重新选择！");
            }else{
                String holderIds[] = recipient.split(",");
                String familyIds[] = familyId.split(",");
                String familyNames[] = familyName.split(",");
                List<SystemInviteProcess> processList = new ArrayList<SystemInviteProcess>();
                for(int i=0; i< familyIds.length; i++){
                    FamilyMember member = new FamilyMember();
                    member.setFamilyId(familyIds[i]);
                    member.setFamilyName(familyNames[i]);
                    member.setSystemMemberId(sponsor);
                    SystemInviteProcess process = new SystemInviteProcess();
                    
                    process.setSponsor(sponsor);
                    process.setSponsorTime(new Date());
                    process.setRecipient(holderIds[i]);
                    process.setInvitationMenu(menuId);
                    //未处理
                    process.setProcessStatus("1");
                    String event = "用户 "+sponsor+" 请求加入您的家庭：【"+familyNames[i] +"】";
                    process.setInvitationEvent(event);
                    String nextAction = "/family_member/familyMemberApplyProcess.action?method=familyMemberApplyProcess";
                    process.setNextaction(nextAction);
                    
                    process.setRelationEntityName(FamilyMember.class.getName());
                    
                    JsonFormat jf1 = new JsonFormat(true);
                    process.setRelationData(Json.toJson(member, jf1));
                    processList.add(process);
                }
                this.systemInviteProcessService.saveOrUpdateAll(processList);
                resultMap.put("success", true);
                resultMap.put("msg", "已向所选家庭户主发出请求，请等待户主回应！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码：" + e.getMessage());
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
     * <p>Discription:[处理申请]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyMemberApplyProcess(){
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
            List<FamilyMember> relationDataList = (List<FamilyMember>) request.getSession().getAttribute("relationDataList");
            List<FamilyMember> memberList = new ArrayList<FamilyMember>();
            StringBuffer sb = new StringBuffer();
            
            if(relationDataList != null){
                //剔除重复的
                List<FamilyMember> ll = ListUtils.removeDuplication(relationDataList, "familyId", "systemMemberId");
                
                for(FamilyMember m : ll){
                    List l = this.familyMemberService.findByExample(m);
                    if(l != null && l.size() >0){
                        sb.append("用户【"+m.getSystemMemberId()+"】已经是【"+m.getFamilyName()+"】的成员，无需重复加入！<br><br>");
                        continue;
                    }else{
                        memberList.add(m);
                        sb.append("您已成功接受【"+m.getSystemMemberId()+"】的请求，成为【"+m.getFamilyName()+"】的成员！<br><br>");
                    }
                }
                if(memberList != null && memberList.size() >0){
                    this.familyMemberService.saveOrUpdateAll(memberList);
                }
                
                resultMap.put("success", true);
                resultMap.put("msg", sb.substring(0, sb.lastIndexOf("<br><br>")).toString());
            }else{
                resultMap.put("success", false);
                resultMap.put("msg", "用户信息不完整，无法处理！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码："+e.getMessage());
            LOG.info(e.getMessage());
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
     * <p>Discription:[完善个人信息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyMemberInfoEdit(){
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
            Map<String, Object> paramMap = RequestUtil.getRequestMap(request);
            RequestUtil.repalceAsDate(paramMap, "familyMemberBirthdate", "yyyy-MM-dd");
            RequestUtil.repalceAsDate(paramMap, "familyMemberDeaddate", "yyyy-MM-dd");
            FamilyMember member = new FamilyMember();
            if(paramMap.get("systemMemberId") == null){
                resultMap.put("success", false);
                resultMap.put("msg", "用户信息不完整，无法完善您的个人信息！");
            }else{
                member.setSystemMemberId(paramMap.get("systemMemberId").toString());
                List<FamilyMember> members = this.familyMemberService.findByExample(member);
                if(members == null){
                    resultMap.put("success", false);
                    resultMap.put("msg", "用户信息不存在，无法完善您的个人信息 ！");
                }else{
                    List<FamilyMember> fm = new ArrayList<FamilyMember>();
                    for(FamilyMember m : members){
                        BeanUtils.populate(m, paramMap);
                        fm.add(m);
                    }
                    this.familyMemberService.saveOrUpdateAll(fm);
                    resultMap.put("success", true);
                    resultMap.put("msg", "已成功完善您的个人信息！");
                }
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码：" + e.getMessage());
            LOG.error(e.getMessage());
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.print(Json.toJson(resultMap, jf));
                out.flush();
                out.close();
            }
        }
        
        return null;
    }
    /**
     * <p>Discription:[移除家庭成员]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyMemberRemove(){
        
        return null;
    }
}
