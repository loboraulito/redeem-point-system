package com.integral.system.codelist.dao;

import java.util.Collection;
import java.util.List;

import com.integral.system.codelist.bean.CodeListData;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface ICodeListDataDao {
    /**
     * <p>Discription:[分页查询数据标准值列表]</p>
     * @param sql
     * @param start
     * @param limit
     * @param params
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findCodeListDataByPage(boolean isSql, String sql, int start, int limit, Object[] params);
    /**
     * <p>Discription:[增加或修改数据标准值]</p>
     * @param entity
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(CodeListData entity);
    /**
     * <p>Discription:[批量删除数据标准值]</p>
     * @param entities
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(Collection<CodeListData> entities);
}
