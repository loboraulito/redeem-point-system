/**
 * 
 */
package com.integral.system.right.action;

import com.integral.common.action.BaseAction;
import com.integral.system.right.service.IAuthorizeService;

/**
 * @author cdai
 * 权限授权
 */
public class AuthorizeAction extends BaseAction {
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
}
