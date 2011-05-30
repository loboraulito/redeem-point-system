package com.integral.system.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.integral.common.BaseAction;

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
        PrintWriter out = super.getPrintWriter(request, response);
        out.print("{success:false,msg:'登录失败'}");
        out.flush();
        out.close();
    }

}
