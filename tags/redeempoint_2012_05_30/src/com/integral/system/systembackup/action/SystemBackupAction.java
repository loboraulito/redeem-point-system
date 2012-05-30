package com.integral.system.systembackup.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.integral.common.action.BaseAction;
import com.integral.system.systembackup.bean.SystemBackSettingInfo;
import com.integral.system.systembackup.bean.SystemBackupInfo;
import com.integral.system.systembackup.service.ISystemBackupService;
import com.integral.system.systembackup.service.ISystemBackupSettingService;
import com.integral.util.StringUtils;
import com.integral.util.Tools;

public class SystemBackupAction extends BaseAction {
    private ISystemBackupService systemBackupService;
    private ISystemBackupSettingService systemBackupSettingService;
    private DataSourceTransactionManager transactionManager;
    
    private String userName;
    private String backType;
    private String backFileId;
    private String backValue;
    private String backTime;
    
    private int start;
    private int limit;
    
    private SystemBackupInfo backInfo;
    /**
     * <p>Discription:[系统备份列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String backupList(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("backType", backType);
        List<SystemBackupInfo> list = this.systemBackupService.queryPageBackList(start, limit, map);
        int listSize = this.systemBackupService.queryPageBackList(map);
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            out = super.getPrintWriter();
            resultMap.put("success", true);
            resultMap.put("totalCount", listSize);
            resultMap.put("backinfo", list);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            if(out != null){
                out.print(getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[备份文件下载]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String backupDownload(){
        PrintWriter out = null;
        OutputStream os = null;
        FileInputStream fis = null;
        HttpServletResponse response = ServletActionContext.getResponse();
        try{
            if(backFileId == null || "".equals(backFileId.trim())){
                throw new Exception("您没有选择要下载的备份文件！");
            }else{
                SystemBackupInfo backupInfo = this.systemBackupService.findById(backFileId);
                if(backupInfo == null){
                    throw new Exception("您选择的备份文件不存在！");
                }
                response.setHeader("Content-Disposition",
                        "attachment; fileName="
                                + new String(backupInfo.getFileName().getBytes("gb2312"),
                                        "ISO-8859-1"));
                //获得一个从服务器上的文件myFile中获得输入字节的输入流对象  
                fis = new FileInputStream(backupInfo.getFilePath());
                byte bytes[]=new byte[1024];//设置缓冲区为1024个字节，即1KB 
                int len=0;
                os = response.getOutputStream();
                while((len=fis.read(bytes))!=-1){     
                    //将指定 byte数组中从下标 0 开始的 len个字节写入此文件输出流,(即读了多少就写入多少)  
                    os.write(bytes,0,len);
                }
            }
        }catch(Exception e){
            try{
                if(os != null){
                    os.flush();
                    os.close();
                    os = null;
                }
                if(fis != null){
                    try {
                        fis.close();
                    }
                    catch (IOException e2) {
                        LOG.error(e2.getMessage());
                    }
                }
                //清空response的设置
                response.reset();
                out = getPrintWriter(ServletActionContext.getRequest(), response, "UTF-8", "text/html; charset=utf-8");
                out = response.getWriter();
                out.write("<script>parent.showSystemMsg('系统提示','系统错误，错误代码："+StringUtils.convertChar(e.getMessage())+"')</script>");
                out.flush();
                out.close();
            }catch(Exception e1){
                LOG.error(e1.getMessage());
            }
        }finally{
            if(os != null){
                try {
                    os.flush();
                    os.close();
                }
                catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
            if(fis != null){
                try {
                    fis.close();
                }
                catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return null;
    }
    /**
     * <p>Discription:[系统备份设置]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String systemBackupSetting(){
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TransactionStatus status = super.getTransactionStatus(transactionManager);
        String settingId = ServletActionContext.getRequest().getParameter("id");
        try{
            out = super.getPrintWriter();
            if(this.backType == null || this.backValue == null){
                throw new Exception("您输入的信息部完整！");
            }
            SystemBackSettingInfo settingInfo = new SystemBackSettingInfo();
            settingInfo.setBackValue(backValue);
            settingInfo.setBackType(backType);
            settingInfo.setUserName(userName);
            settingInfo.setBackTime(backTime);
            settingInfo.setCronValue(Tools.getCronValue(backType, backValue, backTime));
            if(settingId != null && !"".equals(settingId.trim())){
                settingInfo.setId(settingId);
            }
            this.systemBackupSettingService.saveOrUpdate(settingInfo);
            resultMap.put("success", true);
            resultMap.put("msg", "您的账目备份设置信息已经成功保存！");
        }catch(Exception e){
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            this.transactionManager.commit(status);
            if(out != null){
                out.print(super.getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    /**
     * <p>Discription:[获取数据备份设置]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String getBackupSettingList(){
        SystemBackSettingInfo settingInfo = new SystemBackSettingInfo();
        settingInfo.setUserName(userName);
        List list = this.systemBackupSettingService.findByExample(settingInfo);
        PrintWriter out = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            out = super.getPrintWriter();
            resultMap.put("success", true);
            resultMap.put("totalCount", list.size());
            resultMap.put("backsetting", list);
        }catch(Exception e){
            e.printStackTrace();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误，错误代码："+e.getMessage());
        }finally{
            if(out != null){
                out.print(getJsonString(resultMap));
                out.flush();
                out.close();
            }
        }
        return null;
    }
    
    public ISystemBackupService getSystemBackupService() {
        return systemBackupService;
    }

    public void setSystemBackupService(ISystemBackupService systemBackupService) {
        this.systemBackupService = systemBackupService;
    }

    public ISystemBackupSettingService getSystemBackupSettingService() {
        return systemBackupSettingService;
    }

    public void setSystemBackupSettingService(ISystemBackupSettingService systemBackupSettingService) {
        this.systemBackupSettingService = systemBackupSettingService;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public String getBackFileId() {
        return backFileId;
    }

    public void setBackFileId(String backFileId) {
        this.backFileId = backFileId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getBackValue() {
        return backValue;
    }
    public void setBackValue(String backValue) {
        this.backValue = backValue;
    }
    
    public String getBackTime() {
        return backTime;
    }
    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }
    public SystemBackupInfo getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(SystemBackupInfo backInfo) {
        this.backInfo = backInfo;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
