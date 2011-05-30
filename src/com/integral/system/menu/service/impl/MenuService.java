package com.integral.system.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.integral.system.menu.bean.MenuInfo;
import com.integral.system.menu.bean.MenuTree;
import com.integral.system.menu.dao.IMenuDao;
import com.integral.system.menu.service.IMenuService;

/** 
 * <p>Description: [菜单服务类]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-5-28
 */
public class MenuService implements IMenuService {
    private IMenuDao menuDao;

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

    @Override
    public List findChildMenuTree(String rootMenuId) {
        List menuTrees = new ArrayList();
        List menus = this.menuDao.findChildMenu(rootMenuId);
        if(menus == null){
            return null;
        }
        for(int i=0,j = menus.size();i<j;i++){
            MenuInfo menu = (MenuInfo) menus.get(i);
            MenuTree menuTree = new MenuTree();
            
            menuTree.setId(menu.getMenuId());
            menuTree.setText(menu.getMenuName());
            menuTree.setQtip(menu.getMenuName());
            
            if("0".equals(menu.getIsLeave())){//非叶子节点
                menuTree.setCls("folder");
                menuTree.setHref(null);
                menuTree.setLeaf(false);
                menuTree.setExpandable(true);
            }else{ //叶子节点
                menuTree.setCls("file");
                menuTree.setHref(menu.getPagePath());
                menuTree.setLeaf(true);
                menuTree.setExpandable(false);
                // TODO 链接的目标位置
                menuTree.setHrefTarget("");
            }
            menuTrees.add(menuTree);
        }
        return menuTrees;
    }

    @Override
    public List findRootMenu(String userName) {
        return null;
    }
}
