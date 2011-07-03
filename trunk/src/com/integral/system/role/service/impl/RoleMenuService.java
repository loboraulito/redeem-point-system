package com.integral.system.role.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.dao.IMenuDao;
import com.integral.system.role.dao.IRoleDao;
import com.integral.system.role.dao.IRoleMenuDao;
import com.integral.system.role.dao.IUserRoleDao;
import com.integral.system.role.service.IRoleMenuService;
import com.integral.system.user.dao.IUserDao;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-18
 */
public class RoleMenuService implements IRoleMenuService {
    private IMenuDao menuDao;
    private IRoleDao roleDao;
    private IRoleMenuDao roleMenuDao;
    private IUserRoleDao userRoleDao;
    private IUserDao userDao;
    private IBaseDao baseDao;
    
    
    public List<String> getRoleMenuMap(String role){
        //查询资源（地址）的名称
        //String sql = "SELECT menu_info.page_path FROM menu_info , rolemenu , role_info WHERE menu_info.menu_id =  rolemenu.menuId AND rolemenu.roleId =  role_info.role_id AND role_info.role_name= ?";
        String sql = "SELECT menu_info.page_path FROM menu_info , rolemenu , role_info WHERE menu_info.menu_id =  rolemenu.menuId AND rolemenu.roleId =  role_info.role_id AND role_info.role_id= ?";
        String param[] = new String[]{role};
        return this.roleMenuDao.queryBySQL(sql, param);
    }
    
    public List<String> getMenuRoleMap(String menu){
        //查询能访问资源的角色
        String sql = "SELECT DISTINCT role_info.role_name FROM rolemenu , menu_info , role_info WHERE rolemenu.menuId =  menu_info.menu_id AND rolemenu.roleId =  role_info.role_id AND menu_info.page_path= ?";
        String param[] = new String[]{menu};
        return this.roleMenuDao.queryBySQL(sql, param);
    }
    
    public List<String> getMenuRoleMapByMenuId(String menu){
        //查询能访问资源的角色
        String sql = "SELECT DISTINCT role_info.role_id FROM rolemenu , menu_info , role_info WHERE rolemenu.menuId =  menu_info.menu_id AND rolemenu.roleId =  role_info.role_id AND menu_info.menu_id= ?";
        String param[] = new String[]{menu};
        return this.roleMenuDao.queryBySQL(sql, param);
    }
    
    /**
     * <p>Discription:[查询根节点（第一级节点）]</p>
     * @param role
     * @return
     * @author 代超
     * @update 2011-5-28 代超[变更描述]
     */
    public List<String> getRootMenuMapBySql(String role){
        //查询资源（地址）的名称
        String sql = "SELECT DISTINCT menu_info.menu_id, menu_info.menu_name, menu_info.page_path, menu_info.menu_level, menu_info.parent_menu, menu_info.is_leave FROM menu_info , rolemenu WHERE menu_info.menu_id =  rolemenu.menuId AND rolemenu.roleId =  ? ";
        String param[] = new String[]{role};
        return this.roleMenuDao.queryBySQL(sql, param);
    }
    /**
     * <p>Discription:[查询有权限访问的根节点]</p>
     * @param role
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List getRootMenuMap(String role){
        //查询资源（地址）的名称
        String sql = "FROM MenuInfo as menu , RoleMenuInfo rolemenu WHERE menu.menuId =  rolemenu.menuId AND menu.parentMenuId is NULL AND rolemenu.roleId =  ? ";
        String param[] = new String[]{role};
        return this.roleMenuDao.queryByHQL(sql, param);
    }
    
    @Override
    public List getChildMenuMap(String role, String rootId) {
        String sql = "FROM MenuInfo as menu , RoleMenuInfo rolemenu WHERE menu.menuId =  rolemenu.menuId AND menu.parentMenuId = ? AND rolemenu.roleId =  ? ";
        String param[] = new String[]{rootId, role};
        return this.roleMenuDao.queryByHQL(sql, param);
    }
    
    @Override
    public void deleteByRoleId(String[] roles) throws Exception {
        if(roles == null || roles.length<1){
            return;
        }
        String sql = "delete from rolemenu where roleId in ( ? ";
        if(roles != null && roles.length>1){
            for(int i=1;i<roles.length;i++){
                sql += " , ? ";
            }
        }
        sql += " )";
        this.baseDao.excuteSQL(sql, roles);
    }
    
    @Override
    public void deleteAll(Collection entities) {
        this.roleMenuDao.deleteAll(entities);
    }

    @Override
    public void saveOrUpdateAll(Collection entities) {
        this.roleMenuDao.saveOrUpdateAll(entities);
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IMenuDao menuDao.
     */
    public IMenuDao getMenuDao() {
        return menuDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param menuDao
     *            The menuDao to set.
     */
    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IRoleDao roleDao.
     */
    public IRoleDao getRoleDao() {
        return roleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param roleDao
     *            The roleDao to set.
     */
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IRoleMenuDao roleMenuDao.
     */
    public IRoleMenuDao getRoleMenuDao() {
        return roleMenuDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param roleMenuDao
     *            The roleMenuDao to set.
     */
    public void setRoleMenuDao(IRoleMenuDao roleMenuDao) {
        this.roleMenuDao = roleMenuDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IUserRoleDao userRoleDao.
     */
    public IUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param userRoleDao
     *            The userRoleDao to set.
     */
    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @return IUserDao userDao.
     */
    public IUserDao getUserDao() {
        return userDao;
    }

    /**
     * <p>
     * Discription:[方法功能描述]
     * </p>
     * 
     * @param userDao
     *            The userDao to set.
     */
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @return IBaseDao baseDao.
     */
    public IBaseDao getBaseDao() {
        return baseDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param baseDao The baseDao to set.
     */
    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }

}
