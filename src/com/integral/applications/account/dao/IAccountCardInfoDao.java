package com.integral.applications.account.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.integral.applications.account.bean.AccountCardInfo;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IAccountCardInfoDao {
    /**
     * <p>Discription:[保存数据]</p>
     * @param cardInfo
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(AccountCardInfo cardInfo);
    /**
     * <p>Discription:[保存或修改]</p>
     * @param cardInfo
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(AccountCardInfo cardInfo);
    /**
     * <p>Discription:[批量保存或修改]</p>
     * @param cardInfos
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdateAll(Collection<AccountCardInfo> cardInfos);
    /**
     * <p>Discription:[删除数据]</p>
     * @param cardInfos
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void delete(AccountCardInfo cardInfos);
    /**
     * <p>Discription:[批量删除]</p>
     * @param cardInfos
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(Collection<AccountCardInfo> cardInfos);
    /**
     * <p>Discription:[主键查询]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public AccountCardInfo findById(java.lang.String id);
    /**
     * <p>Discription:[多属性查询]</p>
     * @param instance
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountCardInfo> findByExample(AccountCardInfo instance);
    /**
     * <p>Discription:[单属性查询]</p>
     * @param propertyName
     * @param value
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<AccountCardInfo> findByProperty(String propertyName, Object value);
    /**
     * <p>Discription:[自定义sql查询]</p>
     * @param sql 自定义sql语句
     * @param isSql 是否是标准sql语句
     * @param paramMap 查询参数
     * @param start 分页参数
     * @param limit 分页参数
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findBySql(String sql, boolean isSql, Map<String, Object> paramMap, int start, int limit);
}
