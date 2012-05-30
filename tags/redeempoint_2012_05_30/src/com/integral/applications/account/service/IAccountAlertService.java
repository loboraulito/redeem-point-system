package com.integral.applications.account.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    
    public void update(AccountAlert transientInstance);

    public void saveOrUpdate(AccountAlert instance);

    public void saveOrUpdateAll(Collection instance);

    public List findByCustom(Object[] params);

    public List findByCustom(String sql, Object[] params);

    public List doWithAccountBaseInfo(List baseInfo);
    
    /**
     * <p>Discription:[分页条件查询]</p>
     * @param paramMap条件列表
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountAlert> findInstanceList(Map<String, Object> paramMap, int start, int limit);
    /**
     * <p>Discription:[分页条件查询总记录数]</p>
     * @param paramMap
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findInstanceListSize(Map<String, Object> paramMap);
}
