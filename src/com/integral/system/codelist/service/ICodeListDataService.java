package com.integral.system.codelist.service;

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
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<CodeListData> getCodeListDataByPage(int start, int limit);
}
