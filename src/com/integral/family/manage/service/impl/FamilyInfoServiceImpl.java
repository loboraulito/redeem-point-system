package com.integral.family.manage.service.impl;

import com.integral.common.dao.impl.BaseDao;
import com.integral.family.manage.dao.IFamilyInfoDAO;
import com.integral.family.manage.service.IFamilyInfoService;
/**
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
public class FamilyInfoServiceImpl implements IFamilyInfoService {
    private BaseDao baseDao;
    private IFamilyInfoDAO familyInfoDao;
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
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return IFamilyInfoDAO familyInfoDao.
     */
    public IFamilyInfoDAO getFamilyInfoDao() {
        return familyInfoDao;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param familyInfoDao The familyInfoDao to set.
     */
    public void setFamilyInfoDao(IFamilyInfoDAO familyInfoDao) {
        this.familyInfoDao = familyInfoDao;
    }
}
