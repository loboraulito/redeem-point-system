package com.integral.family.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    
    public List<FamilyInfo> findAllFamilyList(int start, int limit){
        String hql = "FROM FamilyInfo ";
        return this.familyInfoDao.findByParams(hql, true, start, limit, null);
    }
    
    public List<FamilyInfo> findFamilyListByParam(int start, int limit, Map<String, Object> paramMap){
        StringBuffer hql = new StringBuffer("FROM FamilyInfo where 1=1 ");
        Object []p = null;
        if(paramMap != null){
            Iterator iter = paramMap.entrySet().iterator();
            p = new Object[paramMap.size()];
            int i=0;
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                p[i] = val;
                hql.append(" and ").append(key).append(" = :").append(key).append(" ");
                i++;
            }
        }
        return this.familyInfoDao.findByParams(hql.toString(), true, start, limit, paramMap);
    }
    
    public int findAllFamilyListSize(){
        String hql = "FROM FamilyInfo ";
        return this.familyInfoDao.findCountByParams(hql, true, -1, -1, null);
    }
    
    public void save(FamilyInfo instance){
        this.familyInfoDao.save(instance);
    }
    
    public void saveOrUpdate(FamilyInfo instance){
        this.familyInfoDao.saveOrUpdate(instance);
    }

    public void deleteAll(List<FamilyInfo> persistentInstances){
        this.familyInfoDao.deleteAll(persistentInstances);
    }
    
    public List<FamilyInfo> findByExample(FamilyInfo instance){
        return this.familyInfoDao.findByExample(instance);
    }
    
    public FamilyInfo findById(java.lang.String id){
        return this.familyInfoDao.findById(id);
    }
    
    public void saveOrUpdateAll(List<FamilyInfo> persistentInstances){
        this.familyInfoDao.saveOrUpdateAll(persistentInstances);
    }
    
    public void updateAllHolder(List<FamilyInfo> persistentInstances) throws Exception{
        StringBuffer sql = new StringBuffer("update family_info set family_house_holder = ? where family_id = ?");
        if(persistentInstances != null && persistentInstances.size() > 0){
            List<Object[]> paramList = new ArrayList<Object[]>();
            for(FamilyInfo info : persistentInstances){
                Object [] obj = new Object[]{info.getFamilyHouseHolder(), info.getFamilyId()};
                paramList.add(obj);
            }
            this.baseDao.excuteSQLBatch(sql.toString(), paramList);
        }else{
            throw new Exception("家庭信息不完整，无法处理请求！");
        }
    }
}
