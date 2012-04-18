package com.integral.applications.account.service;

import java.util.Collection;
import java.util.List;

import com.integral.applications.account.bean.BalanceInfo;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IBalanceInfoService {

    public void save(BalanceInfo transientInstance);

    public void delete(BalanceInfo persistentInstance);

    public void deleteAll(Collection persistentInstance);

    public List findByExample(BalanceInfo instance);

    public BalanceInfo findById(String id);

    public void saveOrUpdate(BalanceInfo instance);

    public void saveOrUpdateAll(Collection instance);

}
