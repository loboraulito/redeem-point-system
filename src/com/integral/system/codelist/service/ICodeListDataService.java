package com.integral.system.codelist.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.integral.system.codelist.bean.CodeListData;

/** 
 * <p>Description: [数据字典数据]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface ICodeListDataService {
    /**
     * <p>Discription:[查询所有数据标准值数量]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long getCodeListDataSize();
    /**
     * <p>Discription:[分页查询数据标准值]</p>
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @throws SQLException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<CodeListData> getCodeListDataByPage(int start, int limit) throws SQLException;
    
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
    /**
     * <p>Discription:[通过数据标准删除数据标准值]</p>
     * @param codeList
     * @author:[代超]
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteByCodeListId(String [] codeList) throws Exception;
    /**
     * <p>Discription:[查找数据标准值树形结构]</p>
     * @param codeId
     * @param parentDataKey
     * @return
     * @author:[代超]
     * @throws SQLException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findCodeDataListTree(String codeId, String parentDataKey) throws SQLException;
    
    /**
     * <p>Discription:[根据数据标准值属性查找]</p>
     * @param instance
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByExample(CodeListData instance);
    /**
     * <p>Discription:[根据数据标准值某指定属性查找]</p>
     * @param propertyName 属性名, 同与bean中的成员变量名
     * @param value 要查找的值
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByProperty(String propertyName, Object value);
    /**
     * <p>Discription:[根据数据标准值dataKey属性查找]</p>
     * @param dataKey
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findByDataKey(Object dataKey);
    /**
     * <p>Discription:[查询所有的数据标准值,以code, dataid排序]</p>
     * @return
     * @author:[代超]
     * @throws SQLException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List findAllOrderByDataCode() throws SQLException;
}
