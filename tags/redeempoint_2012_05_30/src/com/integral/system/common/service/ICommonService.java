package com.integral.system.common.service;

import java.util.List;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public interface ICommonService {
    /**
     * <p>Discription:[将行政区域组装成地址]</p>
     * @param codeId
     * @param dataKey
     * @return
     * @author:[代超]
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List getAddressFromCodeData(String codeId, String ... dataKey) throws Exception;
}
