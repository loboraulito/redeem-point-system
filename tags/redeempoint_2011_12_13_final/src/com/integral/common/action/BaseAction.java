package com.integral.common.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
}
