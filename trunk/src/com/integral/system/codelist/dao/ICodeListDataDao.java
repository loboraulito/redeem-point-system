package com.integral.system.codelist.dao;

import java.util.List;

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
}
