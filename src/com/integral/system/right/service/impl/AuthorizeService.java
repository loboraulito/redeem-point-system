/**
 * 
 */
package com.integral.system.right.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.integral.common.dao.IBaseDao;
import com.integral.system.right.bean.AuthorizeInfo;
import com.integral.system.right.dao.IAuthorizeDao;
import com.integral.system.right.service.IAuthorizeService;

/**
 * @author cdai
 *
 */
public class AuthorizeService implements IAuthorizeService {
	private IAuthorizeDao authorizeDao;
	private IBaseDao baseDao;
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
	@Override
	public List showAuthorizeInfo() {
		String sql = "SELECT employee_info.operater_id, role_info.role_id, employee_info.operater_name, role_info.role_name FROM employee_info , role_info , supplier_role WHERE employee_info.operater_name =  supplier_role.operater_id AND role_info.role_id =  supplier_role.role_id ";
		List list = this.baseDao.queryBySQL(sql, null);
		List authorizeList = new ArrayList();
		if(list != null){
			for(int i=0,j = list.size();i<j;i++){
				AuthorizeInfo authorize = new AuthorizeInfo();
				Object [] obj = (Object[]) list.get(i);
				authorize.setUserId(String.valueOf(obj[0]));
				authorize.setRoleId(String.valueOf(obj[1]));
				authorize.setUserName(String.valueOf(obj[2]));
				authorize.setRoleName(String.valueOf(obj[3]));
				authorize.setCls("folder");
				authorize.setLeaf(false);
				authorizeList.add(authorize);
			}
		}
		return authorizeList;
	}
	
	
	
}
