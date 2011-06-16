/**
 * 
 */
package com.integral.system.right.service.impl;

import com.integral.common.dao.IBaseDao;
import com.integral.system.right.dao.IAuthorizeDao;
import com.integral.system.right.service.IAuthorizeService;

/**
 * @author cdai
 *
 */
public class AuthorizeService implements IAuthorizeService {
	private IAuthorizeDao authorizeDao;
	/**
	 * @return the authorizeDao
	 */
	public IAuthorizeDao getAuthorizeDao() {
		return authorizeDao;
	}
	/**
	 * @param authorizeDao the authorizeDao to set
	 */
	public void setAuthorizeDao(IAuthorizeDao authorizeDao) {
		this.authorizeDao = authorizeDao;
	}
	/**
	 * @return the baseDao
	 */
	public IBaseDao getBaseDao() {
		return baseDao;
	}
	/**
	 * @param baseDao the baseDao to set
	 */
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	private IBaseDao baseDao;
}
