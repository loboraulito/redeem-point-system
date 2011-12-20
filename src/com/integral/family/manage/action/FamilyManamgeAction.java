package com.integral.family.manage.action;

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
import com.integral.family.manage.bean.FamilyInfo;
import com.integral.family.manage.service.IFamilyInfoService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyManamgeAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private IFamilyInfoService familyManageService;
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

}
