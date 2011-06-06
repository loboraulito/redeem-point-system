package com.integral.system.menu.service;

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
}
