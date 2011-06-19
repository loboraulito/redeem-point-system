/**
 * 
 */
package com.integral.system.right.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.bean.MenuTree;
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
	
	public List showAuthorizeMenuInfo(String rootId) {
		String sql = "";
		List list = null;
		if(rootId == null || "".equals(rootId)){
		    sql = "FROM MenuInfo where parentMenuId is NULL ";
		    list = this.baseDao.queryByHQL(sql,null);
		}else{
		    sql = "FROM MenuInfo where parentMenuId = ? ";
		    list = this.baseDao.queryByHQL(sql, new String[]{rootId});
		}
		
		List authorizeList = new ArrayList();
		if(list != null){
			for(int i=0,j = list.size();i<j;i++){
			    MenuTree tree = new MenuTree();
			    MenuInfo menu = (MenuInfo) list.get(i);
				tree.setId(menu.getMenuId());
				tree.setHref(null);
				tree.setDescription(menu.getMenuName());
				tree.setLeaf(false);
				tree.setCls("folder");
				tree.setQtip(menu.getMenuName());
				tree.setText(menu.getMenuName());
				tree.setExpandable(true);
				tree.setSingleClickExpand(true);
				if("1".equals(menu.getIsLeave())){
				    //叶子节点，则查询按钮
				    tree.setComment("leaf=yes");
				}else{
				    tree.setComment("leaf=no");
				}
				authorizeList.add(tree);
			}
		}
		return authorizeList;
	}
}
