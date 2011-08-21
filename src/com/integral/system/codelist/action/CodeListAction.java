package com.integral.system.codelist.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.common.action.BaseAction;
import com.integral.system.codelist.bean.CodeList;
import com.integral.system.codelist.bean.CodeListData;
import com.integral.system.codelist.service.ICodeListDataService;
import com.integral.system.codelist.service.ICodeListService;
import com.integral.util.RequestUtil;
import com.integral.util.office.OfficeOperationUtils;

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
    
    /** 文件上传 */
    private File [] codeDataList;
    private String[] codeDataListContentType;
    private String[] codeDataListFileName;
    private String savePath;
    
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
     * @return File[] codeDataList.
     */
    public File[] getCodeDataList() {
        return codeDataList;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeDataList The codeDataList to set.
     */
    public void setCodeDataList(File[] codeDataList) {
        this.codeDataList = codeDataList;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String[] codeDataListContentType.
     */
    public String[] getCodeDataListContentType() {
        return codeDataListContentType;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeDataListContentType The codeDataListContentType to set.
     */
    public void setCodeDataListContentType(String[] codeDataListContentType) {
        this.codeDataListContentType = codeDataListContentType;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String[] codeDataListFileName.
     */
    public String[] getCodeDataListFileName() {
        return codeDataListFileName;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeDataListFileName The codeDataListFileName to set.
     */
    public void setCodeDataListFileName(String[] codeDataListFileName) {
        this.codeDataListFileName = codeDataListFileName;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String savePath.
     */
    public String getSavePath() {
        return ServletActionContext.getRequest().getRealPath(savePath);
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param savePath The savePath to set.
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
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
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        long dataSize = this.codeListService.getCodeListSize();
        List <CodeList> list = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PrintWriter out = null;
        //true:不换行，忽略null
        JsonFormat jf = new JsonFormat(true);
        //设置Unicode编码
        jf.setAutoUnicode(true);
        try{
            out = super.getPrintWriter(request, response);
            list = this.codeListService.getCodeListByPage(start, limit);
            resultMap.put("success", true);
            resultMap.put("totalCount", dataSize);
            resultMap.put("codeList", list);
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
    /**
     * <p>Discription:[添加数据标准]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeListManageAdd(){
        Map<String, Object> paramMap = RequestUtil.getRequestMap(request);
        CodeList codeList = new CodeList();
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
            List list = this.codeListService.findByName(paramMap.get("codeName"));
            if(list != null && list.size()>0){
                resultMap.put("success", false);
                resultMap.put("msg", "系统中已存在名称为 “"+ paramMap.get("codeName") +"” 的数据标准，请更改名称！");
            }else{
                BeanUtils.populate(codeList, paramMap);
                if(codeList.getCodeId() == null || "".equals(codeList.getCodeId())){
                    codeList.setCodeId(null);
                }
                this.codeListService.saveOrUpdate(codeList);
                resultMap.put("success", true);
                resultMap.put("msg", "名称为 “"+ paramMap.get("codeName") +"” 的数据标准已成功保存！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
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
     * <p>Discription:[修改数据标准]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeListManageEdit(){
        Map<String, Object> paramMap = RequestUtil.getRequestMap(request);
        CodeList codeList = new CodeList();
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
            BeanUtils.populate(codeList, paramMap);
            if(codeList.getCodeId() == null || "".equals(codeList.getCodeId())){
                codeList.setCodeId(null);
            }
            this.codeListService.saveOrUpdate(codeList);
            resultMap.put("success", true);
            resultMap.put("msg", "名称为 “"+ paramMap.get("codeName") +"” 的数据标准已成功保存！");
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
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
     * <p>Discription:[删除数据标准]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeListManageDelete(){
        String codeLists = request.getParameter("codeId");
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
            List<CodeList> codes = new ArrayList<CodeList>();
            if(codeLists != null && !"".equals(codeLists.trim())){
                String [] codeList = codeLists.split(",");
                for(int i=0; i<codeList.length; i++){
                    CodeList code = new CodeList();
                    code.setCodeId(codeList[i]);
                    codes.add(code);
                }
                this.codeListDataService.deleteByCodeListId(codeList);
            }
            this.codeListService.deleteAll(codes);
            resultMap.put("success", true);
            resultMap.put("msg", "您所选数据标准已成功删除！");
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
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
     * <p>Discription:[数据标准值列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeDataList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        long dataSize = this.codeListDataService.getCodeListDataSize();
        List <CodeListData> list = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PrintWriter out = null;
        //true:不换行，忽略null
        JsonFormat jf = new JsonFormat(true);
        //设置Unicode编码
        jf.setAutoUnicode(true);
        try{
            out = super.getPrintWriter(request, response);
            list = this.codeListDataService.getCodeListDataByPage(start, limit);
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
    /**
     * <p>Discription:[添加数据标准值]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeDataManageAdd(){
        Map<String, Object> paramMap = RequestUtil.getRequestMap(request);
        CodeListData codeData = new CodeListData();
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
            BeanUtils.populate(codeData, paramMap);
            CodeListData c = new CodeListData();
            c.setCodeId(paramMap.get("codeId") == null ? null : paramMap.get("codeId").toString());
            c.setDataKey(paramMap.get("dataKey") == null ? null :paramMap.get("dataKey").toString());
            List list = this.codeListDataService.findByExample(c);
            if(list != null && list.size() >0){
                resultMap.put("success", false);
                resultMap.put("msg", "已存在代码为 “"+paramMap.get("dataKey")+"” 的数据标准值！");
            }else{
                if(codeData.getDataId() == null || "".equals(codeData.getDataId().trim())){
                    codeData.setDataId(null);
                }
                this.codeListDataService.saveOrUpdate(codeData);
                resultMap.put("success", true);
                resultMap.put("msg", "数据标准值已成功保存！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
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
     * <p>Discription:[修改数据标准值]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeDataManageEdit(){
        Map<String, Object> paramMap = RequestUtil.getRequestMap(request);
        CodeListData codeData = new CodeListData();
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
            BeanUtils.populate(codeData, paramMap);
            if(codeData.getDataId() == null || "".equals(codeData.getDataId().trim())){
                codeData.setDataId(null);
            }
            this.codeListDataService.saveOrUpdate(codeData);
            resultMap.put("success", true);
            resultMap.put("msg", "数据标准值已成功保存！");
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
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
     * <p>Discription:[删除数据标准值]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeDataManageDelete(){
        String dataIds = request.getParameter("dataIds");
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
            if(dataIds == null || "".equals(dataIds.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "您没有选择数据标准值！");
            }else{
                String dataId[] = dataIds.split(",");
                List<CodeListData> list = new ArrayList<CodeListData>();
                for(int i =0; i<dataId.length; i++){
                    CodeListData data = new CodeListData();
                    data.setDataId(dataId[i]);
                    list.add(data);
                }
                this.codeListDataService.deleteAll(list);
                resultMap.put("success", true);
                resultMap.put("msg", "所选数据标准值已成功删除！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "数据标准值删除过程中异常！<br>"+e.getMessage()+"");
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
     * <p>Discription:[查询数据标准树形结构]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String codeDataManageTree(){
        String codeId = request.getParameter("codeId");
        String parentKey = request.getParameter("node");
        PrintWriter out = null;
        //true:不换行，忽略null
        JsonFormat jf = new JsonFormat(true);
        //设置Unicode编码
        jf.setAutoUnicode(true);
        try{
            out = super.getPrintWriter(request, response);
            List list = this.codeListDataService.findCodeDataListTree(codeId, parentKey);
            out.print(Json.toJson(list,jf).replaceAll("\"checked\":false,", ""));
        }catch(Exception e){
            out.print("{success:false}");
        }finally{
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[导出数据标准值]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String exportCodeDataList(){
        //要导出的字段
        Map<String, Object> requestMap = RequestUtil.getRequestMap(request);
        OutputStream os = null;
        PrintWriter pw = null;
        try{
            response.setHeader("Content-Disposition",
                    "attachment; fileName="
                            + new String("数据标准值.xls".getBytes("gb2312"),
                                    "ISO-8859-1"));
            os = response.getOutputStream();
            List list = this.codeListDataService.findAllOrderByDataCode();
            OfficeOperationUtils<CodeListData> util = new OfficeOperationUtils<CodeListData>();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.putAll(requestMap);
            
            util.writExcelFile("数据标准值", list, os, map, "yyyy-MM-dd");
            os.close();
        }catch(Exception e){
            e.printStackTrace();
            try {
                pw = super.getPrintWriter(request, response, "UTF-8", "text/html; charset=utf-8");
                String msg = "文件导出过程中出现异常，请检查！"+e.toString();
                pw.write("<script>alert('"+msg+"')</script>");
                pw.flush();
                pw.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally{
            if(os != null){
                try {
                    os.flush();
                    os.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * <p>Discription:[导出数据标准值模板]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String exportCodeDataDemo(){
        //要导出的字段
        Map<String, Object> requestMap = RequestUtil.getRequestMap(request);
        OutputStream os = null;
        PrintWriter pw = null;
        try{
            response.setHeader("Content-Disposition",
                    "attachment; fileName="
                            + new String("数据标准模板.xls".getBytes("gb2312"),
                                    "ISO-8859-1"));
            os = response.getOutputStream();
            CodeListData data = new CodeListData("1","10000","数据标准值","1","数据标准分类","","上级数据标准值","备注");
            List<CodeListData> list = new ArrayList<CodeListData>();
            list.add(data);
            OfficeOperationUtils<CodeListData> util = new OfficeOperationUtils<CodeListData>();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.putAll(requestMap);
            util.writExcelFile("数据标准模板", list, os, map, "yyyy-MM-dd");
            os.close();
        }catch(Exception e){
            e.printStackTrace();
            try {
                pw = super.getPrintWriter(request, response, "UTF-8", "text/html; charset=utf-8");
                String msg = "文件导出过程中出现异常，请检查！"+e.toString();
                pw.write("<script>alert('"+msg+"')</script>");
                pw.flush();
                pw.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally{
            if(os != null){
                try {
                    os.flush();
                    os.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * <p>Discription:[导入数据标准值]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String importCodeDataList(){
        
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(definition);
        OfficeOperationUtils<CodeListData> util = new OfficeOperationUtils<CodeListData>();
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String resultMsg = "文件导入成功！";
        //是否有重复数据,若有，则文件不导入到数据库中
        boolean bool = false;
        
        PrintWriter out = null;
        //可以使key-value转换的map
        BidiMap properties = new TreeBidiMap();
        properties.put("dataId", "数据标准值唯一编码");
        properties.put("dataKey", "数据标准值编号");
        properties.put("dataValue", "数据标准值");
        properties.put("codeId", "数据标准分类编码");
        properties.put("codeName", "数据标准分类");
        properties.put("parentDataKey", "上级数据标准值编号");
        properties.put("parentDataValue", "上级数据标准值");
        properties.put("remark", "备注");
        
        try{
            if(codeDataList == null){
                throw new FileNotFoundException();
            }
            //上传文件中的所有数据
            Map map = util.readExcelFile(util.getWorkBook(codeDataList[0]));
            
            out = super.getPrintWriter(request, response, "utf-8", "text/html; charset=utf-8");
            //一个xls文件中的各个sheet页
            List<CodeListData> importCodeData = new ArrayList<CodeListData>();
            for(Object o : map.entrySet()){
                Map.Entry entry = (Map.Entry)o;
                //一个sheet页
                List codeList = (List) entry.getValue();
                if(codeList != null){
                    //sheet页的第一行是标题行
                    String [] header = (String[]) codeList.get(0);
                    for(int i=1, j = codeList.size(); i<j; i++){
                        Map codeData = new HashMap();
                        //sheet页的其他行
                        String [] datas = (String[]) codeList.get(i);
                        if(datas != null){
                            for(int m = 0; m<datas.length; m++){
                                codeData.put(properties.getKey(header[m]), datas[m]);
                            }
                            CodeListData codeListData = new CodeListData();
                            try {
                                BeanUtils.populate(codeListData, codeData);
                                CodeListData clone = new CodeListData();
                                //codeName
                                //查询对应的codeName的ID
                                if(codeListData.getCodeName() != null && !"".equals(codeListData.getCodeName().trim())){
                                    List codeNameList = this.codeListService.findByName(codeListData.getCodeName());
                                    if(codeNameList != null && codeNameList.size()>0){
                                        CodeList codeName = (CodeList) codeNameList.get(0);
                                        codeListData.setCodeId(codeName.getCodeId());
                                    }
                                }
                                //查询对应codeId的dataKey是否已经存在
                                clone.setCodeId(codeListData.getCodeId());
                                clone.setDataKey(codeListData.getDataKey());
                                List cloneList = this.codeListDataService.findByExample(clone);
                                if(cloneList != null && cloneList.size() >0){
                                    //已经存在，不允许再导入
                                    resultMsg = "您上传的文件中第  "+i+" 行数据已经存在数据库中，请检查！";
                                    bool = true;
                                    break;
                                }
                                
                                //parentDataValue
                                //查询对应dataValue的dataKey
                                if(codeListData.getParentDataValue() != null && !"".equals(codeListData.getParentDataValue().trim())){
                                    List dataList = this.codeListDataService.findByDataValue(codeListData.getParentDataValue());
                                    if(dataList != null && dataList.size() >0){
                                        CodeListData data = (CodeListData) dataList.get(0);
                                        codeListData.setParentDataKey(data.getDataKey());
                                    }
                                }
                                importCodeData.add(codeListData);
                            }
                            catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            LOG.info("" + importCodeData);
            if(!bool){
                this.codeListDataService.saveOrUpdateAll(importCodeData);
            }
            resultMap.put("success", true);
            resultMap.put("msg", resultMsg);
        }catch(Exception e){
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "文件导入失败！");
        }finally{
            transactionManager.commit(status);
            if(out != null){
                out.print(Json.toJson(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
