package com.integral.system.systembackup.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.systembackup.bean.SystemBackupInfo;
import com.integral.system.systembackup.dao.ISystemBackupDao;
import com.integral.system.systembackup.service.ISystemBackupService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class SystemBackupServiceImpl implements ISystemBackupService {
    private BaseDao baseDao;
    private ISystemBackupDao backDao;
    
    @Override
    public void saveOrUpdateAll(Collection<SystemBackupInfo> instance) {
        this.backDao.saveOrUpdateAll(instance);
    }
    @Override
    public void saveOrUpdate(SystemBackupInfo instance) {
        this.backDao.saveOrUpdate(instance);
    }
    @Override
    public List findByExample(SystemBackupInfo instance) {
        return this.backDao.findByExample(instance);
    }
    @Override
    public SystemBackupInfo findById(String id) {
        return this.backDao.findById(id);
    }
    @Override
    public void deleteAll(Collection<SystemBackupInfo> persistentInstance) {
        this.backDao.deleteAll(persistentInstance);
    }
    @Override
    public void delete(SystemBackupInfo persistentInstance) {
        this.backDao.delete(persistentInstance);
    }
    @Override
    public void save(SystemBackupInfo transientInstance) {
        this.backDao.save(transientInstance);
    }
    @Override
    public List queryPageBackList(int start, int limit, Map<String, Object> paramMap) {
        StringBuffer hql = new StringBuffer("from SystemBackupInfo model where 1 = 1 ");
        if(paramMap != null && !paramMap.isEmpty()){
            for(String key : paramMap.keySet()){
                if(paramMap.get(key) == null || "".equals(paramMap.get(key).toString().trim())){
                    continue;
                }
                hql.append(" and model.").append(key).append(" = :").append(key).append(" ");
            }
        }
        return this.baseDao.queryPageByHQL(hql.toString(), paramMap, start, limit);
    }
    @Override
    public int queryPageBackList(Map<String, Object> paramMap) {
        List list = queryPageBackList(-1, -1, paramMap);
        if(list != null && !list.isEmpty()){
            return list.size();
        }
        return 0;
    }
    
    public BaseDao getBaseDao() {
        return baseDao;
    }
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    public ISystemBackupDao getBackDao() {
        return backDao;
    }
    public void setBackDao(ISystemBackupDao backDao) {
        this.backDao = backDao;
    }
}
