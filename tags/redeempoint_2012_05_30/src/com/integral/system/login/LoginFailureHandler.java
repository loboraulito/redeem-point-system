package com.integral.system.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.integral.common.action.BaseAction;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 26, 2011
 */
public class LoginFailureHandler extends BaseAction implements AuthenticationFailureHandler {

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * @author cdai
     * @update May 26, 2011 cdai[变更描述]
     */

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String loginType = request.getParameter("loginType");
        if("ext".equals(loginType)){
            PrintWriter out = super.getPrintWriter(request, response);
            String msg = exception.getMessage();
            
            out.print("{success:false,msg:'登录失败',error:'"+exception.getMessage()+"'}");
            out.flush();
            out.close();
        }else{
            request.getSession().setAttribute("loginFailMsg", "用户名或密码错误，请重新输入！");
            //request.setAttribute("loginFailMsg", "用户名或密码错误！");
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }

}
