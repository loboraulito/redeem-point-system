package com.integral.system.common.service.impl;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.common.service.ICommonService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CommonServiceImpl implements ICommonService {
    private BaseDao baseDao;

    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
}
