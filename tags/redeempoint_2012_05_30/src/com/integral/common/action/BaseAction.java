package com.integral.common.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>Description: 公共父类</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-13
 */
public class BaseAction extends ActionSupport {
    /**
     * <p>Discription:序列号</p>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * <p>Discription:获取写文件流工具</p>
     * @param request
     * @param response
     * @param encode 编码方式
     * @param contentType 文件输出流的类型
     * @return
     * @throws IOException
     * @author 代超
     * @update 2011-5-13 代超[变更描述]
     */
    protected PrintWriter getPrintWriter(HttpServletRequest request,
            HttpServletResponse response, String encode, String contentType) throws IOException{
        request.setCharacterEncoding(encode);
        response.setContentType(contentType);
        PrintWriter out = response.getWriter();
        return out;
    }
    /**
     * <p>Discription:获取写文件流工具</p>
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 代超
     * @update 2011-5-13 代超[变更描述]
     */
    protected PrintWriter getPrintWriter(HttpServletRequest request,
            HttpServletResponse response) throws IOException{
        return getPrintWriter(request, response, "UTF-8", "text/json; charset=utf-8");
    }
    /**
     * <p>Discription:[获取写文件流工具]</p>
     * @return
     * @throws IOException
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    protected PrintWriter getPrintWriter() throws IOException{
        return getPrintWriter(ServletActionContext.getRequest(), ServletActionContext.getResponse(), "UTF-8", "text/json; charset=utf-8");
    }
    /**
     * <p>Discription:[获取当前请求]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    protected HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }
    
    /**
     * <p>Discription:[格式化Json字符串]</p>
     * @param jsonMap
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    protected String getJsonString(Map<String, Object> jsonMap){
        JsonFormat jf = new JsonFormat(true);
        jf.setAutoUnicode(true);
        return Json.toJson(jsonMap, jf);
    }
    /**
     * <p>Discription:[手动开启事务]</p>
     * @param transactionManager
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    protected TransactionStatus getTransactionStatus(DataSourceTransactionManager transactionManager){
        // 定义TransactionDefinition并设置好事务的隔离级别和传播方式。
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        // 代价最大、可靠性最高的隔离级别，所有的事务都是按顺序一个接一个地执行
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        return transactionManager.getTransaction(definition);
    }
    
}
