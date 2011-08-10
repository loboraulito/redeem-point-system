package com.integral.system.codelist.action;

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
import com.integral.system.codelist.bean.CodeListData;
import com.integral.system.codelist.service.ICodeListDataService;
import com.integral.system.codelist.service.ICodeListService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CodeListAction extends BaseAction implements ServletRequestAware, ServletResponseAware{
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private ICodeListService codeListService;
    private ICodeListDataService codeListDataService;
    
    /** 事务处理 */
    private DataSourceTransactionManager transactionManager;
    
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return ICodeListService codeListService.
     */
    public ICodeListService getCodeListService() {
        return codeListService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeListService The codeListService to set.
     */
    public void setCodeListService(ICodeListService codeListService) {
        this.codeListService = codeListService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return ICodeListDataService codeListDataService.
     */
    public ICodeListDataService getCodeListDataService() {
        return codeListDataService;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeListDataService The codeListDataService to set.
     */
    public void setCodeListDataService(ICodeListDataService codeListDataService) {
        this.codeListDataService = codeListDataService;
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

    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[数据标准列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeList(){
        return null;
    }
    /**
     * <p>Discription:[数据标准值列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeDataList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        long dataSize = this.codeListDataService.getCodeListDataSize();
        List <CodeListData> list = this.codeListDataService.getCodeListDataByPage(start, limit);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PrintWriter out = null;
        //true:不换行，忽略null
        JsonFormat jf = new JsonFormat(true);
        //设置Unicode编码
        jf.setAutoUnicode(true);
        try{
            out = super.getPrintWriter(request, response);
            resultMap.put("success", true);
            resultMap.put("totalCount", dataSize);
            resultMap.put("codeListData", list);
        }catch(Exception e){
            resultMap.put("success", false);
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
