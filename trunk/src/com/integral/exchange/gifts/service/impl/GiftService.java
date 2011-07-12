package com.integral.exchange.gifts.service.impl;

import com.integral.common.dao.impl.BaseDao;
import com.integral.exchange.gifts.dao.IGiftDao;
import com.integral.exchange.gifts.service.IGiftService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: xxx@neusoft.com">作者中文名</a>
 * @version $Revision$ 
 */
public class GiftService implements IGiftService {
    private IGiftDao giftDao;
    private BaseDao baseDao;
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IGiftDao giftDao.
     */
    public IGiftDao getGiftDao() {
        return giftDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param giftDao The giftDao to set.
     */
    public void setGiftDao(IGiftDao giftDao) {
        this.giftDao = giftDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return BaseDao baseDao.
     */
    public BaseDao getBaseDao() {
        return baseDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param baseDao The baseDao to set.
     */
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
}
