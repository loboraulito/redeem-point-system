package com.integral.system.menu.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.dao.IMenuDao;
import com.integral.system.menu.service.IMenuService;

/** 
 * <p>Description: [菜单服务类]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-28
 */
public class MenuService implements IMenuService {
    private IMenuDao menuDao;
    private IBaseDao baseDao;

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

    /**
     * <p>Discription:[Spring的IOC注入方法]</p>
     * @return IMenuDao menuDao.
     */
    public IMenuDao getMenuDao() {
        return menuDao;
    }

    /**
     * <p>Discription:[Spring的IOC注入方法]</p>
     * @param menuDao The menuDao to set.
     */
    public void setMenuDao(IMenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List findAll() {
        return this.menuDao.findAll();
    }
    
    public long findAllMenuSize(){
        long size = 0L;
        String sql = "select count(menu_info.menu_id) as menu_size from menu_info";
        List list = this.baseDao.queryBySQL(sql, null);
        if(list!=null){
            size = NumberUtils.toLong((String.valueOf(list.get(0))), 0L);
        }
        return size;
    }

    @Override
    public List findMenuByPage(int start, int limit) {
        return this.menuDao.findMenuByPage(start, limit);
    }
    
    public List findMenuByPageWithParentName(int start, int limit){
        String sql = "SELECT menu_info.menu_id, menu_info.menu_name, menu_info.page_path, menu_info.menu_level, menu_info.parent_menu, menu_info.is_leave, (select menu.menu_name menuname from menu_info menu where menu_info.parent_menu = menu.menu_id) parent_menu_name FROM menu_info order by parent_menu_name";
        List menus = this.baseDao.queryPageBySQL(sql, null, start, limit);
        List list = new ArrayList();
        if(menus!=null){
            for(int i=0,j = menus.size();i<j;i++){
                MenuInfo menu = new MenuInfo();
                Object obj[] = (Object[]) menus.get(i);
                menu.setMenuId(obj[0] == null?"":obj[0].toString());
                menu.setMenuName(obj[1] == null?"":obj[1].toString());
                menu.setPagePath(obj[2] == null?"":obj[2].toString());
                menu.setMenuLevel(obj[3] == null?"":obj[3].toString());
                menu.setParentMenuId(obj[4] == null?"":obj[4].toString());
                menu.setIsLeave(obj[5] == null?"":obj[5].toString());
                menu.setParentMenuName(obj[6] == null?"":obj[6].toString());
                list.add(menu);
            }
        }
        return list;
    }
    
    /**
     * <p>Discription:[查询父级菜单]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public List findRootMenu(){
        return this.menuDao.findRootMenu();
    }
    /**
     * <p>Discription:[查询父菜单下的子菜单]</p>
     * @param rootMenuId
     * @return
     * @author 代超
     * @update 2011-5-29 代超[变更描述]
     */
    public List findChildMenu(String rootMenuId){
        return this.menuDao.findChildMenu(rootMenuId);
    }
    /**
     * <p>Discription:[添加或修改菜单信息]</p>
     * @param menu
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void saveOrUpdateMenu(MenuInfo menu){
        this.menuDao.saveOrUpdateMenu(menu);
    }
    
    /**
     * <p>Discription:[使用主键查询]</p>
     * @param menuId
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public MenuInfo findById(String menuId){
        return this.menuDao.findById(menuId);
    }
    
    /**
     * <p>Discription:[批量删除菜单信息]</p>
     * @param menus
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void deleteAll(Collection menus){
        this.menuDao.deleteAll(menus);
    }
}
