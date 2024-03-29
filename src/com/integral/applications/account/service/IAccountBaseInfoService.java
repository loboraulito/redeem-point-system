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
    /**
     * <p>Discription:[分页条件查询]</p>
     * @param start
     * @param limit
     * @param params 条件参数
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountBaseInfo> queryPage(int start, int limit, Map<String, Object> params);
    /**
     * <p>Discription:[分页查询-查询总记录数]</p>
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int queryPageSize(Map<String, Object> params);
    
    public void delete(AccountBaseInfo persistentInstance);

    public void deleteAll(Collection persistentInstance);

    public List findAll();

    public List findByExample(AccountBaseInfo instance);

    public AccountBaseInfo findById(String id);

    /**
     * <p>Discription:[新增账目信息]</p>
     * @param transientInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(AccountBaseInfo transientInstance);
    
    /**
     * <p>Discription:[更新账目信息]</p>
     * @param transientInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void update(AccountBaseInfo transientInstance);

    public void saveOrUpdate(AccountBaseInfo instance);

    public void saveOrUpdateAll(Collection instance);

    public Double[] marginAccount(List accounts);

    public List queryByProperty(Map<String, Object> properties);
    /**
     * <p>Discription:[批量查询账目信息]</p>
     * @param baseIds 账目ID列表
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountBaseInfo> queryListByIds(String [] baseIds);

    public Object[] checkImportData(List list, Map properties, String userId, String userName);
    /**
     * <p>Discription:[快速记账]</p>
     * @param outAmount
     * @param inAmount
     * @param comment
     * @param cardId
     * @param userName
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void chargeAccount(double outAmount, double inAmount, String comment, String cardId, String userName);

    /**
     * 组装预算信息
     * <p>Discription:[方法功能中文描述]</p>
     * @param accountList
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountBaseInfo> queryAccountBaseInfoBudgetPage(int start, int limit, Map<String, Object> paramMap);
}
