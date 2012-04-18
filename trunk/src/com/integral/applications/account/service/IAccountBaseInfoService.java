package com.integral.applications.account.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integral.applications.account.bean.AccountBaseInfo;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IAccountBaseInfoService {

    public void delete(AccountBaseInfo persistentInstance);

    public void deleteAll(Collection persistentInstance);

    public List findAll();

    public List findByExample(AccountBaseInfo instance);

    public AccountBaseInfo findById(String id);

    public void save(AccountBaseInfo transientInstance);

    public void saveOrUpdate(AccountBaseInfo instance);

    public void saveOrUpdateAll(Collection instance);

    public Double[] marginAccount(List accounts);

    public List queryByProperty(Map<String, Object> properties);

    public Object[] checkImportData(List list, Map properties, String userId, String userName);

}
