package com.integral.family.relationship.dao;

import java.util.List;
import java.util.Map;

import com.integral.family.relationship.bean.FamilyRelation;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface IFamilyRelationDAO {
    /**
     * <p>Discription:[新增或修改家庭成员关系]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(FamilyRelation instance);
    /**
     * <p>Discription:[新增家庭成员关系]</p>
     * @param instance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void save(FamilyRelation instance);
    /**
     * <p>Discription:[批量新增/修改家庭成员关系]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdateAll(List<FamilyRelation> persistentInstances);
    /**
     * <p>Discription:[删除家庭成员关系]</p>
     * @param persistentInstance
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void delete(FamilyRelation persistentInstance);
    /**
     * <p>Discription:[批量删除家庭成员关系]</p>
     * @param persistentInstances
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(List<FamilyRelation> persistentInstances);
    /**
     * <p>Discription:[通过属性查询]</p>
     * @param propertyName
     * @param value
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyRelation> findByProperty(String propertyName, Object value);
    /**
     * <p>Discription:[主键查询]</p>
     * @param id
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public FamilyRelation findById(java.lang.String id);
    /**
     * <p>Discription:[多属性查询]</p>
     * @param instance
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyRelation> findByExample(FamilyRelation instance);
    /**
     * <p>Discription:[自定义sql分页查询]</p>
     * @param sql
     * @param isHql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<FamilyRelation> findByParams(final String sql, final boolean isHql, final int start, final int limit,
            final Map<String, Object> params);
    /**
     * <p>Discription:[自定义sql查询数量]</p>
     * @param sql
     * @param isHql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCountByParams(String sql, boolean isHql, int start, int limit, Map<String, Object> params);
}
