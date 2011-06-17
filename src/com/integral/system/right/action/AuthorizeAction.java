/**
 * 
 */
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
import com.integral.system.right.service.IAuthorizeService;

/**
 * @author cdai
 * 权限授权
 */
public class AuthorizeAction extends BaseAction implements ServletRequestAware, ServletResponseAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private IAuthorizeService authorizeService;

	/**
	 * @return the authorizeService
	 */
	public IAuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	/**
	 * @param authorizeService the authorizeService to set
	 */
	public void setAuthorizeService(IAuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String begin(){
		return SUCCESS;
	}
	
	public String authorizeList(){
		List list = this.authorizeService.showAuthorizeInfo();
		PrintWriter out = null;
        try {
            out = super.getPrintWriter(request, response);
            out.print("{success:true,totalCount:"+list.size()+",data:"+Json.toJson(list)+"}");
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
	
	public String authorizeUser(){
		
		return null;
	}
	
	public String authorizeMenu(){
		
		return null;
	}

}
