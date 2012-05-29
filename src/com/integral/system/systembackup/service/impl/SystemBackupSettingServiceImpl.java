package com.integral.system.systembackup.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.systembackup.bean.SystemBackSettingInfo;
import com.integral.system.systembackup.dao.ISystemBackupSettingDao;
import com.integral.system.systembackup.service.ISystemBackupSettingService;

public class SystemBackupSettingServiceImpl implements ISystemBackupSettingService {

    private ISystemBackupSettingDao backupSettingDao;
    private BaseDao baseDao;
    @Override
    public void saveOrUpdateAll(Collection<SystemBackSettingInfo> instance) {
        this.backupSettingDao.saveOrUpdateAll(instance);
    }

    @Override
    public void saveOrUpdate(SystemBackSettingInfo instance) {
        this.backupSettingDao.saveOrUpdate(instance);
    }

    @Override
    public List findByExample(SystemBackSettingInfo instance) {
        return this.backupSettingDao.findByExample(instance);
    }

    @Override
    public SystemBackSettingInfo findById(String id) {
        return this.backupSettingDao.findById(id);
    }

    @Override
    public void deleteAll(Collection<SystemBackSettingInfo> persistentInstance) {
        this.backupSettingDao.deleteAll(persistentInstance);
    }

    @Override
    public void delete(SystemBackSettingInfo persistentInstance) {
        this.backupSettingDao.delete(persistentInstance);
    }

    @Override
    public void save(SystemBackSettingInfo transientInstance) {
        this.backupSettingDao.save(transientInstance);
        
    }

    @Override
    public List queryPageBackList(int start, int limit, Map<String, Object> paramMap) {
        return null;
    }

    @Override
    public int queryPageBackList(Map<String, Object> paramMap) {
        return 0;
    }

    public ISystemBackupSettingDao getBackupSettingDao() {
        return backupSettingDao;
    }

    public void setBackupSettingDao(ISystemBackupSettingDao backupSettingDao) {
        this.backupSettingDao = backupSettingDao;
    }

    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    
}
