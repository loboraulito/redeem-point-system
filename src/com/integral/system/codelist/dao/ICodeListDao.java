package com.integral.system.codelist.dao;

import java.util.Collection;
import java.util.List;

import com.integral.system.codelist.bean.CodeList;

public interface ICodeListDao {
    /**
     * <p>Discription:[分页查询数据标准列表]</p>
     * @param isSql 是sql语句或者是hql语句
     * @param sql sql语句
     * @param start 分页起始
     * @param limit 分页终止
     * @param params 其他参数
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<CodeList> getCodeListByPage(boolean isSql, String sql, int start, int limit, Object[] params);
    /**
     * <p>Discription:[新增或修改数据标准]</p>
     * @param entity
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void saveOrUpdate(CodeList entity);
    /**
     * <p>Discription:[批量删除数据标准]</p>
     * @param entities
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteAll(Collection<CodeList> entities);
    /**
     * <p>Discription:[用数据标准名称查询]</p>
     * @param name
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByName(Object name);
}
