package com.integral.system.message.service.impl;

import java.util.List;
import java.util.Map;

import com.integral.common.dao.impl.BaseDao;
import com.integral.system.message.bean.SystemMessage;
import com.integral.system.message.dao.ISystemMessageDao;
import com.integral.system.message.service.IMessageService;
import com.integral.util.dwr.MessageSender;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class MessageServiceImpl implements IMessageService {
    private BaseDao baseDao;
    private ISystemMessageDao messageDao;
    
    public BaseDao getBaseDao() {
        return baseDao;
    }
    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    public ISystemMessageDao getMessageDao() {
        return messageDao;
    }
    public void setMessageDao(ISystemMessageDao messageDao) {
        this.messageDao = messageDao;
    }
    @Override
    public void saveOrUpdate(SystemMessage instance) {
        this.messageDao.saveOrUpdate(instance);
        MessageSender sender = new MessageSender();
        sender.sendMessage(instance.getMessageTo(), instance);
    }
    @Override
    public void save(SystemMessage instance) {
        this.messageDao.save(instance);
        MessageSender sender = new MessageSender();
        sender.sendMessage(instance.getMessageTo(), instance);
    }
    @Override
    public void saveOrUpdateAll(List<SystemMessage> persistentInstances) {
        this.messageDao.saveOrUpdateAll(persistentInstances);
        MessageSender sender = new MessageSender();
        for(SystemMessage instance : persistentInstances){
            sender.sendMessage(instance.getMessageTo(), instance);
        }
    }
    @Override
    public void delete(SystemMessage persistentInstance) {
        this.messageDao.delete(persistentInstance);
    }
    @Override
    public void deleteAll(List<SystemMessage> persistentInstances) {
        this.messageDao.deleteAll(persistentInstances);
    }
    @Override
    public List<SystemMessage> findByProperty(String propertyName, Object value) {
        return this.messageDao.findByProperty(propertyName, value);
    }
    @Override
    public SystemMessage findById(String id) {
        return this.messageDao.findById(id);
    }
    @Override
    public List<SystemMessage> findByExample(SystemMessage instance) {
        return this.messageDao.findByExample(instance);
    }
    @Override
    public List<SystemMessage> findByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params) {
        return this.messageDao.findByParams(sql, isHql, start, limit, params);
    }
    @Override
    public int findCountByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params) {
        return this.messageDao.findCountByParams(sql, isHql, start, limit, params);
    }
    @Override
    public List<SystemMessage> findByParams(int start, int limit, Map<String, Object> params) {
        String sql = "FROM SystemMessage as model where 1=1 and model.messageTo = :userId";
        List<SystemMessage> messageList = this.messageDao.findByParams(sql, true, start, limit, params);
        return messageList;
    }
    @Override
    public int findCountByParams(int start, int limit, Map<String, Object> params) {
        String sql = "FROM SystemMessage as model where 1=1 and model.messageTo = :userId";
        return this.messageDao.findCountByParams(sql, true, -1, -1, params);
    }
    
}
