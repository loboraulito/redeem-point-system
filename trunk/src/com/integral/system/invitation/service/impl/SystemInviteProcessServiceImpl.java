package com.integral.system.invitation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.invitation.bean.SystemInviteProcess;
import com.integral.system.invitation.dao.ISystemInviteProcessDao;
import com.integral.system.invitation.service.ISystemInviteProcessService;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class SystemInviteProcessServiceImpl implements ISystemInviteProcessService {
    private BaseDao baseDao;
    private ISystemInviteProcessDao systemInviteProcessDao;
    public BaseDao getBaseDao() {
        return baseDao;
    }
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    public ISystemInviteProcessDao getSystemInviteProcessDao() {
        return systemInviteProcessDao;
    }
    public void setSystemInviteProcessDao(ISystemInviteProcessDao systemInviteProcessDao) {
        this.systemInviteProcessDao = systemInviteProcessDao;
    }
    @Override
    public List<SystemInviteProcess> findByUserId(String userId, int start, int limit) {
        String sql = "from SystemInviteProcess model where model.recipient = :userId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        
        return this.systemInviteProcessDao.findByParams(sql, true, start, limit, params);
    }
    @Override
    public int findCountByUserId(String userId) {
        String sql = "from SystemInviteProcess model where model.recipient = :userId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        
        return this.systemInviteProcessDao.findCountByParams(sql, true, -1, -1, params);
    }
}
