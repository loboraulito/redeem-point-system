package com.integral.family.relationship.service.impl;

import com.integral.common.dao.impl.BaseDao;
import com.integral.family.relationship.dao.IFamilyRelationDAO;
import com.integral.family.relationship.service.IFamilyRelationService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class FamilyRelationServiceImpl implements IFamilyRelationService {
    private BaseDao baseDao;
    private IFamilyRelationDAO familyRelationDao;
    public BaseDao getBaseDao() {
        return baseDao;
    }
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    public IFamilyRelationDAO getFamilyRelationDao() {
        return familyRelationDao;
    }
    public void setFamilyRelationDao(IFamilyRelationDAO familyRelationDao) {
        this.familyRelationDao = familyRelationDao;
    }
}
