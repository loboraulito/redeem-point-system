package com.integral.family.manage.action;

import java.io.PrintWriter;
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
import com.integral.family.manage.bean.FamilyInfo;
import com.integral.family.manage.service.IFamilyInfoService;
import com.integral.family.member.bean.FamilyMember;
import com.integral.family.member.service.IFamilyMemberService;
import com.integral.util.RequestUtil;
import com.integral.util.Tools;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyManamgeAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private IFamilyInfoService familyManageService;
    private IFamilyMemberService familyMemberService;
    private DataSourceTransactionManager transactionManager;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IFamilyInfoService familyManageService.
     */
    public IFamilyInfoService getFamilyManageService() {
        return familyManageService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param familyManageService The familyManageService to set.
     */
    public void setFamilyManageService(IFamilyInfoService familyManageService) {
        this.familyManageService = familyManageService;
    }

    public IFamilyMemberService getFamilyMemberService() {
        return familyMemberService;
    }

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

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public String begin(){
        return SUCCESS;
    }
    /**
     * 某用户所在家庭列表(一个用户可以加入到多个家庭)
     * <p>Discription:[方法功能中文描述]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String familyList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        String userId = request.getParameter("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        if(userId == null || "".equals(userId)){
            resultMap.put("success", false);
            resultMap.put("msg", "用户ID为空，不能查询您所在家庭信息！");
        }
        try{
            out = super.getPrintWriter(request, response);
            List<FamilyInfo> list = this.familyManageService.findFamilyListByUserId(userId, start, limit);
            int listSize = this.familyManageService.findFamilyListSizeByUserId(userId);
            if(listSize < 1){
                resultMap.put("success", false);
                resultMap.put("msg", "您当前未加入任何家庭，请加入家庭或者创建家庭！");
                resultMap.put("msg1", "申请加入家庭，或者是创建家庭？");
            }else{
                resultMap.put("success", true);
                resultMap.put("familyList", list);
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
    
    public String familyManageAdd(){
        Map<String, Object> paramMap = RequestUtil.getRequestMap(request);
        
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
            RequestUtil.repalceAsDate(paramMap, "familyCreateDate", "yyyy-MM-dd");
            FamilyInfo family = new FamilyInfo();
            BeanUtils.populate(family, paramMap);
            String familyId = Tools.getUUID();
            if(family.getFamilyId() == null || "".equals(family.getFamilyId())){
                family.setFamilyId(familyId);
            }
            //用户自己创建的家庭，需要将该用户添加到家庭成员中
            this.familyManageService.save(family);
            
            FamilyMember member = new FamilyMember();
            member.setFamilyId(familyId);
            member.setSystemMemberId(family.getFamilyHouseHolder());
            
            this.familyMemberService.save(member);
            
            resultMap.put("success", true);
            resultMap.put("msg", "家庭信息创建成功，您已经成为：" + family.getFamilyName() + " 家庭的户主。<br><br>系统已自动将您加入到家庭成员中，请完善您的家庭成员信息！");
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "家庭信息创建失败，错误代码：" + e.getMessage());
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

}
