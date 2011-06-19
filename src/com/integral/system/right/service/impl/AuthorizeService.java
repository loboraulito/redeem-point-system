/**
 * 
 */
package com.integral.system.right.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.bean.MenuTree;
import com.integral.system.right.dao.IAuthorizeDao;
import com.integral.system.right.service.IAuthorizeService;
import com.integral.system.user.bean.UserInfo;
import com.integral.util.user.ProtectUserInfo;

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
	
	public List showAuthorzieUser(int start, int limit, Object [] params){
	    String sql = "from UserInfo model, UserRole role where model.userName = role.userId and role.roleId = ? ";
	    List list = this.baseDao.queryPageByHQL(sql, params, start, limit);
	    List userList = new ArrayList();
	    if(list != null){
	        for(int i=0,j = list.size();i<j;i++){
	            Object[] obj = (Object[]) list.get(i);
	            UserInfo user = (UserInfo) obj[0];
	            userList.add(user);
	        }
	    }
	    return ProtectUserInfo.protectUserInfo(userList);
	}
    @Override
    public Long showAuthorzieUser(Object[] params) {
        String sql = "from UserInfo model, UserRole role where model.userName = role.userId and role.roleId = ? ";
        List list = this.baseDao.queryPageByHQL(sql, params, -1, -1);
        if(list != null){
            return (long)list.size();
        }
        return 0L;
    }
    @Override
    public List showAuthorzieMenu(String roleId, String rootId) {
        String sql = "select model.menuId from RoleMenuInfo model where model.roleId = ?";
        //授权的菜单id
        List authorizeMenu = this.baseDao.queryByHQL(sql, new Object[]{roleId});
        
        String menuSql = "";
        List rootMenu = null;
        if(rootId == null || "".equals(rootId)){
            menuSql = "FROM MenuInfo where parentMenuId is NULL ";
            rootMenu = this.baseDao.queryByHQL(menuSql,null);
        }else{
            menuSql = "FROM MenuInfo where parentMenuId = ? ";
            rootMenu = this.baseDao.queryByHQL(menuSql, new String[]{rootId});
        }
        
        
        
        return authorizeMenu;
    }
}
