package com.integral.applications.account.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integral.applications.account.bean.AccountAlert;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IAccountAlertDAO {

    public void delete(AccountAlert transientInstance);

    public void deleteAll(Collection transientInstance);

    public List findByExample(AccountAlert instance);

    public AccountAlert findById(String id);

    public void save(AccountAlert transientInstance);

    public void saveOrUpdate(AccountAlert instance);

    public void saveOrUpdateAll(Collection instance);

    public List findByCustom(Object[] params);

    public List findByCustom(String sql, Object[] params);

    public List findByProperty(Map<String, Object> properties);
    
    /**
     * <p>Discription:[分页条件查询]</p>
     * @param sqlOrHql sql/hql语句
     * @param isSql 是否是sql
     * @param paramMap 参数列表
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountAlert> findInstanceList(String sqlOrHql, boolean isSql, Map<String, Object> paramMap, int start, int limit);

}
