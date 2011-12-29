package com.integral.system.common.action;

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
import com.integral.system.codelist.service.ICodeListDataService;
import com.integral.system.codelist.service.ICodeListService;
import com.integral.system.common.service.ICommonService;

/** 
 * <p>Description: [公共函数库]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CommonAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
    private ICommonService commonService;
    private ICodeListDataService codeListDataService;
    private ICodeListService codeListService;
    private DataSourceTransactionManager transactionManager;
    
    public ICommonService getCommonService() {
        return commonService;
    }

    public void setCommonService(ICommonService commonService) {
        this.commonService = commonService;
    }

    public ICodeListDataService getCodeListDataService() {
        return codeListDataService;
    }

    public void setCodeListDataService(ICodeListDataService codeListDataService) {
        this.codeListDataService = codeListDataService;
    }

    public ICodeListService getCodeListService() {
        return codeListService;
    }

    public void setCodeListService(ICodeListService codeListService) {
        this.codeListService = codeListService;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private HttpServletRequest request;
    private HttpServletResponse response;
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
        this.request= request;
    }
    /**
     * <p>Discription:[获取指定数据字典]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeListCombo(){
        String codeId = request.getParameter("codeId");
        String codeName = request.getParameter("codeName");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        
        try{
            out = super.getPrintWriter(request, response);
            if((codeId == null || "".equals(codeId.trim())) && (codeName == null || "".equals(codeName.trim()))){
                resultMap.put("success", false);
                resultMap.put("msg", "数据字典信息不完整，无法查询您的请求信息");
            }else{
                List list = this.codeListDataService.findCodeDataListCombo(codeId, codeName);
                if(list == null || list.size()<1){
                    resultMap.put("success", false);
                    resultMap.put("msg", "系统中没有查询到您指定的数据字典！");
                }else{
                    resultMap.put("success", true);
                    resultMap.put("codeList", list);
                }
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
     * <p>Discription:[获取字典中的地址信息, 家庭成员信息中针对身份证号码处理]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String getAddressFromCodeData(){
        String codeId = request.getParameter("codeId");
        String datakKey = request.getParameter("datakey");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        
        try{
            out = super.getPrintWriter(request, response);
            if((codeId == null || "".equals(codeId.trim())) && (datakKey == null || "".equals(datakKey.trim()))){
                resultMap.put("success", false);
                resultMap.put("msg", "提供的信息不完整，无法查询您的请求信息");
            }else{
                String proKey = datakKey.substring(0,2) + "0000";
                String cityKey = datakKey.substring(0, 4) + "00";
                String obj[] = new String[]{proKey, cityKey, datakKey};
                List list = this.commonService.getAddressFromCodeData(codeId, obj);
                if(list == null || list.size()<1){
                    resultMap.put("success", false);
                    resultMap.put("msg", "系统中没有查询到您指定的信息！");
                }else{
                    resultMap.put("success", true);
                    resultMap.put("codeList", list);
                }
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
