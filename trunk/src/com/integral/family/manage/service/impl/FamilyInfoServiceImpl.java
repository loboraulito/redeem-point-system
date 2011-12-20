package com.integral.family.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.family.manage.bean.FamilyInfo;
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
    
    public List<FamilyInfo> findFamilyListByUserId(String userId, int start, int limit){
        String hql = "FROM FamilyInfo model WHERE model.familyId IN (" +
        		" SELECT familyId FROM FamilyMember WHERE systemMemberId = :systemMemberId)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("systemMemberId", userId);
        
        return this.familyInfoDao.findByParams(hql, true, start, limit, params);
    }
    
    public int findFamilyListSizeByUserId(String userId){
        String hql = "FROM FamilyInfo model WHERE model.familyId IN (" +
                " SELECT familyId FROM FamilyMember WHERE systemMemberId = :systemMemberId)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("systemMemberId", userId);
        return this.familyInfoDao.findCountByParams(hql, true, -1, -1, params);
    }
}
