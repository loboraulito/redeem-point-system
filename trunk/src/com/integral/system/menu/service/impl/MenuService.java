package com.integral.system.menu.service.impl;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.common.dao.IBaseDao;
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
}
