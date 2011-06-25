package com.integral.system.menu.service;

import java.util.Collection;
import java.util.List;

import com.integral.system.menu.bean.ButtonInfo;

/** 
 * <p>Description: [按钮信息]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public interface IButtonService {
    /**
     * <p>Discription:[查询所有的按钮地址]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-5 代超[变更描述]
     */
    public List<String> findAllButtonUrl();
    
    /**
     * <p>Discription:[查询所有的按钮信息]</p>
     * @return
     * @author: 代超
     * @update: 2011-6-5 代超[变更描述]
     */
    public List<ButtonInfo> findAllButton();
    
    /**
     * <p>Discription:[批量删除按钮信息]</p>
     * @param button
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void deleteAll(Collection button);
    /**
     * <p>Discription:[根据菜单ID查询按钮]</p>
     * @param menuId
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public List findButtonByMenuId(String menuId);
}
