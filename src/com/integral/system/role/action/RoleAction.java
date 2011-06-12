package com.integral.system.role.action;

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
import com.integral.system.role.service.IRoleService;

/** 
 * <p>Description: [角色管理]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-12
 */
public class RoleAction extends BaseAction implements ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private IRoleService roleService;
    
    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IRoleService roleService.
     */
    public IRoleService getRoleService() {
        return roleService;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param roleService The roleService to set.
     */
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
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
     * <p>Discription:[角色管理]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public String begin(){
        return SUCCESS;
    }
    
    /**
     * <p>Discription:[角色列表]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-12 代超[变更描述]
     */
    public String roleManageList(){
        int start = NumberUtils.toInt(request.getParameter("start"), 0);
        int limit = NumberUtils.toInt(request.getParameter("limit"), 50);
        
        long roleSize = this.roleService.findRoleSize();
        List roleList = this.roleService.findRoleListByPage(start, limit);
        
        PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+roleSize+",roleList:"+Json.toJson(roleList)+"}");
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
}
