package com.integral.system.right.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.integral.common.dao.IBaseDao;
import com.integral.system.menu.bean.ButtonInfo;
import com.integral.system.right.dao.IRightDao;
import com.integral.system.right.service.IRightService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate 2011-6-5
 */
public class RightService implements IRightService {
    private IRightDao rightDao;
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
     * @return IRightDao rightDao.
     */
    public IRightDao getRightDao() {
        return rightDao;
    }

    /**
     * <p>Discription:[方法功能描述]</p>
     * @param rightDao The rightDao to set.
     */
    public void setRightDao(IRightDao rightDao) {
        this.rightDao = rightDao;
    }
    
    public List<String> getButtonRoleNameByButton(String buttonId){
        //String sql = "SELECT role_info.role_name FROM menubutton , right_info , role_info WHERE menubutton.button_id =  right_info.button_id AND right_info.role_id =  role_info.role_id AND menubutton.menu_id =  right_info.menu_id AND menubutton.button_id =  ? ";
        String sql = "SELECT role_info.role_name FROM menubutton , right_info , role_info WHERE menubutton.button_id =  right_info.button_id AND right_info.role_id =  role_info.role_id AND menubutton.button_id =  ? ";
        String []params = new String[]{buttonId};
        return this.baseDao.queryBySQL(sql, params);
    }

    @Override
    public List getButtonByRight(String menuId, String roleId) {
        List buttons = new ArrayList();
        List buttonRight = this.rightDao.getButtonByRight(menuId, roleId);
        if(buttonRight !=null){
            for(int i=0,j = buttonRight.size();i<j;i++){
                Object[] obj = (Object[]) buttonRight.get(i);
                ButtonInfo button = (ButtonInfo) obj[0];
                buttons.add(button);
            }
        }
        return buttons;
    }
}
