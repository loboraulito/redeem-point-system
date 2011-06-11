package com.integral.system.user.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;

import com.integral.common.action.BaseAction;
import com.integral.system.user.service.IUserService;

public class UserAction extends BaseAction implements ServletRequestAware, ServletResponseAware{
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IUserService userService;
    
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IUserService userService.
     */
    public IUserService getUserService() {
        return userService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param userService The userService to set.
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    /**
     * <p>Discription:[用户管理列表页面]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public String userManageList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        
        long userSize = this.userService.findUserSize();
        List userList = this.userService.findUserByPageWithProtect(start, limit);
        
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+userSize+",userList:"+Json.toJson(userList)+"}");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
    
    /**
     * <p>Discription:[用户管理的入口]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-11 代超[变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
}
