package com.integral.system.menu.service.impl;

import java.util.List;

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
}
