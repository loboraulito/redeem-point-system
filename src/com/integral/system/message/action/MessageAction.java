package com.integral.system.message.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.integral.common.action.BaseAction;
import com.integral.system.message.bean.SystemMessage;
import com.integral.system.message.service.IMessageService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class MessageAction extends BaseAction implements ServletRequestAware, ServletResponseAware, ServletContextAware {

    private ServletContext context;
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    private DataSourceTransactionManager transactionManager;
    private IMessageService messageService;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param context
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param response
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param request
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    
    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }
    /**
     * <p>Discription:[入口]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    /**
     * <p>Discription:[消息列表]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String messageList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        String userId = request.getParameter("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        PrintWriter out = null;
        try{
            out = super.getPrintWriter(request, response);
            Map<String, Object> requestMap = new HashMap<String, Object>();
            requestMap.put("userId", userId);
            if(userId == null || "".equals(userId.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "用户信息不完善，无法处理请求！");
            }else{
                List<SystemMessage> msgList = this.messageService.findByParams(start, limit, requestMap);
                int msgSize = this.messageService.findCountByParams(start, limit, requestMap);
                
                resultMap.put("success", true);
                resultMap.put("messageList", msgList);
                resultMap.put("totalCount", msgSize);
            }
        }catch(Exception e){
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
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
     * <p>Discription:[发送消息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String sendMessage(){
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
            String messageTos = request.getParameter("messageTo");
            if(messageTos == null || "".equals(messageTos.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "您未选择消息接收人！");
            }else{
                Date now = new Date();
                String [] messageTo = messageTos.split(",");
                List<SystemMessage> messageList = new ArrayList<SystemMessage>();
                for(String msgTo : messageTo){
                    SystemMessage msg = new SystemMessage();
                    msg.setMessageSendTime(now);
                    msg.setMessageTo(msgTo);
                    msg.setMessageFrom(request.getParameter("messageFrom"));
                    msg.setMessageTitle(request.getParameter("messageTitle"));
                    msg.setMessageContent(request.getParameter("messageContent"));
                    //新消息
                    msg.setMessageNew("1");
                    messageList.add(msg);
                }
                this.messageService.saveOrUpdateAll(messageList);
                resultMap.put("success", true);
                resultMap.put("msg", "您的消息已经成功发送给接收人！");
            }
        }catch(Exception e){
            status.setRollbackOnly();
            LOG.error(e.getMessage());
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码："+e.getMessage());
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
     * <p>Discription:[手动接收消息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String receiveMessage(){
        
        return null;
    }
    /**
     * <p>Discription:[删除消息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String deleteMessage(){
        String messageIds = request.getParameter("msg");
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
            if(messageIds == null || "".equals(messageIds.trim())){
                resultMap.put("success", false);
                resultMap.put("msg", "您没有选中要删除的系统信息！");
            }else{
                String[] msg = messageIds.split(",");
                List<SystemMessage> messageList = new ArrayList<SystemMessage>();
                for(String msgId : msg){
                    SystemMessage message = new SystemMessage(msgId);
                    messageList.add(message);
                }
                this.messageService.deleteAll(messageList);
                resultMap.put("success", true);
                resultMap.put("msg", "您所选系统信息已成功删除！");
            }
        }catch(Exception e){
            LOG.error(e.getMessage());
            status.setRollbackOnly();
            resultMap.put("success", false);
            resultMap.put("msg", "系统错误！错误代码："+e.getMessage());
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
     * <p>Discription:[查找消息]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String queryMessage(){
        return null;
    }
}
