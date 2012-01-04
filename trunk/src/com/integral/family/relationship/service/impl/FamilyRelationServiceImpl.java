package com.integral.family.relationship.service.impl;

import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.family.relationship.bean.FamilyRelation;
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
    @Override
    public void saveOrUpdate(FamilyRelation instance) {
        this.familyRelationDao.saveOrUpdate(instance);
    }
    @Override
    public void save(FamilyRelation instance) {
        this.familyRelationDao.save(instance);
    }
    @Override
    public void saveOrUpdateAll(List<FamilyRelation> persistentInstances) {
        this.familyRelationDao.saveOrUpdateAll(persistentInstances);
    }
    @Override
    public void delete(FamilyRelation persistentInstance) {
        this.familyRelationDao.delete(persistentInstance);
    }
    @Override
    public void deleteAll(List<FamilyRelation> persistentInstances) {
        this.familyRelationDao.deleteAll(persistentInstances);
    }
    @Override
    public List<FamilyRelation> findByProperty(String propertyName, Object value) {
        return this.familyRelationDao.findByProperty(propertyName, value);
    }
    @Override
    public FamilyRelation findById(String id) {
        return this.familyRelationDao.findById(id);
    }
    @Override
    public List<FamilyRelation> findByExample(FamilyRelation instance) {
        return this.familyRelationDao.findByExample(instance);
    }
    @Override
    public List<FamilyRelation> findByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params) {
        return null;
    }
    @Override
    public int findCountByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params) {
        return 0;
    }
}
