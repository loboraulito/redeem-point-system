package com.integral.system.menu.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.bean.ButtonInfo;
import com.integral.system.menu.dao.IButtonDao;
import com.integral.system.menu.service.IButtonService;

/** 
 * <p>Description: [按钮信息]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public class ButtonService implements IButtonService {
    private IButtonDao buttonDao;
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
     * <p>Discription:[方法功能描述]</p>
     * @return IButtonDao buttonDao.
     */
    public IButtonDao getButtonDao() {
        return buttonDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param buttonDao The buttonDao to set.
     */
    public void setButtonDao(IButtonDao buttonDao) {
        this.buttonDao = buttonDao;
    }

    @Override
    public List<String> findAllButtonUrl() {
        return this.buttonDao.findAllButtonUrl();
    }

    @Override
    public List<ButtonInfo> findAllButton() {
        return this.buttonDao.findAllButton();
    }
    
    /**
     * <p>Discription:[批量删除按钮信息]</p>
     * @param button
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public void deleteAll(Collection button){
        this.buttonDao.deleteAll(button);
    }
    /**
     * <p>Discription:[根据菜单ID查询按钮]</p>
     * @param menuId
     * @return
     * @author: 代超
     * @update: 2011-6-25 代超[变更描述]
     */
    public List findButtonByMenuId(String menuId){
        return this.buttonDao.findButtonByMenuId(menuId);
    }
    
    public Long findAllButtonSize(){
        long size = 0L;
        String sql = "select count(menubutton.button_id) as buttonsize from menubutton";
        List list = this.baseDao.queryBySQL(sql, null);
        if(list!=null){
            size = NumberUtils.toLong((String.valueOf(list.get(0))), 0L);
        }
        return size;
    }
    
    public List findButtonByPageWithMenu(int start, int limit){
        String sql = "SELECT menubutton.button_id, menubutton.button_name, menubutton.button_text, menubutton.menu_id," +
        		" menubutton.button_url, menubutton.button_show, menubutton.button_css, menubutton.handler," +
        		" menu_info.menu_name FROM menubutton left join menu_info on menubutton.menu_id =  menu_info.menu_id" +
        		" ORDER BY menubutton.menu_id ASC ";
        List buttons = this.baseDao.queryPageBySQL(sql, new String[]{}, start, limit);
        List list = new ArrayList();
        if(buttons != null){
            for(int i = 0, j = buttons.size(); i < j; i++){
                ButtonInfo button = new ButtonInfo();
                Object obj[] = (Object[]) buttons.get(i);
                button.setButtonId(obj[0] == null ? "" : obj[0].toString());
                button.setButtonName(obj[1] == null ? "" : obj[1].toString());
                button.setButtonText(obj[2] == null ? "" : obj[2].toString());
                button.setMenuId(obj[3] == null ? "" : obj[3].toString());
                button.setButtonUrl(obj[4] == null ? "" : obj[4].toString());
                button.setIsShow(obj[5] == null ? "" : obj[5].toString());
                button.setButtonIconCls(obj[6] == null ? "" : obj[6].toString());
                button.setHandler(obj[7] == null ? "" : obj[7].toString());
                button.setMenuName(obj[8] == null ? "" : obj[8].toString());
                list.add(button);
            }
        }
        return list;
    }

    @Override
    public void saveOrUpdate(ButtonInfo button) {
        this.buttonDao.saveOrUpdate(button);
    }
}
