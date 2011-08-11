package com.integral.system.codelist.service;

import java.util.List;

import com.integral.system.codelist.bean.CodeList;

/** 
 * <p>Description: [数据字典服务]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface ICodeListService {
    /**
     * <p>Discription:[查询所有的数据标准]</p>
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long getCodeListSize();
    /**
     * <p>Discription:[分页查询数据标准列表]</p>
     * @param start
     * @param limit
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<CodeList> getCodeListByPage(int start, int limit);
}
