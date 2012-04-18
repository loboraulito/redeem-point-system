package com.integral.applications.account.service;

import java.util.Collection;
import java.util.List;

import com.integral.applications.account.bean.AccountAlert;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IAccountAlertService {

    public void delete(AccountAlert transientInstance);

    public void deleteAll(Collection transientInstance);

    public List findByExample(AccountAlert instance);

    public AccountAlert findById(String id);

    public void save(AccountAlert transientInstance);

    public void saveOrUpdate(AccountAlert instance);

    public void saveOrUpdateAll(Collection instance);

    public List findByCustom(Object[] params);

    public List findByCustom(String sql, Object[] params);

    public List doWithAccountBaseInfo(List baseInfo);

}
