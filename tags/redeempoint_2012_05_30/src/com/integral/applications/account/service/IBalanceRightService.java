package com.integral.applications.account.service;

import java.util.Collection;
import java.util.List;

import com.integral.applications.account.bean.BalanceRight;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IBalanceRightService {

    public void delete(BalanceRight persistentInstance);

    public void deleteAll(Collection<BalanceRight> persistentInstance);

    public List findByExample(BalanceRight instance);

    public BalanceRight findById(String id);

    public void save(BalanceRight transientInstance);

    public void saveOrUpdate(BalanceRight instance);

    public List findBalanceRightByPage(int start, int limit, String[] params);

}
