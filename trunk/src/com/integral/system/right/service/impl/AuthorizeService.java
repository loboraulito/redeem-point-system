/**
 * 
 */
package com.integral.system.right.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.bean.ButtonInfo;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.bean.MenuTree;
import com.integral.system.right.bean.AuthorizeInfo;
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
        String sql = "select CONCAT('menu_',model.menuId) as menuId from RoleMenuInfo model where model.roleId = ?";
        //授权的菜单id
        List authorizeMenu = this.baseDao.queryByHQL(sql, new Object[]{roleId});
        //授权的按钮ID
        sql = "select CONCAT('button_',model.buttonId) as buttonId from RightInfo model where model.roleId = ?";
        authorizeMenu.addAll(this.baseDao.queryByHQL(sql, new Object[]{roleId}));
        
        return getChildList(authorizeMenu, rootId);
    }
    
    public List showAuthorzieMenu(String roleId) {
        String sql = "select CONCAT('menu_',model.menuId) as menuId from RoleMenuInfo model where model.roleId = ?";
        //授权的菜单id
        List authorizeMenu = this.baseDao.queryByHQL(sql, new Object[]{roleId});
        //授权的按钮ID
        sql = "select CONCAT('button_',model.buttonId) as buttonId from RightInfo model where model.roleId = ?";
        authorizeMenu.addAll(this.baseDao.queryByHQL(sql, new Object[]{roleId}));
        
        return authorizeMenu;
    }
    
    /**
     * 查找根节点为rootID的儿子节点
     * @param menuList
     * @param rootId
     * @return
     */
    public List getChildList(String rootId){
    	List childList = new ArrayList();
    	String sql = "";
    	List rootMenu = null;
        if(rootId == null || "".equals(rootId)){
        	sql = "FROM MenuInfo where parentMenuId is NULL ";
            rootMenu = this.baseDao.queryByHQL(sql,null);
        }else{
        	sql = "FROM MenuInfo where parentMenuId = ? ";
            rootMenu = this.baseDao.queryByHQL(sql, new String[]{rootId});
        }
    	
    	for(int i=0,j=rootMenu.size();i<j;i++){
    		MenuInfo menu = (MenuInfo) rootMenu.get(i);
    		MenuTree tree = new MenuTree();
    		if(rootId == null || menu.getParentMenuId() == null || menu.getParentMenuId().equals(rootId)){
    			tree.setId("menu_"+menu.getMenuId());
    			tree.setChecked(false);
    			tree.setText(menu.getMenuName());
    			tree.setCls("folder");
    			tree.setSingleClickExpand(true);
    			tree.setQtip(menu.getMenuName());
    			tree.setLeaf(false);
    			if("0".equals(menu.getIsLeave())){
    				tree.setChildren(getChildList(menu.getMenuId()));
    			}else{
    				//button
    				List buttons = this.baseDao.queryByHQL("FROM ButtonInfo where menuId = ?", new Object[]{menu.getMenuId()});
    				List buttonChild = new ArrayList();
    				for(int m=0,n=buttons.size();m<n;m++){
    					ButtonInfo button = (ButtonInfo) buttons.get(m);
    					MenuTree buttonTree = new MenuTree();
    					buttonTree.setExpandable(false);
    					buttonTree.setLeaf(true);
    					buttonTree.setCls("file");
    					buttonTree.setId("button_"+button.getButtonId());
    	    			buttonTree.setChecked(false);
    					buttonTree.setText(button.getButtonText());
    					buttonChild.add(buttonTree);
    				}
    				tree.setChildren(buttonChild);
    			}
    			childList.add(tree);
    		}
    	}
    	return childList;
    }    
    
    
    /**
     * 查找根节点为rootID的儿子节点
     * @param menuList
     * @param rootId
     * @return
     */
    public List getChildList(List authorizeMenu, String rootId){
    	List childList = new ArrayList();
    	String sql = "";
    	List rootMenu = null;
        if(rootId == null || "".equals(rootId)){
        	sql = "FROM MenuInfo where parentMenuId is NULL ";
            rootMenu = this.baseDao.queryByHQL(sql,null);
        }else{
        	sql = "FROM MenuInfo where parentMenuId = ? ";
            rootMenu = this.baseDao.queryByHQL(sql, new String[]{rootId});
        }
    	
    	for(int i=0,j=rootMenu.size();i<j;i++){
    		MenuInfo menu = (MenuInfo) rootMenu.get(i);
    		MenuTree tree = new MenuTree();
    		if(rootId == null || menu.getParentMenuId() == null || menu.getParentMenuId().equals(rootId)){
    			tree.setId("menu_"+menu.getMenuId());
    			if(authorizeMenu.contains(tree.getId())){
    				tree.setChecked(true);
    			}else{
    				tree.setChecked(false);
    			}
    			tree.setText(menu.getMenuName());
    			tree.setCls("folder");
    			tree.setSingleClickExpand(true);
    			tree.setQtip(menu.getMenuName());
    			tree.setLeaf(false);
    			if("0".equals(menu.getIsLeave())){
    				tree.setChildren(getChildList(authorizeMenu, menu.getMenuId()));
    			}else{
    				//button
    				List buttons = this.baseDao.queryByHQL("FROM ButtonInfo where menuId = ?", new Object[]{menu.getMenuId()});
    				List buttonChild = new ArrayList();
    				for(int m=0,n=buttons.size();m<n;m++){
    					ButtonInfo button = (ButtonInfo) buttons.get(m);
    					MenuTree buttonTree = new MenuTree();
    					buttonTree.setExpandable(false);
    					buttonTree.setLeaf(true);
    					buttonTree.setCls("file");
    					buttonTree.setId("button_"+button.getButtonId());
    					if(authorizeMenu.contains(buttonTree.getId())){
    						buttonTree.setChecked(true);
    	    			}else{
    	    				buttonTree.setChecked(false);
    	    			}
    					buttonTree.setText(button.getButtonText());
    					buttonChild.add(buttonTree);
    				}
    				tree.setChildren(buttonChild);
    			}
    			childList.add(tree);
    		}
    	}
    	return childList;
    }    
    
    public List findAuthorizeMenu(String roleId){
        String hql = "from RoleMenuInfo model where model.roleId= ?";
        return this.baseDao.queryByHQL(hql, new Object[]{roleId});
    }
    
    public List findAuthorizeButton(String roleId){
        String hql = "from RightInfo model where model.roleId= ?";
        return this.baseDao.queryByHQL(hql, new Object[]{roleId});
    }
    
    public void deleteAll(Collection entities){
        this.authorizeDao.deleteAll(entities);
    }
    
    @Override
    public void saveOrUpdateAll(Collection entities) {
        this.authorizeDao.saveOrUpdateAll(entities);
    }
    
    public List findAllAuthorizeUserAndRole(int start, int limit){
        List list = new ArrayList();
        String sql = "SELECT employee_info.operater_id, employee_info.operater_code, employee_info.operater_name, role_info.role_id, role_info.role_name FROM employee_info Left Join supplier_role on employee_info.operater_name =  supplier_role.operater_id left join role_info on supplier_role.role_id =  role_info.role_id ";
        List l = this.baseDao.queryPageBySQL(sql, new String[]{}, start, limit);
        if(l!=null){
            for(int i=0,j = l.size();i<j;i++){
                Object[] obj = (Object[]) l.get(i);
                AuthorizeInfo auth = new AuthorizeInfo();
                auth.setUserId(obj[0]==null ? "" : obj[0].toString());
                auth.setUserCode(obj[1] == null ? "" : obj[1].toString());
                auth.setUserName(obj[2] == null ? "" : obj[2].toString());
                auth.setRoleId(obj[3] == null ? "" : obj[3].toString());
                auth.setRoleName(obj[4] == null ? "" : obj[4].toString());
                list.add(auth);
            }
        }
        return list;
    }
}
