package com.integral.system.right.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.nutz.json.Json;

import com.integral.common.action.BaseAction;
import com.integral.system.right.service.IRightService;

public class RightAction extends BaseAction implements
    ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IRightService rightService;

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRightService rightService.
     */
    public IRightService getRightService() {
        return rightService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param rightService The rightService to set.
     */
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
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
     * <p>Discription:[根据角色以及菜单查询该角色能访问的按钮]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-6 代超[变更描述]
     */
    public String buttonRight(){
        String menuId = request.getParameter("menuId");
        String roleId = request.getParameter("roleId");
        List buttons = this.rightService.getButtonByRight(menuId, roleId);
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+buttons.size()+",buttons:"+Json.toJson(buttons)+"}");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
